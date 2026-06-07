# Gateway Server

An API Gateway server for the microservices banking application. This service provides a single entry point for all client requests, handling routing, security, and cross-cutting concerns.

## Overview

The Gateway Server is responsible for:
- Routing requests to appropriate microservices
- OAuth2/JWT authentication and authorization
- Request/response filtering and tracing
- Load balancing through service discovery
- Centralized security enforcement
- Cross-cutting concerns (logging, correlation IDs)

## Technology Stack

- **Java**: 17
- **Spring Boot**: 4.0.6
- **Spring Cloud**: 2025.1.1
- **Spring Cloud Gateway**: API Gateway with WebFlux
- **Spring Security**: OAuth2 Resource Server with JWT
- **Service Discovery**: Netflix Eureka Client
- **Configuration**: Spring Cloud Config
- **Reactive Programming**: Project Reactor
- **Build Tool**: Maven
- **Containerization**: Jib (Google Cloud Tools)

## Features

- **API Routing**: Intelligent routing to backend microservices
- **Load Balancing**: Client-side load balancing via Eureka
- **Security**: OAuth2/JWT authentication with Keycloak integration
- **Request Filtering**: Custom filters for request processing
- **Response Filtering**: Custom filters for response processing
- **Correlation Tracking**: Automatic correlation ID generation and tracking
- **Path Rewriting**: URL path transformation for service routing
- **Service Discovery**: Dynamic service discovery via Eureka
- **Health Monitoring**: Spring Boot Actuator endpoints
- **Configuration Management**: Integration with Config Server
- **Docker Support**: Jib plugin for container image building

## Configuration

### Application Properties

- **Server Port**: 8072
- **Service Name**: gatewayserver
- **Config Server**: http://localhost:8071/
- **Eureka Server**: http://localhost:8070/eureka/
- **Gateway Discovery**: Disabled (manual routing configured)
- **Connect Timeout**: 1000ms
- **Response Timeout**: 10s

### Security Configuration

- **OAuth2 Provider**: Keycloak
- **Issuer**: http://localhost:7080/realms/master
- **JWK Set URI**: http://localhost:7080/realms/master/protocol/openid-connect/certs
- **JWT Decoder**: Nimbus Reactive JWT Decoder

### Route Configuration

The gateway routes requests to the following services:

1. **Accounts Service**
   - Path Pattern: `/accounts/**`
   - Target Service: `ACCOUNTS` (via load balancer)
   - Path Rewrite: `/accounts/(?<segment>.*)` → `/${segment}`
   - Response Header: `X-Response-Time` (timestamp)
   - Security: Requires authentication

2. **Accounts Ledger Service**
   - Path Pattern: `/events/**`
   - Target Service: `ACCOUNTSLEDGER` (via load balancer)
   - Path Rewrite: `/events/(?<segment>.*)` → `/${segment}`
   - Response Header: `X-Response-Time` (timestamp)
   - Security: Permits all (no authentication required)

## Project Structure

```
gatewayserver/
├── src/main/java/com/cloud/gatewayserver/
│   ├── GatewayServerApplication.java
│   ├── config/
│   │   └── SecurityConfig.java
│   └── filters/
│       ├── FilterUtility.java
│       ├── RequestTraceFilter.java
│       └── ResponseTraceFilter.java
├── src/main/resources/
│   └── application.yaml
└── pom.xml
```

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Eureka Server running on port 8070
- Config Server running on port 8071
- Keycloak Server running on port 7080 (for OAuth2/JWT)
- Backend services (Accounts, Accounts Ledger) registered with Eureka

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

The image will be tagged as `cloud/gatewayserver:R1`

## API Endpoints

### Accounts Service Routes

All requests to `/accounts/**` are routed to the Accounts service:

```bash
# Example: Get account details
curl -H "Authorization: Bearer <jwt-token>" \
  http://localhost:8072/accounts/12345

# Example: Get account balance
curl -H "Authorization: Bearer <jwt-token>" \
  http://localhost:8072/accounts/12345/balance
```

**Note**: These endpoints require valid JWT authentication.

### Accounts Ledger Service Routes

All requests to `/events/**` are routed to the Accounts Ledger service:

```bash
# Example: Create ledger event
curl -X POST \
  http://localhost:8072/events \
  -H "Content-Type: application/json" \
  -d '{"eventId":"1234567890","accountId":"1234567890","type":"CREDIT","amount":100.0,"currency":"USD","eventTimestamp":"2024-01-01T00:00:00Z"}'
```

**Note**: These endpoints do not require authentication.

## Security

### Authentication Flow

1. Client obtains JWT token from Keycloak
2. Client includes JWT token in `Authorization` header
3. Gateway validates JWT token using Keycloak's JWK set
4. If valid, request is forwarded to backend service
5. If invalid, 401 Unauthorized is returned

