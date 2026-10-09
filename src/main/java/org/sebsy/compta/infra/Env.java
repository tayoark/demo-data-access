package org.sebsy.compta.infra;

public final class Env {

    private Env() {
    }

    public static String required(String nom) {
        String valeur = System.getenv(nom);
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalStateException("Variable d'environnement " + nom + " absente");
        }
        return valeur;
    }
}
