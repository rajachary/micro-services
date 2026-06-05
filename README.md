# Microservices Architecture

A cloud-native microservices banking application built with Spring Boot, Spring Cloud, and Docker.

## Architecture Overview

This project implements a microservices architecture for banking operations with the following services:

- **Config Server** - Centralized configuration management
- **Eureka Server** - Service discovery and registration
- **Gateway Server** - API Gateway for routing and load balancing
- **Accounts Service** - Account management and transactions
- **Account Ledger Service** - Financial event tracking and ledger management

## Technology Stack

- **Java Version**: 17
- **Spring Boot**: 4.0.6
- **Spring Cloud**: 2025.1.1
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Configuration**: Spring Cloud Config
- **Database**: H2 (in-memory)
- **HTTP Client**: OpenFeign
- **Validation**: Jakarta Validation
- **Code Generation**: Lombok

## Modules

### 1. Config Server

**Port**: 8071

**Description**: Centralized configuration server that provides configuration properties to all microservices.

**Key Features**:
- `@EnableConfigServer` annotation for configuration server capabilities
- Serves configuration files from a Git repository or file system
- Supports environment-specific configurations (dev, prod, etc.)
- Actuator endpoints for health monitoring

**Dependencies**:
- Spring Boot Actuator
- Spring Cloud Config Server
- Lombok

**Running the Service**:
```bash
cd configserver
mvn spring-boot:run
```

**Docker**:
```bash
docker run -p 8071:8071 cloud/configserver:R1
```

---

### 2. Eureka Server

**Port**: 8070

**Description**: Service discovery server that enables microservices to register and discover each other without hard-coded hostnames.

**Key Features**:
- `@EnableEurekaServer` annotation for Eureka server capabilities
- Service registration and discovery
- Health checks for registered services
- Dashboard for monitoring registered services

**Dependencies**:
- Spring Boot Actuator
- Spring Cloud Config Client
- Spring Cloud Netflix Eureka Server

**Running the Service**:
```bash
cd eurekaserver
mvn spring-boot:run
```

**Docker**:
```bash
docker run -p 8070:8070 cloud/eurekaserver:R1
```

**Eureka Dashboard**: http://localhost:8070

---

### 3. Gateway Server

**Port**: 8072

**Description**: API Gateway that routes requests to appropriate microservices, provides cross-cutting concerns, and acts as a single entry point.

**Key Features**:
- Spring Cloud Gateway with WebFlux (reactive programming)
- Route configuration for path-based routing
- Load balancing using Eureka service discovery
- Request/response filtering and modification
- Route definitions:
  - `/accounts/**` → ACCOUNTS service
  - `/events/**` → ACCOUNTSLEDGER service

**Dependencies**:
- Spring Boot Actuator
- Spring Cloud Config Client
- Spring Cloud Gateway Server WebFlux
- Spring Cloud Netflix Eureka Client
- Reactor Test

**Running the Service**:
```bash
cd gatewayserver
mvn spring-boot:run
```

**Docker**:
```bash
docker run -p 8072:8072 cloud/gatewayserver:R1
```

**API Endpoints**:
- `GET /accounts/**` - Routes to Accounts service
- `GET /events/**` - Routes to Account Ledger service

---

### 4. Accounts Service

**Port**: 8080

**Description**: Manages bank accounts, transactions, and balance information.

**Key Features**:
- `@EnableFeignClients` for declarative HTTP clients
- RESTful API for account operations
- JPA for data persistence
- Input validation using Jakarta Validation
- Integration with Eureka for service discovery
- Integration with Config Server for configuration

**Dependencies**:
- Spring Boot Actuator
- Spring Cloud OpenFeign
- Spring Boot Data JPA
- Spring Boot Validation
- Spring Boot WebMVC
- Spring Cloud Config Client
- Spring Cloud Netflix Eureka Client
- Lombok
- H2 Database

**API Endpoints**:
- `GET /{accountId}/transactions` - Get account transactions
- `GET /{accountId}/balance` - Get current account balance
- `GET /{accountId}` - Get account details and recent transactions

**Running the Service**:
```bash
cd accounts
mvn spring-boot:run
```

**Docker**:
```bash
docker run -p 8080:8080 cloud/accounts:R1
```

---

### 5. Account Ledger Service

**Port**: 9000

**Description**: Tracks financial events and maintains the account ledger for transaction history.

**Key Features**:
- Event-driven architecture for financial transactions
- RESTful API for ledger operations
- JPA for data persistence with H2 database
- Input validation using Jakarta Validation
- Integration with Eureka for service discovery
- Integration with Config Server for configuration
- OpenFeign for inter-service communication
- Lombok for reducing boilerplate code

**Dependencies**:
- Spring Boot Actuator
- Spring Boot Data JPA
- Spring Cloud Config Client
- Spring Cloud Netflix Eureka Client
- Spring Boot WebMVC
- Spring Boot DevTools
- Jakarta Validation API
- Lombok
- H2 Database
- Spring Cloud OpenFeign Core

**Entities**:
- `AccountsLedger` - Main ledger entity with fields:
  - eventId (String, 10 digits)
  - accountId (String, 10 digits)
  - type (String, 10 digits)
  - amount (Double)
  - currency (String)
  - eventTimestamp (String)
  - metaData (MetaData object)

- `MetaData` - Additional metadata with fields:
  - batchId
  - source

