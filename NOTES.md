# NOTES

## TP 01
- Question pom : 1 dependance concerne JDBC (le pilote mariadb-java-client), 0 concernent l'API JDBC (java.sql est dans le JDK).
- Item 8, classe de la connexion : com.zaxxer.hikari.pool.HikariProxyConnection (mandataire du pool, pas java.sql.Connection).
- Item 9, conteneur arrete : echec apres ... ms. Message : ... Le delai vient du connectionTimeout du pool (3 s) : le pool attend avant d'abandonner.
- Item 10, DB_PASSWORD absente : ExceptionInInitializerError: Variable d'environnement DB_PASSWORD absente. Le message est comprehensible : il nomme la variable. Le wrapper vient du champ static de Db, qui cree le pool au chargement de la classe.
