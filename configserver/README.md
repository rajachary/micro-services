# Config Server

A centralized configuration management server for the microservices banking application. This service provides externalized configuration for all microservices in the system.

## Overview

The Config Server is responsible for:
- Centralized configuration management for all microservices
- Environment-specific configuration profiles (dev, qa, prod)
- Configuration versioning and distribution
- Dynamic configuration updates without service restart
- Support for multiple backend storage options (native file system, Git)

## Technology Stack

- **Java**: 17
- **Spring Boot**: 4.0.6
- **Spring Cloud**: 2025.1.1
- **Spring Cloud Config Server**: Central configuration management
- **Build Tool**: Maven
- **Containerization**: Jib (Google Cloud Tools)

## Features

- **Centralized Configuration**: Single source of truth for all service configurations
- **Profile Support**: Environment-specific configurations (default, qa, prod)
- **Multiple Storage Backends**: Support for native file system and Git repositories
- **Health Monitoring**: Spring Boot Actuator endpoints
- **Service Discovery Ready**: Integrates with Eureka for service registration
- **Docker Support**: Jib plugin for container image building
- **Hot Reload**: Configuration changes can be picked up without restart (with appropriate client configuration)

## Configuration

### Application Properties

- **Server Port**: 8071
- **Service Name**: configserver
- **Active Profile**: native (file system based)
- **Config Search Location**: classpath:/config

### Storage Backends

The Config Server supports two storage backends:

#### Native (File System) - Currently Active

Configuration files are stored in the classpath at `src/main/resources/config/`

```yaml
spring:
  profiles:
    active: native
  cloud:
    config:
      server:
        native:
          search-locations: "classpath:/config"
```

#### Git (Alternative)

Configuration can be stored in a Git repository:

```yaml
spring:
  profiles:
    active: git
  cloud:
    config:
      server:
        git:
          uri: "https://github.com/your-org/config-repo.git"
          default-label: main
          timeout: 5
          clone-on-start: true
          force-pull: true
```

## Managed Configurations

The Config Server manages configuration for the following services:

### Accounts Service
- `accounts.yml` - Default configuration
- `accounts-qa.yml` - QA environment configuration
- `accounts-prod.yml` - Production environment configuration

### Accounts Ledger Service
- `accountsledger.yml` - Default configuration

### Eureka Server
- `eurekaserver.yml` - Default configuration

### Example Configuration Structure

```yaml
build:
  version: "3.0"

accounts:
  message: "Welcome to EazyBank accounts related local APIs"
  contactDetails:
    name: "John Doe - Developer"
    email: "john@eazybank.com"
  onCallSupport:
    - (555) 555-1234
    - (555) 523-1345
```

## Project Structure

```
configserver/
├── src/main/java/com/cloud/configserver/
│   └── ConfigserverApplication.java
├── src/main/resources/
│   ├── application.yaml
│   └── config/
│       ├── accounts.yml
│       ├── accounts-qa.yml
│       ├── accounts-prod.yml
│       ├── accountsledger.yml
│       └── eurekaserver.yml
└── pom.xml
```

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven 3.6+

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

The image will be tagged as `cloud/configserver:R1`

## Accessing Configuration

### Configuration Access Pattern

Client services can access configuration using the following URL pattern:

```
http://localhost:8071/{application}/{profile}/{label}
```

Where:
- `application`: The service name (e.g., accounts, accountsledger)
- `profile`: The environment profile (e.g., default, qa, prod)
- `label`: The version label (for Git backend, typically branch name)

### Examples

```bash
# Get default configuration for accounts service
curl http://localhost:8071/accounts/default

# Get QA configuration for accounts service
curl http://localhost:8071/accounts/qa

# Get production configuration for accounts service
curl http://localhost:8071/accounts/prod

# Get configuration for accounts ledger service
curl http://localhost:8071/accountsledger/default
```

## Actuator Endpoints

The following actuator endpoints are available:

- `/actuator/health` - Health check
- `/actuator/info` - Application information
- `/actuator` - All available endpoints
- `/actuator/env` - Environment properties
- `/actuator/refresh` - Trigger configuration refresh (for clients)

## Client Configuration

### Client Service Configuration

To connect to the Config Server, client services should include:

```yaml
spring:
  application:
    name: "your-service-name"
  config:
    import: "optional:configserver:http://localhost:8071/"
```

### Dependencies

Client services need the following dependency:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

## Dependencies

Key dependencies include:
- Spring Boot Starter Actuator
- Spring Cloud Config Server
- Lombok

## Configuration Management Best Practices

1. **Sensitive Data**: Avoid storing sensitive information (passwords, API keys) in configuration files. Use environment variables or secret management systems
2. **Version Control**: Keep configuration files under version control when using Git backend
3. **Profile Separation**: Maintain separate configurations for different environments (dev, qa, prod)
4. **Validation**: Validate configuration changes before deploying to production
5. **Documentation**: Document configuration properties and their purposes

## Switching Storage Backends

### To Switch from Native to Git

1. Update `application.yaml`:
```yaml
spring:
  profiles:
    active: git
  cloud:
    config:
      server:
        git:
          uri: "your-git-repo-url"
          default-label: main
```

2. Remove or comment out the native configuration
3. Restart the Config Server

### To Switch from Git to Native

1. Update `application.yaml`:
```yaml
spring:
  profiles:
    active: native
  cloud:
    config:
      server:
        native:
          search-locations: "classpath:/config"
```

2. Ensure configuration files exist in the classpath config directory
3. Restart the Config Server

## Troubleshooting

### Configuration Not Loading

- Verify the Config Server is running on port 8071
- Check the service name matches the configuration file name
- Ensure the profile is correctly specified
- Review Config Server logs for errors

### Native Backend Issues

- Verify configuration files exist in `src/main/resources/config/`
- Check file naming matches the expected pattern `{service}-{profile}.yml`
- Ensure YAML syntax is valid

### Git Backend Issues

- Verify Git repository URL is accessible
- Check authentication if repository is private
- Ensure the specified branch/label exists
- Review clone and pull operations in logs

## Development

### Adding New Service Configuration

1. Create a new YAML file in `src/main/resources/config/`
2. Name it following the pattern: `{service-name}.yml` for default profile
3. Add environment-specific profiles: `{service-name}-{profile}.yml`
4. Restart the Config Server
5. Test configuration access using curl or a browser

### Updating Existing Configuration

1. Edit the appropriate YAML file in `src/main/resources/config/`
2. Changes are immediately available for new client requests
3. For running clients, trigger a refresh using `/actuator/refresh` endpoint if configured

## License

This project is part of a microservices banking application.

## Author

Kumar Thirunavukarasu
