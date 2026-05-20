# GuidanceD20

A D&D 5e companion web application built with Spring Boot. Browse class/subclass wikis, manage character sheets, look up spells, and more.

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.2** (Web, Thymeleaf, Data JPA)
- **H2 Database** (file-based, embedded — no external DB required)
- **Lombok**
- **Maven** (wrapper not included — requires a local install)
- **Docker** (optional)

---

## Prerequisites

Pick **one** of the two paths below:

| Path | You need installed |
|---|---|
| **Run with Docker** | Docker & Docker Compose |
| **Run locally** | Java 21 JDK, Maven 3.9+ |

---

## Running with Docker

This is the fastest way: no Java or Maven install needed on the host.

```bash
# 1. Clone the repo
git clone https://github.com/<your-username>/GuidanceD20.git
cd GuidanceD20

# 2. Build and start the container
docker compose up --build
```

The app will be available at **http://localhost:8080**.

To stop it:

```bash
docker compose down
```

---

## Running Locally

```bash
# 1. Clone the repo
git clone https://github.com/<your-username>/GuidanceD20.git
cd GuidanceD20

# 2. Build the project
mvn clean package -DskipTests

# 3. Run the JAR
java -jar target/guidance-d20-0.0.1-SNAPSHOT.jar
```

The app will be available at **http://localhost:8080**.

> **Tip:** During development you can also run `mvn spring-boot:run` for automatic restarts.

---

## Configuration

The application properties file is located at:

```
src/main/resources/templates/application.proprieties
```

Key defaults:

| Property | Default | Notes |
|---|---|---|
| `spring.datasource.url` | `jdbc:h2:file:/app/data/guidancedb` | H2 file DB path (inside Docker). Change to a local path (e.g. `jdbc:h2:file:./data/guidancedb`) when running outside Docker. |
| `spring.datasource.username` | `sa` | |
| `spring.datasource.password` | *(empty)* | |
| `spring.jpa.hibernate.ddl-auto` | `update` | Auto-creates/updates tables on startup |
| `spring.h2.console.enabled` | `true` | Accessible at `/h2-console` |

> **Note for local runs:** If you see a file-permission error on startup, update the datasource URL to a writable local path like `jdbc:h2:file:./data/guidancedb`.

---

## Available Pages

Once the app is running, you can visit:

| URL | Description |
|---|---|
| `/` | Home / landing page |
| `/signup` | Create a new account |
| `/login` | Log in to an existing account |
| `/dashboard` | User dashboard (logged in) |
| `/my-characters` | View your saved character sheets |
| `/character-sheet` | Create or edit a character sheet |
| `/spell-helper` | Browse and filter D&D spells |
| `/wiki` | Class & subclass wiki index |
| `/wiki/class/{className}` | Detailed page for a specific class |
| `/h2-console` | H2 database web console (dev tool) |

---

## Project Structure

```
GuidanceD20/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── src/main/
    ├── java/org/example/
    │   ├── Main.java                  # Spring Boot entry point
    │   ├── WebController.java         # Main page routes
    │   ├── WikiController.java        # Wiki routes
    │   ├── DatabaseSeeder.java        # Seeds D&D data on startup
    │   ├── UserService.java           # User registration & login
    │   ├── CharacterSheet.java        # Character sheet entity
    │   ├── DndClass.java              # D&D class entity
    │   ├── DndClassProgression.java   # Class level progression
    │   ├── DndSubclass.java           # Subclass entity
    │   ├── Spell.java                 # Spell entity
    │   ├── DndAction.java             # Action entity
    │   └── *Repository.java           # Spring Data JPA repositories
    └── resources/templates/
        ├── application.proprieties     # App configuration
        ├── dashboard.html
        ├── login.html
        ├── signup.html
        ├── character-sheet.html
        ├── my-characters.html
        ├── spell-helper.html
        ├── wiki-index.html
        └── wiki-class.html
```

---

## Troubleshooting

**"Port 8080 already in use"** — Stop whatever is using the port, or change the mapping in `docker-compose.yml` (e.g. `"9090:8080"`) or add `server.port=9090` to the properties file.

**H2 database lock error** — Only one process can hold the H2 file lock at a time. Make sure you don't have a second instance running.

**`application.proprieties` not found at runtime** — The filename contains a typo (`proprieties` instead of `properties`). Spring Boot auto-discovers `application.properties` by convention, so if the app can't find its config, rename the file to `application.properties` and move it to `src/main/resources/` (one level up, out of the `templates/` folder).
