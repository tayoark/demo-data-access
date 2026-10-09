package org.sebsy.compta.dao.jdbc;

import org.sebsy.compta.dao.PersistenceFailure;
import org.sebsy.compta.infra.Db;
import java.sql.Connection;
import java.sql.SQLException;

public final class Tx {

    private Tx() {
    }

    @FunctionalInterface
    public interface SqlWork<R> {
        R apply(Connection connection) throws SQLException;
    }

    static <R> R with(String description, SqlWork<R> work) {
        try (Connection cnx = Db.dataSource().getConnection()) {
            return work.apply(cnx);
        } catch (SQLException e) {
            throw new PersistenceFailure(description + " failed", e);
        }
    }
}
