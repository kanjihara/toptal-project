# Toptal Project

Spring Boot REST API for managing meals and orders. Data is persisted to JSON files on disk.

## Prerequisites

| Tool | Version |
|------|---------|
| Java | 24 |
| Maven | 3.9+ |

> **Note:** Java 25/26 causes `spring-boot:run` to fail due to an ASM incompatibility in Spring Boot 3.5.0. Use Java 24.

## Running the Application

```bash
mvn clean spring-boot:run
```

The server starts on **http://localhost:8080**.

## Building

```bash
# Compile
mvn compile

# Package as executable JAR
mvn package -DskipTests

# Run the JAR
java -jar target/toptal-project-1.0-SNAPSHOT.jar
```

## Running Tests

```bash
# All tests
mvn test

# Single test class
mvn test -Dtest=MainTest
```

## API

### Swagger UI

**http://localhost:8080/swagger-ui/index.html**

OpenAPI JSON spec: http://localhost:8080/v3/api-docs

### Meals

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/meals` | List all meals |
| `GET` | `/meals/{id}` | Get meal by ID |
| `POST` | `/meals` | Create a meal |
| `PUT` | `/meals/{id}` | Update a meal |
| `DELETE` | `/meals/{id}` | Delete a meal |

**Meal JSON structure:**
```json
{
  "id": 1,
  "user_id": "18",
  "name": "Pasta Carbonara",
  "calories": 383,
  "protein": 12.67,
  "date_consumed": "2022-09-25",
  "type": "lunch"
}
```

### Orders

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/orders` | List all orders |
| `GET` | `/orders/{id}` | Get order by ID |
| `POST` | `/orders` | Create an order |
| `PUT` | `/orders/{id}` | Update an order |
| `DELETE` | `/orders/{id}` | Delete an order |

**Order JSON structure:**
```json
{
  "order_id": 1,
  "customer_id": 1,
  "customer_name": "Russell Smith",
  "loyalty_points": 5,
  "order_items": [
    {
      "product_id": 10,
      "product_name": "Portable Charger",
      "product_category": "Electronics",
      "unit_price": 29.99
    }
  ]
}
```

## Data Persistence

Changes made via the API are written back to the JSON source files:

- `src/main/resources/meals.json`
- `src/main/resources/orders.json`

File paths are configured in `src/main/resources/application.properties` and are relative to the working directory. When running the packaged JAR from a different location, override them:

```bash
java -jar target/toptal-project-1.0-SNAPSHOT.jar \
  --data.meals-file=/absolute/path/to/meals.json \
  --data.orders-file=/absolute/path/to/orders.json
```
