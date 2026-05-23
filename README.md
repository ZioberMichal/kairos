# Kairos

[![Maven](https://img.shields.io/badge/Maven-3.9+-orange.svg)](https://maven.apache.org/)
[![Java](https://img.shields.io/badge/Java-25-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

> **Kairos** (καιρός) — *The right moment to act.*  
> A production-ready Spring Boot starter template with clean architecture, modern best practices, and comprehensive tooling to bootstrap enterprise applications with confidence.

## ✨ Features

- **🏗️ Clean Architecture**: Layered structure with clear separation of concerns
- **🔐 Security**: Spring Security pre-configured with BCrypt password encoding
- **📊 Database**: Spring Data JPA with MySQL, HikariCP connection pooling, and Flyway migrations
- **🔍 API Documentation**: OpenAPI/Swagger UI integration out-of-the-box
- **📝 Validation**: Bean validation with custom error handling
- **🔄 Auditing**: Automatic JPA entity auditing (createdBy, updatedBy, timestamps)
- **📊 HATEOAS**: Hypermedia support for RESTful APIs
- **🧪 Testing**: Comprehensive test setup with TestContainers, Mockito, and REST Assured
- **📋 Logging**: Environment-specific logging with Logback (async appenders in production)
- **⚡ Performance**: Async I/O, connection pooling, and optimized configuration

## 📋 Prerequisites

- **Java JDK 25** or higher
- **MySQL 8.4** or higher
- **Docker** (optional, for running MySQL container)
- **Maven 3.9+**

## 🚀 Quick Start

### 1. Setup Local Environment

#### Option A: Docker (Recommended)

```bash
# Run MySQL in Docker
docker run --name KairosMySQL \
  -e MYSQL_ROOT_PASSWORD=secret-password \
  -e MYSQL_DATABASE=kairos \
  -p 3306:3306 \
  -d mysql:8.4

# Connect to MySQL (password: secret-password)
docker exec -it KairosMySQL mysql -u root -p
```

#### Option B: Local MySQL Installation

```bash
# Create database
mysql -u root -p -e "CREATE DATABASE kairos;"
```

### 2. Build the Application

```bash
# Clean build
mvn clean package

# Or skip tests for faster build
mvn clean package -DskipTests
```

### 3. Run the Application

#### Development Profile
```bash
# Run with maximum verbosity for debugging
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Production Profile
```bash
# Run with minimal logging and async appenders
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

#### From IDE
```bash
# Set active profile in Run Configuration
# VM options: -Dspring.profiles.active=dev
Run Application.main()
```

## 🏗️ Project Structure

```
kairos/
├── src/main/java/com/kairos/
│   ├── config/              # Spring configuration classes
│   │   ├── DatabaseConfiguration.java
│   │   ├── WebSecurityConfig.java
│   │   ├── JacksonConfig.java
│   │   └── ExceptionsControllerAdvice.java
│   ├── core/                # Core, reusable components
│   │   ├── exceptions/      # Custom exception hierarchy
│   │   ├── security/        # Security utilities and services
│   │   ├── web/             # Common web models (ApiError, etc.)
│   │   ├── mapstruct/       # Mapper interfaces
│   │   ├── util/            # Utility classes
│   │   └── hateoas/         # HATEOAS models
│   └── project/             # Business domain
│       ├── employees/       # Example: Employee module
│       ├── departments/     # Example: Department module
│       ├── assets/          # Example: Asset module
│       └── user/            # User management
├── src/main/resources/
│   ├── application.properties           # Default configuration
│   ├── application-dev.properties       # Development profile
│   ├── application-prod.properties      # Production profile
│   ├── logback-spring.xml               # Logging configuration
│   ├── db/migration/                    # Flyway migrations
│   │   ├── schema/                      # Schema creation
│   │   ├── data/                        # Reference data
│   │   └── mockdata/                    # Test/mock data
│   └── templates/                       # HTML templates
├── src/test/java/com/kairos/            # Comprehensive test suite
├── pom.xml                              # Maven configuration
├── mvnw / mvnw.cmd                      # Maven wrapper
└── README.md                            # This file
```

## 🔧 Configuration

### Application Profiles

#### Development (`dev`)
- **Logging**: DEBUG level with console output
- **Database**: `root@localhost`
- **DDL**: Validate (Flyway handles migrations)
- **Use**: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`

#### Production (`prod`)
- **Logging**: WARN level, file-only (async appenders)
- **Database**: Environment variables recommended
- **Use**: Use environment variables for secrets

#### Staging/Test (`stage`, `test`)
- **Logging**: INFO level with async file appenders
- **Database**: Isolated test database

### Environment Variables

Set these for production deployment:

```bash
# Database
export DB_HOST=your-db-host
export DB_PORT=3306
export DB_USERNAME=your-username
export DB_PASSWORD=your-password

# Logging
export LOG_PATH=/var/log/kairos

# Spring
export SPRING_PROFILES_ACTIVE=prod
```

### Key Configuration Properties

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database (HikariCP)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=10000

# JPA/Hibernate
spring.jpa.open-in-view=false
spring.jpa.hibernate.ddl-auto=validate

# Flyway
spring.flyway.locations=classpath:db/migration/schema,classpath:db/migration/data

# Logging
logging.level.com.kairos=DEBUG
logging.file.path=./logs
```

## 📚 API Documentation

Once the application is running, access Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

Or OpenAPI JSON:
```
http://localhost:8080/v3/api-docs
```

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=EmployeeControllerTest
```

### Test Infrastructure

- **TestContainers**: MySQL runs in Docker for integration tests
- **REST Assured**: HTTP assertions
- **Mockito**: Mocking and spying
- **Spring Security Test**: Security context testing

## 🔐 Security Features

- **Authentication**: Form-based login with bcrypt password hashing
- **Authorization**: Method-level security with `@PreAuthorize`
- **Session Management**: Secure session handling
- **CSRF**: Commented TODO for future implementation
- **Auditing**: Automatic tracking of entity changes

### Default Credentials

For development, use:
- **Username**: `admin@kairos.com`
- **Password**: See database seeds in Flyway migrations

## 📝 Logging

### Log Levels by Profile

| Profile | Level | Output |
|---------|-------|--------|
| **dev** | DEBUG | Console + File |
| **test** | INFO | Console + File (async) |
| **prod** | WARN | File only (async) |

### Log Files

Production logs are stored in `./logs/`:
- `kairos.log` - All application logs
- `kairos-error.log` - Errors only
- `.gz` archived files - Compressed old logs (30-day retention)

### Async Appenders

Production uses async appenders for non-blocking I/O:
- Regular logs: 512 queue size
- Error logs: 256 queue size
- No event discarding (prevents log loss)

## 🚀 Deployment

### Docker Build

```dockerfile
FROM openjdk:25-slim
COPY target/kairos-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
# Build Docker image
docker build -t kairos:latest .

# Run container
docker run -e SPRING_PROFILES_ACTIVE=prod \
           -e DB_HOST=mysql-host \
           -e DB_USERNAME=user \
           -e DB_PASSWORD=pass \
           -p 8080:8080 \
           kairos:latest
```

### JAR Deployment

```bash
# Build JAR
mvn clean package

# Run
java -Dspring.profiles.active=prod \
     -Dlogging.file.path=/var/log/kairos \
     -jar target/kairos-0.1-SNAPSHOT.jar
```

## 📖 Example Usage

### Create Employee

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "departmentId": 1
  }'
```

### Get All Employees

```bash
curl http://localhost:8080/api/employees
```

See [Swagger UI](http://localhost:8080/swagger-ui.html) for complete API documentation.

## 🛠️ Development Best Practices

This template follows:

- **Clean Architecture**: Separation of concerns across layers
- **Domain-Driven Design**: Entity aggregates and value objects
- **SOLID Principles**: Single Responsibility, Open/Closed, etc.
- **12-Factor App**: Environment-based configuration
- **RESTful Conventions**: Proper HTTP methods and status codes
- **DRY**: MapStruct for entity/DTO mapping
- **Testing**: Comprehensive unit and integration tests

## 📦 Dependencies

### Key Libraries

- **Spring Boot 4.0.6**: Framework
- **Spring Data JPA**: Database abstraction
- **Spring Security**: Authentication & authorization
- **Hibernate**: ORM
- **MySQL Connector**: Database driver
- **HikariCP**: Connection pooling
- **Flyway**: Database migrations
- **Lombok**: Boilerplate reduction
- **MapStruct**: Type-safe DTO mapping
- **SpringDoc OpenAPI**: Swagger/OpenAPI integration
- **Logback**: Logging

### Testing

- **JUnit 5**: Test framework
- **Mockito**: Mocking
- **REST Assured**: HTTP testing
- **TestContainers**: Containerized MySQL for tests
- **Spring Security Test**: Security testing

## ❓ Troubleshooting

### MySQL Connection Error

```
Cannot connect to mysql:localhost:3306
```

**Solution**: Ensure MySQL is running and credentials are correct:
```bash
docker exec -it KairosMySQL mysqladmin ping -u root -p
```

### Tests Failing with TestContainers

```
Could not find a valid Docker environment
```

**Solution**: Ensure Docker is running:
```bash
docker ps
```

### Port Already in Use

```
Address already in use: 8080
```

**Solution**: Change port in `application.properties`:
```properties
server.port=8081
```

## 📄 License

This project is licensed under the **Apache License 2.0** - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! This is a starter template, so feel free to:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📧 Support

For questions or issues:

- Create a [GitHub Issue](../../issues)
- Check existing documentation in `/docs` (if available)
- Review [Spring Boot Documentation](https://spring.io/projects/spring-boot)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Contributors and maintainers
- Community feedback and suggestions

---