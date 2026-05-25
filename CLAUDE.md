# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Run Commands

```bash
# Build
mvn clean install

# Run tests
mvn test

# Run a single test class
mvn test -Dtest=MainTest

# Run the application
mvn spring-boot:run
```

No Maven wrapper is present — use the system `mvn` directly.

## Architecture

This is a Spring Boot web application exposing REST endpoints for meals and orders. The entry point is `Main` (annotated with `@SpringBootApplication`), which boots a full Spring context and an embedded Tomcat server.

**Layers**:
- **Controllers** (`com.kanjih.toptal.controller`) — `MealController` (`/meals`) and `OrderController` (`/orders`) expose CRUD endpoints (`GET`, `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}`) and return `ResponseEntity` with appropriate status codes.
- **Service** (`com.kanjih.toptal.service.DataService`) — `@Service` holding meals and orders in-memory (`LinkedHashMap` keyed by id) with `AtomicInteger` id sequences. Loaded on startup via `@PostConstruct` from JSON files; every mutation rewrites the JSON file on disk via Jackson's pretty printer.
- **Model** (`com.kanjih.toptal.model`):
  - `Meal` — nutrition data: `id`, `userId`, `name`, `calories`, `protein`, `type`, `dateConsumed`. Snake_case JSON fields mapped with `@JsonProperty`.
  - `Order` — customer order with `customerName` and a list of `OrderItem`s plus loyalty points.
  - `OrderItem` — line item within an order.
- **Config** (`com.kanjih.toptal.config.OpenApiConfig`) — `@Configuration` exposing an `OpenAPI` bean (title/version/description) for springdoc.

**Data files** (paths configured in `application.properties`, default to `src/main/resources/`):
- `meals.json` — array of meal objects (read on startup, rewritten on each mutation)
- `orders.json` — array of order objects (same behavior)

**Endpoints**:
- REST API: `http://localhost:8080/meals`, `http://localhost:8080/orders`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI spec: `http://localhost:8080/v3/api-docs`

Note: `OldMain.java` is kept as a reference of the previous console-only version and is not wired into the Spring context.

## Key Libraries

- **Spring Boot 3.5.0** (`spring-boot-starter-web`) — embedded Tomcat, Spring MVC, JSON via Jackson
- **springdoc-openapi 2.6.0** (`springdoc-openapi-starter-webmvc-ui`) — auto-generates the OpenAPI spec from controllers and serves Swagger UI
- **Jackson Databind** — JSON (de)serialization; `@JsonProperty` for snake_case ↔ camelCase mapping
- **JUnit 5 + Spring Boot Test** (`spring-boot-starter-test`) — `@SpringBootTest` / `@WebMvcTest` style tests for the controllers and service