**API Endpoints**:
- `POST /` - Create a new ledger event
  - Request body: AccountsLedgerDto
  - Response: 201 CREATED
- `GET /{id}` - Get events by ID
  - Parameter: id (alphanumeric)
  - Response: List of AccountsLedger
- `GET /account={accountId}` - Get events by account ID
  - Parameter: accountId (alphanumeric)
  - Response: AccountsLedger

**Running the Service**:
```bash
cd accountledger
mvn spring-boot:run
```

**Docker**:
```bash
docker run -p 9000:9000 cloud/accountledger:R1
```

---

## Docker Compose Deployment

### Development Environment

The project includes Docker Compose configurations for easy deployment of all services.

**Location**: `docker-compose/dev/`

**Services**:
- configserver (port 8071)
- eurekaserver (port 8070)
- accounts (port 8080)
- accountledger (port 9000)
- gatewayserver (port 8072)

**Starting all services**:
```bash
cd docker-compose/dev
docker-compose up
```

**Stopping all services**:
```bash
docker-compose down
```

**Service Dependencies**:
- Config Server starts first (no dependencies)
- Eureka Server depends on Config Server
- Accounts and Account Ledger depend on both Config Server and Eureka Server
- Gateway Server depends on Accounts and Account Ledger services

**Health Checks**:
All services include health checks that verify:
- Actuator health endpoint (`/actuator/health/readiness`)
- Service readiness before dependent services start
- Automatic retry logic (10 retries with 10s intervals)

**Network Configuration**:
- All services communicate via the `accountledger` bridge network
- Services use Docker internal DNS for service discovery
- Memory limits: 700MB per service

---

## Observability Stack

The project includes observability tools for monitoring and logging:

**Location**: `docker-compose/observability/`

**Components**:
- **Prometheus** - Metrics collection and monitoring
- **Grafana** - Visualization and dashboards
- **Loki** - Log aggregation
- **Tempo** - Distributed tracing
- **Alloy** - Metrics collection agent

---

## Building Docker Images

Each service includes the Jib Maven plugin for building Docker images without Docker daemon.

**Build all services**:
```bash
# For each service directory
cd <service-directory>
mvn clean package jib:build
```

**Image naming convention**: `cloud/<service-name>:R1`

---

## Configuration

### Service Configuration

All services use Spring Cloud Config for centralized configuration:

- Config Server URL: `http://configserver:8071/`
- Eureka Server URL: `http://eurekaserver:8070/eureka/`
- Active profile: `default`

### Environment Variables

Common environment variables for microservices:
- `SPRING_APPLICATION_NAME` - Service name for Eureka registration
- `SPRING_PROFILES_ACTIVE` - Active Spring profile
- `SPRING_CONFIG_IMPORT` - Config server location
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` - Eureka server location

---

## Service Communication

### Service Discovery
- All services register with Eureka Server
- Gateway Server uses Eureka for load balancing
- Services can discover each other via service names (e.g., `ACCOUNTS`, `ACCOUNTSLEDGER`)

### Inter-Service Communication
- OpenFeign for declarative HTTP clients
- Gateway Server routes requests based on path patterns
- Services communicate via REST APIs

---

## Development

### Prerequisites
- Java 17
- Maven 3.x
- Docker & Docker Compose

### Local Development

1. **Start Config Server**:
   ```bash
   cd configserver
   mvn spring-boot:run
   ```

2. **Start Eureka Server**:
   ```bash
   cd eurekaserver
   mvn spring-boot:run
   ```

3. **Start Accounts Service**:
   ```bash
   cd accounts
   mvn spring-boot:run
   ```

4. **Start Account Ledger Service**:
   ```bash
   cd accountledger
   mvn spring-boot:run
   ```

5. **Start Gateway Server**:
   ```bash
   cd gatewayserver
   mvn spring-boot:run
   ```

### Testing Services

**Eureka Dashboard**: http://localhost:8070

**Gateway Routes**:
- Accounts: http://localhost:8072/accounts/**
- Ledger: http://localhost:8072/events/**

**Direct Service Access**:
- Accounts: http://localhost:8080
- Account Ledger: http://localhost:9000
- Config Server: http://localhost:8071
- Eureka Server: http://localhost:8070
- Gateway: http://localhost:8072

---

## Troubleshooting

### Common Issues

1. **Service Registration Failed**
   - Ensure Eureka Server is running
   - Check Eureka Server URL configuration
   - Verify network connectivity

2. **Configuration Not Loading**
   - Ensure Config Server is running
   - Check Config Server URL in service configuration
   - Verify configuration files exist

3. **Gateway Routing Issues**
   - Verify target services are registered in Eureka
   - Check route configuration in Gateway Server
   - Ensure service names match Eureka registrations

### Health Checks

All services expose actuator endpoints:
- Health: `/actuator/health`
- Readiness: `/actuator/health/readiness`
- Info: `/actuator/info`

---

## Project Structure

```
micro-services/
├── accountledger/          # Account Ledger Service
├── accounts/               # Accounts Service
├── configserver/           # Configuration Server
├── eurekaserver/           # Eureka Service Discovery
├── gatewayserver/          # API Gateway
├── docker-compose/         # Docker Compose configurations
│   ├── dev/               # Development environment
│   └── observability/     # Monitoring stack
└── README.md              # This file
```

---

## License

This project is for educational and demonstration purposes.

---

## Contact

For questions or issues, please refer to the project repository or contact the development team.
