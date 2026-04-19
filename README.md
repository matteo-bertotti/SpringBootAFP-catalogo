# Catalogo Film - Spring Boot

Applicazione web MVC per la gestione di un catalogo film, sviluppata con Spring Boot, Thymeleaf e MySQL.

Permette di:
- visualizzare l'elenco dei film
- ordinare il catalogo per campi principali
- aggiungere un nuovo film
- modificare un film esistente
- eliminare un film singolo
- eliminare tutti i film

## Stack Tecnologico

- Java 21
- Spring Boot 4.0.5
- Spring Web MVC
- Spring Data JPA
- Thymeleaf
- Bean Validation (Jakarta Validation)
- MySQL
- Maven Wrapper
- Docker Compose (per database MySQL)

## Struttura del Progetto

```text
catalogo/
	src/main/java/it/tn/mat/catalogo/
		controller/      -> Controller MVC (rotte e viste)
		domain/          -> Entita, DTO form, enum
		repositories/    -> Repository JPA
		services/        -> Logica applicativa
	src/main/resources/
		templates/       -> Pagine Thymeleaf
		static/css/      -> Stili CSS
		application.properties
	docker-compose.yml
	pom.xml
```

## Requisiti

- JDK 21
- Docker + Docker Compose (opzionale ma consigliato per MySQL)

## Configurazione

Le proprieta principali sono in `catalogo/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://${DB_HOST:localhost}:3306/${DB_NAME:catalogo_db}
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASSWORD:root}

spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
spring.jpa.show-sql=true
```

Variabili ambiente supportate:
- `DB_HOST` (default: `localhost`)
- `DB_NAME` (default: `catalogo_db`)
- `DB_USER` (default: `root`)
- `DB_PASSWORD` (default: `root`)

## Avvio Rapido

### 1) Avvia il database MySQL (Docker)

Da root progetto:

```bash
cd catalogo
docker compose up -d
```

Questo avvia MySQL sulla porta `3306` con:
- database: `catalogo_db`
- utente: `root`
- password: `root`

### 2) Avvia l'applicazione


L'app sara disponibile su:
- `http://localhost:8080`

## Rotte Principali

- `GET /` elenco film
	- query params opzionali:
		- `sort`: `titolo` (default), `autore`, `genere`, `anno`
		- `dir`: `asc` (default), `desc`
- `GET /new` form inserimento film
- `POST /new` creazione nuovo film
- `GET /film/{id}` dettaglio film
- `GET /film/edit/{id}` form modifica film
- `POST /film/edit/{id}` salvataggio modifica
- `GET /film/delete/{id}` elimina film singolo
- `GET /film/deleteAll` elimina tutti i film

## Modello Dati

Entita principale: `Film`
- `id: UUID`
- `titolo: String`
- `autore: String`
- `genere: String`
- `annoPubblicazione: int`

Form object: `FilmForm` con validazioni:
- `titolo`: obbligatorio, max 50 caratteri
- `autore`: obbligatorio, max 50 caratteri
- `genere`: obbligatorio (enum `Genere`)
- `annoPubblicazione`: min 1888, max 2100
