# Starter du TP 01

Ce dossier est votre point de départ. Copiez-le sous le nom `demo-data-access` et travaillez
dedans.

Il contient :

- `enonce.md` — l'énoncé du TP (dans l'archive ; en ligne, il a sa propre page) ;
- `env/` — la base PostgreSQL du module (`docker compose up -d`, voir `env/README.md`) ;
- `pom.xml` — toutes les dépendances du module, versions figées, commentées par chapitre ;
- `src/main/resources/logback.xml` — journalisation prête (SQL Hibernate à `DEBUG`, pool à `INFO`) ;
- `.gitignore`.

Il ne contient **aucune** classe Java : c'est à vous d'écrire `Env` et `Db`.
