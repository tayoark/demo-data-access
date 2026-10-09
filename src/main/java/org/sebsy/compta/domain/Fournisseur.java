package org.sebsy.compta.domain;

public class Fournisseur {

    private Long id;
    private String nom;
    private Adresse adresse;

    protected Fournisseur() {
    }

    public Fournisseur(String nom, Adresse adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Adresse getAdresse() { return adresse; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Fournisseur)) {
            return false;
        }
        Fournisseur autre = (Fournisseur) o;
        return nom != null && nom.equals(autre.nom);
    }

    @Override
    public int hashCode() {
        return nom == null ? 0 : nom.hashCode();
    }

    @Override
    public String toString() {
        return "Fournisseur[id=" + id + ", nom=" + nom + ", ville=" + adresse.ville() + "]";
    }
}
