# Accounts Service

A microservice for managing bank accounts in a banking system. This service handles account creation, balance inquiries, and transaction history retrieval.

## Overview

The Accounts Service is responsible for:
- Managing bank account information
- Providing account balance inquiries
- Retrieving account transaction history
- Integrating with the Accounts Ledger service for transaction events
- Communicating with other microservices via service discovery

## Technology Stack

- **Java**: 17
- **Spring Boot**: 4.0.6
- **Spring Cloud**: 2025.1.1
- **Database**: H2 (In-memory)
- **Service Discovery**: Netflix Eureka
- **Configuration**: Spring Cloud Config
- **API Communication**: OpenFeign
- **Build Tool**: Maven
- **Containerization**: Jib (Google Cloud Tools)

## Features

- **Account Management**: Create and manage bank accounts
- **Balance Inquiry**: Query current account balance
- **Transaction History**: Retrieve recent account transactions
- **Service Discovery**: Auto-registration with Eureka server
- **Centralized Configuration**: Integration with Spring Cloud Config Server
- **Health Monitoring**: Spring Boot Actuator endpoints
- **Database Console**: H2 Console for database inspection
- **Inter-service Communication**: Feign client for Accounts Ledger service integration
- **Docker Support**: Jib plugin for container image building

## Configuration

### Application Properties

- **Server Port**: 8080
- **Service Name**: accounts
- **Database**: H2 in-memory database (testdb)
- **H2 Console**: Enabled at `/h2-console`
- **Eureka Server**: http://localhost:8070/eureka/
- **Config Server**: http://localhost:8071/

### Database Schema

The service uses an H2 database with the following schema:

```sql
CREATE TABLE account (
    accountId varchar(100) NOT NULL PRIMARY KEY,
    amount double NOT NULL,
    currency varchar(100) NOT NULL,
    eventTimestamp varchar(100) NOT NULL
);
```

## API Endpoints

### GET /{accountId}/transactions - Get Account Transactions

Retrieves transaction history for a specific account.

**Path Parameters:**
- `accountId` (string): The account identifier

**Request Body:**
```json
{
  "accountId": "string",
  "balance": "number",
  "currency": "string"
}
```

**Response:** `200 OK`

### GET /{accountId}/balance - Get Current Balance

Retrieves the current balance for a specific account.

**Path Parameters:**
- `accountId` (string): The account identifier

**Response:** `200 OK` with balance information

### GET /{accountId} - Get Account Details

Retrieves account details and recent transactions.

**Path Parameters:**
- `accountId` (string): The account identifier

**Response:** `200 OK` with account details and transaction history

## Project Structure

```
accounts/
├── src/main/java/com/cloud/accounts/
│   ├── controller/
│   │   └── AccountsController.java
│   ├── dto/
│   │   └── AccountsDto.java
│   ├── entity/
│   │   ├── Accounts.java
│   │   └── AccountsLedger.java
│   ├── mapper/
│   │   └── AccountsMapper.java
│   ├── repository/
│   │   └── AccountRepository.java
│   ├── service/
│   │   ├── AccountsService.java
│   │   ├── impl/
│   │   │   └── AccountsServiceImpl.java
│   │   └── client/
│   │       └── AccountsLedgerFeignClient.java
│   └── AccountsApplication.java
├── src/main/resources/
│   ├── application.yml
│   └── schema.sql
└── pom.xml
```

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Eureka Server running on port 8070
- Config Server running on port 8071 (optional)
- Accounts Ledger service running on port 8081

### Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### Docker Build

```bash
# Build Docker image using Jib
mvn jib:build
```

The image will be tagged as `cloud/accounts:R1`

## Actuator Endpoints

The following actuator endpoints are available:

- `/actuator/health` - Health check
- `/actuator/info` - Application information
- `/actuator` - All available endpoints

## H2 Console

Access the H2 database console at:
```
http://localhost:8080/h2-console
```

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## Dependencies

Key dependencies include:
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Actuator
- Spring Boot Starter Validation
- Spring Cloud Starter Config
- Spring Cloud Starter Netflix Eureka Client
- Spring Cloud Starter OpenFeign
- H2 Database
- Lombok
- Account Ledger (internal module dependency)

## Service Integration

The service integrates with:
- **Eureka Server**: For service discovery and registration
- **Config Server**: For centralized configuration management
- **Accounts Ledger Service**: Via Feign client (AccountsLedgerFeignClient) for transaction event data

## Data Models

### Accounts Entity

```java
{
  "accountId": "string (primary key)",
  "balance": "number",
  "currency": "string"
}
```

### Accounts DTO

```java
{
  "eventId": "string",
  "accountId": "string",
  "type": "string",
  "amount": "number",
  "currency": "string"
}
```

## Development

### Adding New Features

1. Add new endpoints in `AccountsController`
2. Implement business logic in `AccountsServiceImpl`
3. Update DTOs and entities as needed
4. Add repository methods in `AccountRepository`
5. Extend Feign client for additional service communication

### Service Communication

The service uses OpenFeign for declarative REST client communication with the Accounts Ledger service. The `AccountsLedgerFeignClient` interface defines the contract for inter-service communication.

## Status

This service is currently under development. The service implementation contains stub methods that need to be completed with actual business logic for:
- Account creation and management
- Balance calculation
- Transaction history retrieval
- Integration with Accounts Ledger service

## License

This project is part of a microservices banking application.

## Author

Kumar Thirunavukarasu
