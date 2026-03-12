# 📚 LibraryHub

> **EN** — A full-stack web application to manage your personal book library. Track your reading status, write reviews, and explore your reading statistics.
>
> **IT** — Un'applicazione web full-stack per gestire la tua libreria personale. Tieni traccia dello stato di lettura, scrivi recensioni ed esplora le tue statistiche.

---

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?style=flat&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-9.4-4479A1?style=flat&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36?style=flat&logo=apache-maven&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.x-005F0F?style=flat&logo=thymeleaf&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-7952B3?style=flat&logo=bootstrap&logoColor=white)

---

## 🖼️ Screenshots

> *Screenshots will be added once the frontend is completed.*
> 
> *Gli screenshot verranno aggiunti al completamento del frontend.*

---

## ✨ Features / Funzionalità

**EN**
- 📖 Add books to your personal library via ISBN
- 🔖 Track reading status: `TO READ`, `READING`, `READ`
- ⭐ Write and edit personal reviews with star ratings (1–5)
- 📊 View personal reading statistics on your dashboard
- 🔒 Secure authentication with JWT

**IT**
- 📖 Aggiungi libri alla tua libreria tramite ISBN
- 🔖 Traccia lo stato di lettura: `DA LEGGERE`, `IN LETTURA`, `LETTO`
- ⭐ Scrivi e modifica recensioni personali con valutazione a stelle (1–5)
- 📊 Visualizza statistiche di lettura personali nella tua dashboard
- 🔒 Autenticazione sicura con JWT

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5 |
| Build Tool | Maven |
| Database | MySQL 9.4 |
| ORM | Spring Data JPA + Hibernate |
| Migrations | Flyway |
| Security | Spring Security + JWT (jjwt) |
| Frontend | Thymeleaf + Bootstrap 5 |
| Testing | JUnit 5 + Mockito + JaCoCo |

---

## 🚀 Getting Started / Avvio in locale

### Prerequisites / Prerequisiti

- Java 21+
- Maven 3.x
- MySQL 9.x

### 1. Clone the repository / Clona il repository

```bash
git clone https://github.com/FrancescoGiannulo/libraryhub.git
cd libraryhub
```

### 2. Configure the database / Configura il database

**EN** — Create the MySQL database:

**IT** — Crea il database MySQL:

```sql
CREATE DATABASE libraryhub;
```

### 3. Configure application properties / Configura le proprietà

**EN** — The project uses Spring profiles. For local development, the `dev` profile is pre-configured with:

**IT** — Il progetto usa i profili Spring. Per lo sviluppo locale, il profilo `dev` è già configurato con:

```yaml
# src/main/resources/application-dev.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/libraryhub
    username: root
    password: root
```

> ⚠️ **EN** Change credentials if your local MySQL setup is different.
> 
> ⚠️ **IT** Modifica le credenziali se la tua configurazione MySQL locale è diversa.

### 4. Run the application / Avvia l'applicazione

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**EN** — The application will be available at `http://localhost:8080`

**IT** — L'applicazione sarà disponibile su `http://localhost:8080`

---

## 📖 API Documentation / Documentazione API

**EN** — Once the application is running, Swagger UI is accessible at:

**IT** — Una volta avviata l'applicazione, Swagger UI è disponibile su:

```
http://localhost:8080/swagger-ui.html
```

---

## 🌐 API Endpoints Overview

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/auth/register` | Register a new user |
| `POST` | `/api/auth/login` | Login and get JWT token |

### Books
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/books/search?isbn=` | Search book by ISBN |
| `POST` | `/api/books` | Add book to personal library |
| `GET` | `/api/books/my` | Get user's books |
| `PATCH` | `/api/books/{id}/status` | Update reading status |
| `DELETE` | `/api/books/{id}` | Remove book from library |

### Reviews
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/reviews` | Add a review |
| `PUT` | `/api/reviews/{id}` | Update a review |
| `GET` | `/api/reviews/book/{bookId}` | Get reviews for a book |

### Stats
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/stats/me` | Get personal reading statistics |

---

## 🗄️ Database Schema

![ER Diagram](docs/er-diagram.png)

---

## 🧪 Running Tests / Eseguire i test

```bash
mvn test
```

**EN** — Test coverage report (JaCoCo) is generated at `target/site/jacoco/index.html`.

**IT** — Il report di copertura test (JaCoCo) viene generato in `target/site/jacoco/index.html`.

---

## 📁 Project Structure / Struttura del progetto

```
src/main/java/com/libraryhub
├── config/          # Security & app configuration
├── controller/      # REST controllers
├── dto/             # Data Transfer Objects
├── entity/          # JPA entities
├── exception/       # Custom exceptions & global handler
├── repository/      # Spring Data JPA repositories
├── security/        # JWT filter & utils
├── service/         # Business logic
└── LibraryHubApplication.java
```

---

## 👤 Author / Autore

**Francesco Giannulo**

[![GitHub](https://img.shields.io/badge/GitHub-FrancescoGiannulo-181717?style=flat&logo=github)](https://github.com/FrancescoGiannulo)

---

## 📄 License

This project is for educational and portfolio purposes.
