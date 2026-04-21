## Qu'est ce qu'une entité?
Une entité est une classe Java qui représente un objet métier persisté en base de données. Elle fait le lien entre le monde objet (Java) et le monde relationnel (SQL).
Chaque instance d'une entité correspond à une **ligne** dans une table de base de données.

### Caractéristiques d'une entité

|Caractéristique|Description|
|---|---|
|Annotée `@Entity`|Indique à JPA que la classe est une entité persistante|
|Possède un identifiant|Un champ annoté `@Id` servant de clé primaire|
|Mappée à une table|Par défaut, le nom de la classe correspond au nom de la table|
|Sérialisable|Implémente souvent `Serializable`|
### Exemple d'entité
```java
import jakarta.persistence.*;

@Entity @Table(name = "utilisateurs") 
public class Utilisateur { 
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; @Column(nullable = false) 
	private String nom; 
	@Column(unique = true, nullable = false) 
	private String email; 

// Constructeurs, getters, setters... }
```
### Annotations courantes

|Annotation|Rôle|
|---|---|
|`@Entity`|Déclare la classe comme entité JPA|
|`@Table(name = "...")`|Spécifie le nom de la table en base|
|`@Id`|Désigne la clé primaire|
|`@GeneratedValue`|Définit la stratégie de génération de l'ID|
|`@Column`|Configure les propriétés d'une colonne (nom, contraintes…)|
|`@OneToMany` / `@ManyToOne`|Gère les relations entre entités|
|`@ManyToMany`|Relation de plusieurs à plusieurs|
### Relations entre entités

Les entités peuvent être reliées entre elles pour refléter les associations de la base de données :
```java

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Plusieurs commandes pour un même utilisateur
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
}
```

## Qu'est-ce qu'une architecture multicouche ?

Une architecture multicouche est un modèle d'organisation du code où l'application est divisée en couches distinctes, chacune ayant un rôle précis et communiquant uniquement avec la couche adjacente.
L'indépendance de chaque couche facilite les modifications individuelles, contrairement à une intervention sur l'ensemble de l'architecture.

Les 4 couches classiques : 
- Controller : Reçoit les requêtes HTTP, renvoie les réponses
- Service : Contient la logique métier
- Repository : Communique avec la base de données
- Model : Représente et stocke les données
## Comment Spring Boot met-il en place une architecture multicouche?
Spring Boot encourage une organisation du code en couches séparées, chacune ayant une responsabilité unique. Cette approche facilite la maintenabilité, la testabilité et l'évolutivité de l'application. 

```

┌─────────────────────────────────────┐
│          Couche Présentation        │  ← @RestController / @Controller
│     (API REST ou interface web)     │
├─────────────────────────────────────┤
│          Couche Service             │  ← @Service
│        (Logique métier)             │
├─────────────────────────────────────┤
│         Couche Repository           │  ← @Repository / JpaRepository
│      (Accès aux données)            │
├─────────────────────────────────────┤
│          Couche Entité              │  ← @Entity
│      (Modèle de données)            │
├─────────────────────────────────────┤
│          Base de données            │  ← PostgreSQL, MySQL, H2…
└─────────────────────────────────────┘

```

### Couche 1 : Entité
Représente les données de l'application.
**Annotation : `@Entity`**
### Couche 2 : Repository
Gère les interactions avec la base de données via Spring Data JPA
`JpaRepository` fournit nativement des méthodes comme `findAll()`, `findById()`, `save()`, `delete()`, etc.
**Annotation : `@Repository`** 
### Couche 3 : Service
Contient la logique de l'application. C'est ici que les règles métier sont appliquées. 
**Annotation :** `@Service
### Couche 4 : Controller
Expose les endpoints HTTP et reçoit les requêtes des clients.
**Annotation : `@RestController

Spring Boot va gérer les instances de classe via son conteneur IoC (Inversion of Control).
Les annotations permettent à Spring de :
- Détecter la classe au démarrage (component scan)
- Instancier un objet de cette classe (appelé Bean)
- L'enregistrer dans son conteneur
- L'injecter partout où elle est nécessaire
C'est ce qu'on appelle l'injection de dépendances.

**Au démarrage :**
```
@SpringBootApplication
  │
  ├── @EnableAutoConfiguration  → configure automatiquement JPA, Tomcat, etc.
  ├── @ComponentScan            → scanne tous les packages à la recherche de @Service, @Repository, @Controller...
  └── @Configuration            → charge les beans de configuration
```

`@RestController` -> Spring enregistre la classe comme handler HTTP via le DispatcherServlet.
`@Repository` -> Spring enregistre un bean + active la translation d'exceptions SQL en exceptions Spring
`@Service` -> Spring enregistre un bean dans le conteneur, éligible pour la gestion des transactions.
`@Entity` + `@Repository` -> Spring Data génère automatiquement toute l'implémentation SQL