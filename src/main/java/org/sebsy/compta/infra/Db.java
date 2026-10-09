package org.sebsy.compta.infra;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public final class Db {

    private static final HikariDataSource DS = creer();

    private Db() {
    }

    private static HikariDataSource creer() {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(Env.required("DB_URL"));
        cfg.setUsername(Env.required("DB_USER"));
        cfg.setPassword(Env.required("DB_PASSWORD"));
        cfg.setPoolName("compta-pool");
        cfg.setMaximumPoolSize(10);
        cfg.setConnectionTimeout(3_000);
        cfg.setLeakDetectionThreshold(20_000);
        return new HikariDataSource(cfg);
    }

    public static DataSource dataSource() {
        return DS;
    }
}
