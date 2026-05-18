# Kairos

[![Maven](https://img.shields.io/badge/Maven-3.9+-orange.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

> **Kairos** (καιρός) — *The right moment to act.*  
> A powerful Spring Boot starter template to help you start projects at the perfect moment.

# PreInstallations
- Java JDK 25
- MySQL 8
- Docker
- Maven

# Local environment
- Run MySQL server:
``````
docker run --name KairosMySQL -e MYSQL_ROOT_PASSWORD=secret-password -e MYSQL_DATABASE=kairos -p 3306:3306 -d mysql:8.4
``````
- Connect to MySQL server
```
docker exec -it KairosMySQL mysql -u root -p
```

# Build and run on the Spring Boot
```
mvn clean package
Run Application from IDE or  
mvn spring-boot:run -Dspring-boot.run.profiles=dev 
```