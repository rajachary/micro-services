# Eureka Server

A service discovery server for the microservices banking application. This service provides service registration and discovery capabilities using Netflix Eureka.

## Overview

The Eureka Server is responsible for:
- Service registration and discovery for all microservices
- Maintaining a registry of available services
- Load balancing support through service discovery
- Health monitoring of registered services
- Fault tolerance and self-preservation mechanisms

## Technology Stack

- **Java**: 17
- **Spring Boot**: 4.0.6
- **Spring Cloud**: 2025.1.1
- **Spring Cloud Netflix Eureka Server**: Service discovery and registration
- **Build Tool**: Maven
- **Containerization**: Jib (Google Cloud Tools)

## Features

- **Service Registration**: Automatic registration of microservices
- **Service Discovery**: Clients can discover and communicate with registered services
- **Health Monitoring**: Continuous health checks of registered services
- **Self-Preservation**: Protects against network partitions and temporary failures
- **Load Balancing**: Integration with client-side load balancers
- **Dashboard**: Web UI for monitoring registered services
- **Configuration Integration**: Optional integration with Config Server
- **Health Monitoring**: Spring Boot Actuator endpoints
- **Docker Support**: Jib plugin for container image building

## Configuration

### Application Properties

- **Service Name**: eurekaserver
- **Config Server**: Optional integration with http://localhost:8071/
- **Actuator Endpoints**: All endpoints exposed

### Default Eureka Configuration

The Eureka Server uses default configuration settings. Key default behaviors:

- **Server Port**: 8070 (default Eureka port)
- **Self-Preservation**: Enabled by default
- **Registry Fetch**: Enabled for peer communication
- **Eviction Interval**: 60 seconds (default)

### Configuration Server Integration

The Eureka Server can optionally load configuration from the Config Server:

```yaml
spring:
  config:
    import: "optional:configserver:http://localhost:8071/"
```

This allows centralized management of Eureka Server configuration.

## Project Structure

```
eurekaserver/
├── src/main/java/com/cloud/eurekaserver/
│   └── EurekaServerApplication.java
├── src/main/resources/
│   └── application.yaml
└── pom.xml
```

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Config Server running on port 8071 (optional, for externalized configuration)

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

The image will be tagged as `cloud/eurekaserver:R1`

## Accessing the Eureka Dashboard

Once the Eureka Server is running, access the dashboard at:

```
http://localhost:8070/
```

The dashboard provides:
- List of registered services
- Service instances and their status
- General server information
- Instance details and health status

## Client Service Configuration

### Registering with Eureka Server

Client services need the following dependencies:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

### Client Configuration

Add to client service's `application.yml`:

```yaml
eureka:
  instance:
    preferIpAddress: true
  client:
    fetchRegistry: true
    registerWithEureka: true
    serviceUrl:
      defaultZone: http://localhost:8070/eureka/
```

## Actuator Endpoints

The following actuator endpoints are available:

- `/actuator/health` - Health check
- `/actuator/info` - Application information
- `/actuator` - All available endpoints
- `/actuator/env` - Environment properties

## Service Registration Flow

1. **Service Startup**: Microservice starts up with Eureka client configuration
2. **Registration**: Service registers with Eureka Server
3. **Heartbeat**: Service sends periodic heartbeats to maintain registration
4. **Discovery**: Other services can discover and communicate with the registered service
5. **Eviction**: If a service stops sending heartbeats, it's evicted from the registry

## Eureka Server Concepts

### Self-Preservation Mode

When Eureka Server loses too many heartbeats in a short time, it enters self-preservation mode:
- Stops evicting instances from the registry
- Protects against network partitions
- Continues to serve discovery requests
- Exits self-preservation when heartbeats resume

### Service Instance Status

Services can have the following statuses:
- **UP**: Service is healthy and available
- **DOWN**: Service is unavailable
- **STARTING**: Service is starting up
- **OUT_OF_SERVICE**: Service is intentionally taken out of rotation
- **UNKNOWN**: Service status is unknown

### Peer Awareness

In production, Eureka Servers can be configured in a cluster:
- Multiple Eureka Server instances
- Peer-to-peer communication
- Replicated registry across peers
- High availability and fault tolerance

## Dependencies

Key dependencies include:
- Spring Boot Starter Actuator
- Spring Cloud Starter Config
- Spring Cloud Starter Netflix Eureka Server

## Troubleshooting

### Services Not Registering

- Verify Eureka Server is running on port 8070
- Check client service configuration for correct Eureka server URL
- Ensure client has Eureka client dependency
- Review client service logs for registration errors
- Check network connectivity between client and server

### Dashboard Not Accessible

- Verify Eureka Server is running
- Check if port 8070 is accessible
- Review firewall rules
- Check application logs for startup errors

### Services Not Discoverable

- Verify service is registered in Eureka dashboard
- Check service status is UP
- Ensure client is configured to fetch registry
- Review client service logs for discovery errors

### Self-Preservation Mode

- Self-preservation is a safety feature, not an error
- Check network connectivity between services
- Verify services are sending heartbeats
- Review server logs for heartbeat failures

## Development

### Adding Custom Configuration

To customize Eureka Server behavior, add properties to `application.yaml`:

```yaml
eureka:
  server:
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 5000
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
```

### Testing Service Registration

1. Start Eureka Server
2. Start a client service with Eureka client configuration
3. Access Eureka dashboard at http://localhost:8070/
4. Verify the service appears in the registry
5. Check service status is UP

## Production Considerations

### High Availability

For production deployments:
- Deploy multiple Eureka Server instances
- Configure peer-to-peer communication
- Use load balancer for Eureka Server access
- Enable self-preservation for fault tolerance

### Security

- Enable authentication for Eureka Server
- Use HTTPS for secure communication
- Restrict access to Eureka dashboard
- Implement proper network segmentation

### Monitoring

- Monitor Eureka Server health and performance
- Track registration and eviction rates
- Monitor self-preservation mode activation
- Set up alerts for critical events

## Best Practices

1. **Always use peer awareness** in production for high availability
2. **Monitor self-preservation** mode activation
3. **Configure appropriate heartbeat intervals** based on network conditions
4. **Use meaningful service names** for easy identification
5. **Enable preferIpAddress** for containerized environments
6. **Regularly review registered services** and clean up unused instances
7. **Test failover scenarios** to ensure resilience

## License

This project is part of a microservices banking application.

## Author

Kumar Thirunavukarasu
