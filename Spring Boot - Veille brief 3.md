---
tags:
  - Backend
  - Spring_Boot
---
## Qu'est-ce qu'un ORM ?
ORM (Object-Relational-Mapping) désigne une technique de programmation informatique qui crée un pont entre les bases de données relationnelles et le paradigme de la programmation orientée objet. 
L'ORM offre une couche d'abstraction qui permet de manipuler les objets et de les persister en base de données comme s'ils étaient des enregistrements de tables.

> [!INFO] Résumé
> Il facilite la gestion des bases de données dans les applications. Il automatise la conversion entre objets Java et données présentes dans des tables relationnelles.

## Qu'est-ce que Hibernate ?
Hibernate est un outil ORM très utilisé dans l'écosystème Java. Son rôle est de faire le pont entre deux mondes qui ne parlent pas le même langage :
```
   Java               SQL
──────────          ─────────
Classe        ←→    Table
Attribut      ←→    Colonne
Objet         ←→    Ligne
Long id       ←→    BIGINT id
```

Hibernate lit les annotations et comprend la structure :

```java
@Entity              // → cette classe = une table
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // → colonne BIGINT auto-increment

    @Column(nullable = false, length = 100)
    private String title;     // → colonne VARCHAR(100) NOT NULL
}
```
Hibernate lit ces annotations et sait exactement à quelle table et quelles colonnes correspondent  les objets.

> [!INFO] Résumé
> Hibernate est le traducteur entre le code Java et la base de données. On manipule des **objets Java**, lui s'occupe de tout ce qui est **SQL**. C'est pour ça qu'on l'appelle un ORM — il mappe le monde _objet_ au monde _relationnel_.

## Qu'est-ce que Spring Data JPA, et quel est son rapport avec Hibernate ?

### Spring Data JPA : la surcouche de confort
Spring Data JPA se pose au-dessus de JPA/Hibernate pour simplifier encore davantage la vie. Sans lui, même avec Hibernate, on devrait écrire ça :
```java
// Sans Spring Data JPA — avec Hibernate "nu"
EntityManager em = entityManagerFactory.createEntityManager();
em.getTransaction().begin();
Book book = em.find(Book.class, id);
em.getTransaction().commit();
em.close();
```
Avec Spring Data JPA, on écrit juste :
```java
bookRepository.findById(id);
```
C'est Spring Data JPA qui génère tout le code `EntityManager` , en analysant le nom des méthodes et en demandant à Hibernate d'exécuter la bonne requête SQL.
### JPA : juste un contrat
JPA n'est pas une librairie, c'est une **spécification** — un ensemble d'interfaces et de règles qui définissent comment un ORM doit fonctionner en Java. JPA seul ne fait rien, il ne contient aucune implémentation.
### Hibernate : le moteur
Hibernate est l'implémentation de JPA— c'est lui qui contient le vrai code qui :
- Traduit tes objets Java en requêtes SQL
- Gère le cache
- Mappe les lignes de ta table vers tes objets `@Entity`
- Gère les transactions

Quand on ajoute `spring-boot-starter-data-jpa` dans le `pom.xml`, Spring Boot embarque **Hibernate automatiquement** comme implémentation JPA par défaut.

## Qu'est-ce que JDBC, et quel est son rapport avec Hibernate ?
**JDBC (Java Database Connectivity)** est l'API de base de Java pour communiquer avec une base de données. C'est la couche la plus basse — le "câble" direct entre le code Java et la BDD.

Concrètement JDBC fait :
```java
// 1. Ouvrir une connexion à la BDD
Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/library", "root", "password"
);

// 2. Préparer une requête
PreparedStatement stmt = conn.prepareStatement(
    "SELECT * FROM book WHERE id = ?"
);

// 3. Exécuter la requête
stmt.setLong(1, id);
ResultSet rs = stmt.executeQuery();

// 4. Lire le résultat ligne par ligne et reconstruire l'objet à la main
if (rs.next()) {
    Book book = new Book();
    book.setId(rs.getLong("id"));
    book.setTitle(rs.getString("title"));
    book.setAvailable(rs.getBoolean("available"));
}

// 5. Tout fermer manuellement
rs.close();
stmt.close();
conn.close();
```

**La pile complète :** 
```
Le code Java
      │
Spring Data JPA   ← on travaille ici
      │
JPA / Hibernate   ← génère le SQL
      │
JDBC              ← envoie le SQL à la BDD
      │
Driver JDBC       ← spécifique à chaque BDD (mysql-connector, postgresql...)
      │
Base de données
```

JDBC est toujours là, même quand on utilise Hibernate. Hibernate ne le remplace pas, il s'en sert en dessous. Hibernate génère le SQL, puis délègue l'envoi à JDBC.
### JDBC vs Hibernate — les différences clés

|                        | JDBC                 | Hibernate                |
| ---------------------- | -------------------- | ------------------------ |
| SQL                    | On l'écrit soi même  | Généré automatiquement   |
| Mapping objet          | On le fait à la main | Automatique via @Entity  |
| Gestion connexions     | Manuelle             | Gérée via un pool        |
| Gestion transactions   | Manuelle             | Gérée via @Transactional |
| Quantité de code       | Beaucoup             | Très peu                 |
| Contrôle               | Total                | Partiel                  |
| Courbe d'apprentissage | Simple               | Plus complexe            |
Hibernate est parfait pour 95% des cas, mais JDBC garde un avantage : le **contrôle total**.
On peut préférer JDBC quand 
- on a des requêtes SQL très complexes qu'Hibernate gère mal
- on a des contraintes de performance extrêmes et qu'on veut optimiser chaque requête
- on travaille sur une base de données existante avec une structure difficile à mapper en objets.

> [!INFO] Résumé
> JDBC est le fondement _ sans lui, rien ne fonctionne. Hibernate est une abstraction au-dessus qui cache toute la complexité de JDBC. Dans un projet Spring Boot on n'écrit jamais de JDBC mais il tourne en permanence à chaque appel de du repository
