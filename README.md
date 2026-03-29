# -achat-en-ligne-ms

![Work Status](https://img.shields.io/badge/work-completed-brightgreen.svg)
![Auteur](https://img.shields.io/badge/Groupeisi-Java-green)
![SpringBoot](https://img.shields.io/badge/Achat--en--Ligne-SpringBoot-yellowgreen)
![Java Version](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.9.6-blueviolet)
![Swagger](https://img.shields.io/badge/Swagger-UI-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![JWT](https://img.shields.io/badge/Auth-JWT-red)


# 🛒 Achat en Ligne — Microservice Spring Boot

# REQUIREMENTS
Prérequis pour démarrer le projet :

  Java 17

  Maven

  PostgreSQL 16

  Postman

---

## Clone du projet
:rocket:

```bash
git clone https://github.com/groupeisi/achat-en-ligne-ms.git
cd achat-en-ligne-ms
# Lancer l'application
mvn clean spring-boot:run
```

---

## Objectifs

```
Ce projet a pour but de se familiariser avec le développement d'une application
Spring Boot en architecture microservices, et de comprendre comment exposer et
gérer des entités via une API RESTful sécurisée par JWT.

Il permet de comprendre :
- Comment fonctionne une application Spring Boot avec microservices ?
- Comment mettre en place une architecture REST avec Spring Security et JWT ?
- Comment gérer les entités UserAccount, Produits, Achats et Ventes via une API REST ?
- Comment documenter une API avec Swagger UI ?
- Comment gérer les logs avec SLF4J / Logback ?
```

---

## Architecture

```
Client (Postman / Swagger UI)
        │
        ▼
  [Spring Security]  ←── JWT Filter (validation du token à chaque requête)
        │
        ▼
  [RestController]   ←── Couche HTTP / Endpoints REST
        │
        ▼
    [Service]        ←── Logique métier + Cache Caffeine + Logs SLF4J
        │
        ▼
    [Mapper]         ←── Conversion Entity ↔ DTO (MapStruct)
        │
        ▼
  [Repository]       ←── Spring Data JPA
        │
        ▼
  [PostgreSQL DB]
```

- **Spring Boot** + **Spring Data JPA**
- **PostgreSQL** pour la base de données
- **Spring Security** + **JWT** pour l'authentification
- **MapStruct** pour le mapping Entity ↔ DTO
- **Caffeine Cache** pour la mise en cache des données
- **Swagger UI** pour la documentation interactive
- **Postman** pour les tests API

---

## Structure du projet

```
achat-en-ligne-ms/
├── pom.xml
└── src/main/java/com/groupeisi/company/
    ├── AchatEnLigneMsApplication.java
    ├── entities/
    │   ├── UserAccount.java
    │   ├── Produits.java
    │   ├── Achats.java
    │   └── Ventes.java
    ├── dto/
    │   ├── UserAccountDto.java
    │   ├── ProduitsDto.java
    │   ├── AchatsDto.java
    │   ├── VentesDto.java
    │   ├── AuthRequest.java
    │   └── AuthResponse.java
    ├── mapper/
    │   ├── UserAccountMapper.java
    │   ├── ProduitsMapper.java
    │   ├── AchatsMapper.java
    │   └── VentesMapper.java
    ├── repository/
    │   ├── UserAccountRepository.java
    │   ├── ProduitsRepository.java
    │   ├── AchatsRepository.java
    │   └── VentesRepository.java
    ├── service/
    │   ├── UserAccountService.java
    │   ├── ProduitsService.java
    │   ├── AchatsService.java
    │   └── VentesService.java
    ├── controller/
    │   ├── AuthController.java
    │   ├── UserAccountController.java
    │   ├── ProduitsController.java
    │   ├── AchatsController.java
    │   └── VentesController.java
    └── config/
        ├── SecurityConfig.java
        ├── JwtUtil.java
        ├── JwtAuthenticationFilter.java
        ├── CustomUserDetailsService.java
        ├── SwaggerConfig.java
        └── GlobalExceptionHandler.java
```

---

## Entités et Repositories

Chaque entité a un repository pour accéder à la base de données :

- `UserAccountRepository`
- `ProduitsRepository`
- `AchatsRepository`
- `VentesRepository`

Les repositories manipulent uniquement les **Entities**.

### Modèle de données

 UserAccount | id (Long), email (String UNIQUE), password (String)   
 Produits    | ref (String PK), name, stock (double), user            
 Achats      | id (Long), dateP, quantity (double), product, user     
 Ventes      | id (Long), dateP, quantity (double), product, user     

### Relations

```
UserAccount  ──>  Produits
UserAccount  ──>  Achats
UserAccount  ──>  Ventes
Produits     ──>  Achats
Produits     ──>  Ventes
```

---

## Authentification JWT

```
1. POST /api/auth/register  →  Créer un compte
2. POST /api/auth/login     →  Obtenir un token JWT
3. Requêtes suivantes       →  Header: Authorization: Bearer <token>
```

Le token est valide **24 heures**. Toutes les routes hors `/api/auth/**` nécessitent un token valide.

---

## Endpoints principaux

### 🔓 Authentification (public)

- `POST /api/auth/register` → créer un compte utilisateur
- `POST /api/auth/login` → se connecter et obtenir le token JWT

### 👤 Users

- `GET /api/users` → liste tous les utilisateurs
- `GET /api/users/{id}` → utilisateur par id
- `PUT /api/users/{id}` → modifier un utilisateur
- `DELETE /api/users/{id}` → supprimer un utilisateur

### 📦 Produits

- `GET /api/produits` → liste tous les produits
- `GET /api/produits/{ref}` → produit par référence
- `GET /api/produits/user/{userId}` → produits d'un utilisateur
- `POST /api/produits` → créer un produit
- `PUT /api/produits/{ref}` → modifier un produit
- `DELETE /api/produits/{ref}` → supprimer un produit

### 🛒 Achats

- `GET /api/achats` → liste tous les achats
- `GET /api/achats/{id}` → achat par id
- `GET /api/achats/user/{userId}` → achats d'un utilisateur
- `GET /api/achats/produit/{ref}` → achats d'un produit
- `POST /api/achats` → créer un achat
- `PUT /api/achats/{id}` → modifier un achat
- `DELETE /api/achats/{id}` → supprimer un achat

### 💰 Ventes

- `GET /api/ventes` → liste toutes les ventes
- `GET /api/ventes/{id}` → vente par id
- `GET /api/ventes/user/{userId}` → ventes d'un utilisateur
- `GET /api/ventes/produit/{ref}` → ventes d'un produit
- `POST /api/ventes` → créer une vente
- `PUT /api/ventes/{id}` → modifier une vente
- `DELETE /api/ventes/{id}` → supprimer une vente

---

## Documentation API

- Swagger UI : `http://localhost:8080/swagger-ui/index.html`
 ![image1](img1.png)
  ![img2](img2.png)
  ![img3](img3.png)
  ![img4](img4.png)
 Pour tester les endpoints protégés dans Swagger :
 1. Appeler `POST /api/auth/login`
 2. Copier le token reçu
3. Cliquer sur **Authorize 🔒** en haut à droite
 4. Saisir `Bearer <votre_token>`

---

## Instructions pour tester

1. Cloner le projet
2. Créer la base de données PostgreSQL : `CREATE DATABASE achatdb;`
3. Configurer `application.yml` avec vos identifiants
4. Lancer l'application : `mvn clean spring-boot:run`
5. Tester les endpoints via :
   - Swagger UI
   - Postman

### POST /auth/register
```json
{
  "email": "user@example.com",
  "password": "monMotDePasse"
}
```

### POST /auth/login
```json
{
  "email": "user@example.com",
  "password": "monMotDePasse"
}
```
Réponse :
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "user@example.com"
}
```

### POST /produits
```json
{
  "ref": "PROD-001",
  "name": "Ordinateur portable",
  "stock": 50.0,
  "userId": 1
}
```

### POST /achats
```json
{
  "dateP": "2026-03-29",
  "quantity": 2.0,
  "productRef": "PROD-001",
  "userId": 1
}
```

### POST /ventes
```json
{
  "dateP": "2026-03-29",
  "quantity": 5.0,
  "productRef": "PROD-001",
  "userId": 1
}
```

---

## Cache Caffeine

| Annotation    | Rôle                                              |
|---------------|---------------------------------------------------|
| `@Cacheable`  | Met en cache les résultats de `findAll/findById`  |
| `@CachePut`   | Met à jour le cache après un `update`             |
| `@CacheEvict` | Vide le cache après un `save` ou `delete`         |

Configuration : **500 entrées max**, expiration après **10 minutes** d'inactivité.

---

## Technologies utilisées

- Java 17
- Spring Boot 3.2.2
- Spring Data JPA + Hibernate 6
- Spring Security + JWT (JJWT 0.11.5)
- PostgreSQL 16
- MapStruct 1.5.5
- Lombok 1.18.30
- Caffeine Cache
- Springdoc OpenAPI 2.3.0 (Swagger UI)
- Maven 3.x

---

## Contact

- **Nom** : Groupe ISI Company
- **Email** : contact@groupeisi.com
