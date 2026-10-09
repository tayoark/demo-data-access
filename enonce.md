# TP 01 — Connexion & DataSource

> Module *Java · Accès aux données* · Chapitre 1 · Durée : **45 min** (30h) · **45 min** (28h) · **40 min** (20h) · Niveau : débutant
> Statut : **obligatoire** dans les trois volumes (30h / 28h / 20h)

## Objectifs d'apprentissage

À l'issue de ce TP, vous êtes capable de :

- créer un projet Maven ciblant le JDK 25 et y déclarer un pilote JDBC ;
- expliquer pourquoi le pilote est une **dépendance** alors que l'API JDBC n'en est pas une ;
- construire un `DataSource` HikariCP unique, configuré **par variables d'environnement** ;
- ouvrir et refermer une connexion sans fuite, avec `try-with-resources` ;
- constater qu'aucune ligne de votre code ne nomme MariaDB en dehors de l'URL.

## Contexte

La quincaillerie dispose d'une base `compta` — celle que vous avez modélisée en cours de SQL.
Elle tourne dans un conteneur MariaDB 12.3. Votre mission de la semaine : bâtir la couche
d'accès aux données de son application de gestion. Aujourd'hui, on se contente d'établir le
contact — mais proprement, parce que tout le reste s'empilera dessus.

## Prérequis

- JDK **25** et Maven **3.9+** (`java -version`, `mvn -version`)
- Docker en fonctionnement
- L'environnement démarré : `cd env && docker compose up -d` (voir `env/README.md`)
- Git configuré

## Instructions

### 1 — Le projet

1. Créez un projet Maven `demo-data-access`, `groupId` `org.sebsy`, à partir de
   `projet-starter/` (pom, `logback.xml`, `.gitignore` fournis).
2. `git init`, premier commit. Créez un dépôt **privé** sur GitHub et ajoutez `ssy-sdv`
   comme collaborateur.
3. Vérifiez que `mvn -q compile` passe.

> **Regardez le `pom.xml` avant de continuer.** Combien de dépendances concernent JDBC ?
> Combien concernent l'**API** JDBC ? Sachez répondre : c'est une question d'examen.

### 2 — Les identifiants

4. Déclarez trois variables d'environnement dans la **configuration d'exécution de votre
   IDE** (IntelliJ : *Run > Edit Configurations > Environment variables*) :

   ```
   DB_URL=jdbc:mariadb://localhost:3306/compta?useServerPrepStmts=true&connectionTimeZone=SERVER
   DB_USER=app
   DB_PASSWORD=app
   ```

> Une variable exportée dans un terminal **n'est pas** visible d'une exécution lancée depuis
> l'IDE. C'est l'écueil numéro un de ce TP.

### 3 — Le DataSource

5. Créez `org.sebsy.compta.infra.Env` avec une méthode `required(String)` qui lit une variable
   d'environnement et **échoue avec un message utile** si elle est absente.
6. Créez `org.sebsy.compta.infra.Db` exposant un `DataSource` **unique** pour toute
   l'application. Réglez au minimum :
   - `maximumPoolSize` — une valeur choisie, pas le défaut ;
   - `connectionTimeout` — échouer vite plutôt que pendre ;
   - `leakDetectionThreshold` — c'est ce paramètre qui vous sauvera au TP 03.

<details>
<summary>Indice 1 — quelle classe instancier ?</summary>

`HikariConfig` porte la configuration, `HikariDataSource` est le pool. Le second se construit
à partir du premier, une seule fois.
</details>

<details>
<summary>Indice 2 — « une seule fois », concrètement ?</summary>

Un champ `static final` initialisé par une méthode privée. Le projet n'a pas de conteneur
d'injection : inventer une abstraction ici serait de la cérémonie sans bénéfice.
</details>

### 4 — Le premier aller-retour

7. Écrivez une classe exécutable qui :
   - emprunte une connexion au pool, dans un `try-with-resources` ;
   - affiche `connexion.getClass().getName()` et `getMetaData().getDatabaseProductVersion()` ;
   - exécute `SELECT COUNT(*) FROM fournisseur` et affiche le résultat.

8. **Observez le nom de classe affiché.** Ce n'est pas `java.sql.Connection` : c'est une classe
   du pilote, atteinte par polymorphisme. Notez-le, on s'en resservira.

### 5 — La preuve par l'erreur

9. Arrêtez le conteneur (`docker compose stop db`), relancez votre programme. Lisez l'exception
   **en entier** : combien de temps s'écoule avant l'échec, et pourquoi ce délai ?
10. Redémarrez le conteneur. Supprimez `DB_PASSWORD` de la configuration : quelle est l'erreur,
    et est-elle compréhensible ?

## Livrables

- Code poussé sur la branche `main` du dépôt privé.
- Un commit par étape logique, messages en français, à l'impératif.
- Critère de « terminé » : `mvn -q compile` passe, et la classe exécutable affiche la version
  de MariaDB puis le nombre de fournisseurs (**4**).

## Pour aller plus loin (optionnel)

- Ajoutez `?connectTimeout=2000` à l'URL et mesurez l'écart avec la question 9.
- Faites afficher par HikariCP la taille de son pool (le logger `com.zaxxer.hikari` est déjà à
  `INFO` dans le `logback.xml` fourni). Combien de connexions sont ouvertes **avant** votre
  première requête, et pourquoi ?
