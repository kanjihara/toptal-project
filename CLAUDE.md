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

This is a minimal Java console application (no web server, no Spring context). Despite using `spring-boot-starter` as a parent, no Spring components (`@Bean`, `@Service`, etc.) are wired. The entry point is `Main.main()`.

**Core pattern**: `Main` loads JSON files from the classpath via `readJsonResource()`, which uses a generic `ObjectMapper.readValue()` helper to deserialize into typed arrays. The resulting arrays are wrapped in `List` for use.

**Model layer** (`com.kanjih.toptal.model`):
- `Meal` — nutrition data: `id`, `userId`, `name`, `calories`, `protein`, `type`, `dateConsumed`. Snake_case JSON fields mapped with `@JsonProperty`.
- `Order` — customer order with `customerName` and a list of `OrderItem`s plus loyalty points.
- `OrderItem` — line item within an order.

**Data files** (`src/main/resources`):
- `meals.json` — array of meal objects
- `orders.json` — array of order objects

## Key Libraries

- **Jackson Databind** — JSON deserialization; `@JsonProperty` is used for snake_case → camelCase field mapping
- **Spring Boot 3.5.0 / Java 26** — dependency management only; no Spring features are actively used
- **JUnit 5** (via `spring-boot-starter-test`) — test runner
