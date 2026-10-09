package org.sebsy.compta.app;

import org.sebsy.compta.infra.Db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PremierContact {

    public static void main(String[] args) throws SQLException {
        try (Connection cnx = Db.dataSource().getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM fournisseur")) {
            System.out.println("Connexion : " + cnx.getClass().getName());
            System.out.println("Version   : " + cnx.getMetaData().getDatabaseProductVersion());
            rs.next();
            System.out.println("Fournisseurs : " + rs.getInt(1));
        }
    }
}
