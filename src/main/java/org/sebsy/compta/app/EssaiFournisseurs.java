package org.sebsy.compta.app;

import org.sebsy.compta.dao.jdbc.BonDaoJdbc;
import org.sebsy.compta.dao.jdbc.FournisseurDaoJdbc;
import org.sebsy.compta.domain.Adresse;
import org.sebsy.compta.domain.Fournisseur;

public class EssaiFournisseurs {

    public static void main(String[] args) {
        FournisseurDaoJdbc fournisseurs = new FournisseurDaoJdbc();
        BonDaoJdbc bons = new BonDaoJdbc();

        System.out.println("toutes()          : " + fournisseurs.toutes().size() + " fournisseurs");
        System.out.println("parId(2)          : " + fournisseurs.parId(2));
        System.out.println("parId(999)        : " + fournisseurs.parId(999));
        System.out.println("parVille(Nantes)  : " + fournisseurs.parVille("Nantes"));

        Fournisseur cree = fournisseurs.creer(new Fournisseur("Peinture Express", Adresse.ville("Nantes")));
        System.out.println("creer()           : " + cree);
        System.out.println("renommer(cree)    : " + fournisseurs.renommer(cree.getId(), "Peinture Expresse"));
        System.out.println("renommer(999)     : " + fournisseurs.renommer(999, "X"));
        System.out.println("supprimer(cree)   : " + fournisseurs.supprimer(cree.getId()));

        System.out.println("delai(bon 1)      : " + bons.delai(1));
        System.out.println("delai(bon 7)      : " + bons.delai(7));
        System.out.println("delaiNaif(bon 7)  : " + bons.delaiNaif(7));
    }
}
