package org.sebsy.compta.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class BonDaoJdbc {

    /*
     * Item 11, resultats sur le bon 7 (delai IS NULL) :
     *   rs.getInt("delai")                    -> 0
     *   rs.getObject("delai", Integer.class)  -> null
     *
     * Item 12 : un getter primitif ne peut pas rendre NULL, il rend 0. "Delai inconnu"
     * devient "livre en zero jour", une valeur precise et fausse. Aucune exception, aucun
     * message : l'erreur entre dans la facturation sans alerte. Un arrondi, lui, reste une
     * valeur approximative du bon type ; ici l'absence de valeur est transformee en valeur.
     */

    // Version 1 : le piege
    public Optional<Integer> delaiNaif(long idBon) {
        return Tx.with("delaiNaif", cnx -> {
            try (PreparedStatement ps = cnx.prepareStatement("SELECT delai FROM bon WHERE id = ?")) {
                ps.setLong(1, idBon);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(rs.getInt("delai"));
                    }
                    return Optional.empty();
                }
            }
        });
    }

    // Version 2 : correcte
    public Optional<Integer> delai(long idBon) {
        return Tx.with("delai", cnx -> {
            try (PreparedStatement ps = cnx.prepareStatement("SELECT delai FROM bon WHERE id = ?")) {
                ps.setLong(1, idBon);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Optional.ofNullable(rs.getObject("delai", Integer.class));
                    }
                    return Optional.empty();
                }
            }
        });
    }
}
