# ATLAS

> Your Academic Operating System

[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/java-21-orange.svg)]()
[![Build: Maven](https://img.shields.io/badge/build-maven-C71A36.svg)]()

ATLAS is a personal operating system for students — one place to manage
academics, productivity, attendance, study sessions, assignments, goals,
expenses, and personal progress.

**Version 1** is a Java 21 CLI application built on a clean, layered
architecture designed to evolve into a Spring Boot + React SaaS platform.

## Vision

Every student should have a single trusted system for their academic life:
no scattered spreadsheets, no missed deadlines, no guesswork about where
their week went. ATLAS starts as a local CLI tool and grows into a platform
that works on the web and on mobile, backed by a personal cloud.

## Features

### Current (V1)

- Interactive CLI shell with a 9-item main menu (Dashboard, Subjects,
  Attendance, Study Sessions, Assignments, Goals, Expenses, Settings)
- Stack-based navigation system — every screen can push/back/exit, and
  screens communicate only through immutable navigation commands
- Clean layered architecture (UI / Service / Repository / Model) with
  `System.in/out` isolated behind interfaces — no Scanner in business logic
- Configuration loaded from `application.properties` with sane defaults
- Consistent presentation layer, graceful Ctrl+C / Ctrl+D shutdown
- Unit tests and a Maven build (Java 21)

### Planned

- Domain models + JSON repositories (local persistence in `data/`)
- Full CRUD for subjects, attendance, study sessions, assignments,
  goals, and expenses
- Dashboard aggregation and progress statistics
- SQLite storage, then a Spring Boot REST API
- React web app, mobile app, cloud sync and SaaS accounts

## Tech Stack

| Layer          | Technology            |
|----------------|-----------------------|
| Language       | Java 21               |
| Build          | Maven 3.9+            |
| Architecture   | Layered, SOLID        |
| Storage (V2)   | JSON files            |
| Storage (V3)   | SQLite                |
| UI (V1)        | CLI (terminal)        |
| UI (V5+)       | React / mobile        |
| Backend (V4+)  | Spring Boot           |

## Project Structure

```
atlas/
├── docs/                      # architecture & governance documentation
├── src/main/java/com/atlas/
│   ├── core/                  # application orchestration (composition root)
│   ├── config/                # configuration loading
│   ├── ui/                    # presentation layer (CLI shell, screens)
│   ├── model/                 # domain entities (V2+)
│   ├── service/               # business logic (V2+)
│   ├── repository/            # persistence (V2+)
│   ├── util/                  # shared helpers (theme, formatting)
│   ├── exception/             # application exception hierarchy
│   └── Main.java              # entry point
├── src/main/resources/        # application.properties
├── src/test/java/             # unit tests
├── data/                      # JSON storage files (created by the app)
├── assets/                    # branding & export templates
├── LICENSE                    # MIT
├── .editorconfig              # cross-editor style rules
├── .gitignore
└── pom.xml
```

## Roadmap

| Version | Focus |
|---------|-------|
| **V1**  | Foundation: CLI shell, navigation, layered architecture |
| **V2**  | Domain model + JSON repositories + CRUD for all modules |
| **V3**  | SQLite persistence, statistics and reporting |
| **V4**  | Spring Boot REST API |
| **V5**  | React web application |
| **V6**  | Mobile apps, accounts, cloud sync (SaaS) |

## Getting Started

### Prerequisites

- JDK 21+ (`java -version`)
- Maven 3.9+ (`mvn -version`)

### Install & Run

```bash
git clone https://github.com/<your-username>/atlas.git
cd atlas

mvn clean package        # compile, test, package
mvn exec:java            # run via Maven
java -jar target/atlas-1.0.0.jar   # run the packaged jar
```

Exit anytime with **Ctrl+C**, or choose **9. Exit** from the main menu.

### Tests

```bash
mvn test
```

## Future Plans

1. **V2** — real functionality: subjects, attendance tracking, study session
   timers, assignment deadlines, goals, and expenses persisted as JSON
2. **V3** — migrate storage to SQLite with analytics (attendance trends,
   study time, budget summaries)
3. **V4** — Spring Boot REST API with authentication
4. **V5** — React SPA consuming the API
5. **V6** — iOS/Android apps, multi-device sync, and a SaaS tier with
   accounts, backups and premium features

## License

This project is licensed under the [MIT License](LICENSE).
