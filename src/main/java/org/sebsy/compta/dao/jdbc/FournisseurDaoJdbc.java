package org.sebsy.compta.dao.jdbc;

import org.sebsy.compta.dao.FournisseurDao;
import org.sebsy.compta.domain.Adresse;
import org.sebsy.compta.domain.Fournisseur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FournisseurDaoJdbc implements FournisseurDao {

    private Fournisseur map(ResultSet rs) throws SQLException {
        Adresse adresse = new Adresse(rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
        Fournisseur f = new Fournisseur(rs.getString("nom"), adresse);
        f.setId(rs.getLong("id"));
        return f;
    }

    @Override
    public Optional<Fournisseur> parId(long id) {
        return Tx.with("parId", cnx -> {
            try (PreparedStatement ps = cnx.prepareStatement(
                    "SELECT id, nom, rue, code_postal, ville FROM fournisseur WHERE id = ?")) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(map(rs));
                    }
                    return Optional.empty();
                }
            }
        });
    }

    @Override
    public Optional<Fournisseur> parNom(String nom) {
        return Tx.with("parNom", cnx -> {
            try (PreparedStatement ps = cnx.prepareStatement(
                    "SELECT id, nom, rue, code_postal, ville FROM fournisseur WHERE nom = ?")) {
                ps.setString(1, nom);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(map(rs));
                    }
                    return Optional.empty();
                }
            }
        });
    }

    @Override
    public List<Fournisseur> parVille(String ville) {
        return Tx.with("parVille", cnx -> {
            try (PreparedStatement ps = cnx.prepareStatement(
                    "SELECT id, nom, rue, code_postal, ville FROM fournisseur WHERE ville = ? ORDER BY nom")) {
                ps.setString(1, ville);
                try (ResultSet rs = ps.executeQuery()) {
                    List<Fournisseur> resultat = new ArrayList<>();
                    while (rs.next()) {
                        resultat.add(map(rs));
                    }
                    return resultat;
                }
            }
        });
    }

    @Override
    public List<Fournisseur> toutes() {
        return Tx.with("toutes", cnx -> {
            try (PreparedStatement ps = cnx.prepareStatement(
                    "SELECT id, nom, rue, code_postal, ville FROM fournisseur ORDER BY id");
                 ResultSet rs = ps.executeQuery()) {
                List<Fournisseur> resultat = new ArrayList<>();
                while (rs.next()) {
                    resultat.add(map(rs));
                }
                return resultat;
            }
        });
    }

    @Override
    public Fournisseur creer(Fournisseur f) {
        return Tx.with("creer", cnx -> {
            try (PreparedStatement ps = cnx.prepareStatement(
                    "INSERT INTO fournisseur (nom, rue, code_postal, ville) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, f.getNom());
                ps.setString(2, f.getAdresse().rue());
                ps.setString(3, f.getAdresse().codePostal());
                ps.setString(4, f.getAdresse().ville());
                ps.executeUpdate();
                try (ResultSet cles = ps.getGeneratedKeys()) {
                    cles.next();
                    f.setId(cles.getLong(1));
                }
                return f;
            }
        });
    }

    @Override
    public boolean renommer(long id, String nouveauNom) {
        return Tx.with("renommer", cnx -> {
            try (PreparedStatement ps = cnx.prepareStatement(
                    "UPDATE fournisseur SET nom = ? WHERE id = ?")) {
                ps.setString(1, nouveauNom);
                ps.setLong(2, id);
                return ps.executeUpdate() > 0;
            }
        });
    }

    @Override
    public boolean supprimer(long id) {
        return Tx.with("supprimer", cnx -> {
            try (PreparedStatement ps = cnx.prepareStatement(
                    "DELETE FROM fournisseur WHERE id = ?")) {
                ps.setLong(1, id);
                return ps.executeUpdate() > 0;
            }
        });
    }
}
