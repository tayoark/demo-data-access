package org.sebsy.compta.domain;

public record Adresse(String rue, String codePostal, String ville) {

    public Adresse {
        if (ville == null) {
            throw new IllegalArgumentException("ville obligatoire");
        }
    }

    public static Adresse ville(String ville) {
        return new Adresse(null, null, ville);
    }
}
