# Accounts Ledger Service

A microservice for managing account ledger events in a banking system. This service handles the recording and retrieval of financial transaction events (credits and debits) for bank accounts.

## Overview

The Accounts Ledger Service is responsible for:
- Recording financial transaction events (CREDIT/DEBIT)
- Storing transaction metadata and timestamps
- Providing APIs to query ledger events
- Integrating with other microservices via service discovery

## Technology Stack

- **Java**: 17
- **Spring Boot**: 4.0.6
- **Spring Cloud**: 2025.1.1
- **Database**: H2 (In-memory)
- **Service Discovery**: Netflix Eureka
- **Configuration**: Spring Cloud Config
- **Build Tool**: Maven
- **Containerization**: Jib (Google Cloud Tools)

## Features

- **Event Recording**: POST API to record credit/debit events
- **Event Querying**: GET APIs to retrieve events by ID or account ID
- **Service Discovery**: Auto-registration with Eureka server
- **Centralized Configuration**: Integration with Spring Cloud Config Server
- **Health Monitoring**: Spring Boot Actuator endpoints
- **Database Console**: H2 Console for database inspection
- **Docker Support**: Jib plugin for container image building

## Configuration

### Application Properties

- **Server Port**: 8081
- **Service Name**: accountsledger
- **Database**: H2 in-memory database (testdb)
- **H2 Console**: Enabled at `/h2-console`
- **Eureka Server**: http://localhost:8070/eureka/
- **Config Server**: http://localhost:8071/

### Database Schema

The service uses an H2 database with the following schema:

```sql
CREATE TABLE accountsledger (
    eventId varchar(100) NOT NULL PRIMARY KEY,
    accountId varchar(100) NOT NULL,
    type varchar(100) NOT NULL,
    amount double NOT NULL,
    currency varchar(100) NOT NULL,
    eventTimestamp varchar(100) NOT NULL,
    batchId varchar,
    source varchar
);
```

## API Endpoints

### POST / - Create Ledger Event

Records a new financial transaction event.

**Request Body:**
```json
{
  "eventId": "string (10 digits)",
  "accountId": "string (10 digits)",
  "type": "string",
  "amount": "number",
  "currency": "string",
  "eventTimestamp": "string (ISO 8601)",
  "metadata": {
    "batchId": "string",
    "source": "string"
  }
}
```

**Response:** `201 CREATED`

### GET /{id} - Get Event by ID

Retrieves ledger events by event ID.

**Query Parameters:**
- `id` (string): Alpha-numeric event identifier

**Response:** `200 OK` with list of events

### GET /account={accountId} - Get Events by Account ID

Retrieves ledger events for a specific account.

**Query Parameters:**
- `accountId` (string): Alpha-numeric account identifier

**Response:** `200 OK` with ledger events

## Project Structure

```
accountledger/
├── src/main/java/com/cloud/accountsledger/
│   ├── controller/
│   │   └── AccountsLedgerController.java
│   ├── dto/
│   │   └── AccountsLedgerDto.java
│   ├── entity/
│   │   ├── AccountsLedger.java
│   │   └── MetaData.java
│   ├── exceptions/
│   │   ├── ErrorResponse.java
│   │   └── GlobalExceptionHandler.java
│   ├── mapper/
│   │   └── AccountsLedgerMapper.java
│   ├── repository/
│   │   └── AccountsLedgerRepository.java
│   ├── service/
│   │   ├── AccountLedgerService.java
│   │   ├── impl/
│   │   │   └── AccountsLedgerServiceImpl.java
│   │   └── client/
│   │       └── AccountsFeignClient.java
│   └── AccountLedgerApplication.java
├── src/main/resources/
│   ├── application.yaml
│   └── schema.sql
└── pom.xml
```

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Eureka Server running on port 8070
- Config Server running on port 8071 (optional)

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

The image will be tagged as `cloud/accountledger:R1`

## Actuator Endpoints

The following actuator endpoints are available:

- `/actuator/health` - Health check
- `/actuator/info` - Application information
- `/actuator` - All available endpoints

## H2 Console

Access the H2 database console at:
```
http://localhost:8081/h2-console
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
- Spring Cloud Starter Config
- Spring Cloud Starter Netflix Eureka Client
- Spring Cloud OpenFeign
- H2 Database
- Lombok
- Jakarta Validation API

## Service Integration

The service integrates with:
- **Eureka Server**: For service discovery and registration
- **Config Server**: For centralized configuration management
- **Accounts Service**: Via Feign client (AccountsFeignClient)

## Development

### Adding New Features

1. Add new endpoints in `AccountsLedgerController`
2. Implement business logic in `AccountsLedgerServiceImpl`
3. Update DTOs and entities as needed
4. Add repository methods in `AccountsLedgerRepository`

### Validation

Request validation is implemented using Jakarta Validation annotations:
- `@NotEmpty` - Ensures fields are not null or empty
- `@Pattern` - Validates field formats (e.g., 10-digit numeric strings)

## License

This project is part of a microservices banking application.

## Author

Kumar Thirunavukarasu
