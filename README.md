# Bill Management System API

Enterprise RESTful service for billing, customer invoicing, and transaction record management.

## Features
- Invoicing and billing lifecycle management (Draft, Issued, Paid, Overdue).
- Customer account linking and payment history tracking.
- JPA / Hibernate relational persistence with MySQL.

## Tech Stack
- Java 21 LTS
- Spring Boot, Spring Data JPA
- MySQL 8.0
- Maven 3.9+

## Configuration
Copy `.env.example` or configure database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/bill_management_db}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:root}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:root}
```

## Getting Started
```bash
./mvnw clean compile
./mvnw spring-boot:run
```