### Security Rules

- `/accounts/**` - Requires authentication (JWT token required)
- `/events/**` - Permits all (no authentication required)
- CSRF protection is disabled for API gateway

### Keycloak Configuration

The gateway is configured to work with Keycloak at:
- Realm: master
- Base URL: http://localhost:7080
- OpenID Connect configuration: `/protocol/openid-connect/certs`

## Filters

### RequestTraceFilter

- **Order**: 1 (executes first)
- **Purpose**: Generates and tracks correlation IDs
- **Behavior**:
  - Checks for existing correlation ID in request headers
  - If not present, generates a new UUID
  - Adds correlation ID to request context
  - Logs correlation ID for tracing

### ResponseTraceFilter

- **Purpose**: Processes response headers and logging
- **Behavior**: Adds response headers and logs response information

### FilterUtility

Utility class for filter operations:
- Correlation ID management
- Header manipulation
- Request/response context handling

## Actuator Endpoints

The following actuator endpoints are available:

- `/actuator/health` - Health check
- `/actuator/info` - Application information
- `/actuator/gateway` - Gateway-specific endpoints
- `/actuator` - All available endpoints

## Service Integration

### Eureka Integration

The gateway registers with Eureka for:
- Service discovery of backend services
- Load balancing across service instances
- Dynamic routing based on service availability

### Config Server Integration

The gateway loads configuration from:
- Config Server at http://localhost:8071/
- Allows centralized configuration management
- Supports environment-specific configurations

## Dependencies

Key dependencies include:
- Spring Boot Starter Actuator
- Spring Cloud Starter Config
- Spring Cloud Starter Gateway Server WebFlux
- Spring Cloud Starter Netflix Eureka Client
- Spring Boot Starter Security
- Spring Boot Starter Security OAuth2 Resource Server
- Spring Security OAuth2 JOSE
- Project Reactor (for reactive programming)

## Correlation ID Tracking

The gateway implements distributed tracing using correlation IDs:

- **Header Name**: `events-ledger-correlation-id`
- **Generation**: UUID-based if not present in request
- **Propagation**: Added to all forwarded requests
- **Logging**: Logged at each filter stage for debugging

## Troubleshooting

### Services Not Accessible

- Verify Eureka Server is running and gateway is registered
- Check backend services are registered in Eureka
- Verify service names match route configuration (ACCOUNTS, ACCOUNTSLEDGER)
- Review gateway logs for routing errors

### Authentication Failures

- Verify Keycloak server is running on port 7080
- Check JWT token is valid and not expired
- Verify issuer and JWK set URI configuration
- Review security logs for JWT validation errors

### Connection Timeouts

- Check backend services are responsive
- Verify timeout settings in application.yaml
- Review network connectivity between gateway and services
- Check for service instance health in Eureka dashboard

### Filter Issues

- Review filter order and execution sequence
- Check correlation ID propagation in logs
- Verify filter utility methods are working correctly
- Test with and without existing correlation IDs

## Development

### Adding New Routes

To add a new route in `GatewayServerApplication.java`:

```java
.route(p -> p
    .path("/new-service/**")
    .filters(f -> f
        .rewritePath("/new-service/(?<segment>.*)", "/${segment}")
        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
    .uri("lb://NEWSERVICE"))
```

### Adding Security Rules

Update `SecurityConfig.java`:

```java
.authorizeExchange(ex ->
    ex.pathMatchers("/new-service/**").authenticated()
        .pathMatchers("/public/**").permitAll())
```

### Adding Custom Filters

1. Implement `GlobalFilter` interface
2. Add `@Component` annotation
3. Specify `@Order` for execution sequence
4. Implement filter logic in `filter()` method

## Best Practices

1. **Always use correlation IDs** for distributed tracing
2. **Secure sensitive endpoints** with proper authentication
3. **Monitor gateway performance** and latency
4. **Configure appropriate timeouts** for backend services
5. **Use load balancing** for high availability
6. **Log all requests and responses** for debugging
7. **Test security rules** thoroughly before deployment
8. **Keep filter logic lightweight** to avoid performance impact
9. **Use path rewriting** to hide internal service structure
10. **Monitor service health** via Eureka dashboard

## Production Considerations

### High Availability

- Deploy multiple gateway instances behind a load balancer
- Configure appropriate health checks
- Use circuit breakers for backend service failures
- Implement retry logic for transient failures

### Security

- Enable HTTPS in production
- Use strong JWT signing algorithms
- Implement rate limiting
- Add IP whitelisting if needed
- Regularly rotate JWT secrets
- Monitor for security anomalies

### Monitoring

- Track request/response times
- Monitor error rates and status codes
- Set up alerts for high latency or errors
- Log authentication failures
- Monitor service discovery health

## License

This project is part of a microservices banking application.

## Author

Kumar Thirunavukarasu
