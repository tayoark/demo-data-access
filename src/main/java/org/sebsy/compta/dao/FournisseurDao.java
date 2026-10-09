package org.sebsy.compta.dao;

import org.sebsy.compta.domain.Fournisseur;
import java.util.List;
import java.util.Optional;

public interface FournisseurDao {
    Optional<Fournisseur> parId(long id);
    Optional<Fournisseur> parNom(String nom);
    List<Fournisseur> parVille(String ville);
    List<Fournisseur> toutes();
    Fournisseur creer(Fournisseur fournisseur);
    boolean renommer(long id, String nouveauNom);
    boolean supprimer(long id);
}
