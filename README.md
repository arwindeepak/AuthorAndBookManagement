# 📚 Author and Book Management System

A Spring Boot RESTful application for managing Authors and Books with secure role-based access control, bulk CSV operations, Swagger API documentation, logging, unit testing, Docker containerization, and CI/CD support using Jenkins.

---

# 🚀 Features

## Author Management

- Create Author
- Get Author By ID
- Get All Authors
- Update Author
- Delete Author
- Bulk Create Authors
- Import Authors from CSV
- Export Authors to CSV
- Duplicate Author Validation

---

## Book Management

- Create Book
- Get Book By ID
- Get All Books
- Update Book
- Delete Book
- Bulk Create Books
- Import Books from CSV
- Export Books to CSV

---

## Security

Implemented using Spring Security.

### Authentication

- HTTP Basic Authentication
- BCrypt Password Encoding

### Authorization

| Role | GET | POST | PUT | DELETE |
|--------|--------|--------|--------|--------|
| ADMIN | ✅ | ✅ | ✅ | ✅ |
| LIBRARIAN | ✅ | ❌ | ❌ | ❌ |

### Test Credentials

#### Admin

```text
username: admin
password: admin123
```

#### Librarian

```text
username: librarian
password: lib123
```

---

# 🏗️ Technology Stack

| Technology | Version |
|------------|----------|
| Java | 17 |
| Spring Boot | 4.x |
| Spring Data JPA | Latest |
| Spring Security | Latest |
| PostgreSQL | Latest |
| Maven | Latest |
| Swagger OpenAPI | Latest |
| Lombok | Latest |
| JUnit 5 | Latest |
| Mockito | Latest |
| Docker | Latest |
| Jenkins | Latest |

---

# 📂 Project Structure

```text
src
 ├── controller
 ├── service
 ├── serviceimpl
 ├── repository
 ├── entity
 ├── dto
 ├── security
 ├── config
 ├── exception
 └── test
```

---

# 📖 API Documentation

Swagger UI:

```text
http://localhost:8081/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8081/v3/api-docs
```

---

# 🔒 Security Configuration

Implemented:

- Role Based Authorization
- URL Based Access Control
- BCrypt Password Encoding
- HTTP Basic Authentication
- Swagger Authorization Support

---

# 📊 Logging

Implemented using:

```text
SLF4J
Logback
```

### Log Levels

```text
INFO
WARN
ERROR
DEBUG
```

### Log File

```text
logs/application.log
```

Example:

```text
Creating author with email: john@example.com
Author created successfully with id: 1
Deleting book with id: 5
```

---

# 📄 CSV Import / Export

## Author CSV Format

```csv
NAME,EMAIL,COUNTRY
John Doe,john@gmail.com,USA
Jane Smith,jane@gmail.com,UK
```

---

## Book CSV Format

```csv
TITLE,ISBN,PRICE,AUTHOR_ID
Spring Boot Guide,111111,499.99,1
Hibernate Mastery,222222,699.99,1
```

---

# 🧪 Testing

Implemented using:

- JUnit 5
- Mockito

### Test Coverage

| Test Class | Test Cases |
|------------|------------|
| AuthorServiceImplTest | 20 |
| BookServiceImplTest | 20 |
| ApplicationTests | 1 |

### Total

```text
41 Test Cases
```

Build Result:

```text
BUILD SUCCESS
Tests run: 41
Failures: 0
Errors: 0
```

---

# 🐳 Docker Support

## Build Image

```bash
docker build -t author-book-management .
```

## Run Container

```bash
docker run -d -p 8081:8081 --name author-book-app author-book-management
```

## Verify Container

```bash
docker ps
```

---

# ⚙️ CI/CD Pipeline

Implemented using Jenkins.

### Pipeline Stages

```text
Checkout Source Code
↓
Build
↓
Unit Testing
↓
Package
↓
Docker Image Creation
↓
Docker Container Deployment
↓
BUILD SUCCESS
```

### Auto Trigger

Pipeline automatically triggers on every Git commit through GitHub Webhooks.

---

# 🗄️ Database

PostgreSQL Database

### Tables

#### Authors

| Column |
|----------|
| id |
| name |
| email |
| country |

#### Books

| Column |
|----------|
| id |
| title |
| isbn |
| price |
| author_id |

---

# 🏛️ Design Principles Used

## SOLID Principles

### Single Responsibility Principle

- Controllers handle requests
- Services handle business logic
- Repositories handle persistence

### Dependency Inversion Principle

Controllers depend on interfaces instead of implementations.

---

# 🎯 Design Patterns Used

## Repository Pattern

```text
AuthorRepository
BookRepository
```

Provides abstraction over database operations.

---

## DTO Pattern

```text
AuthorRequestDTO
AuthorResponseDTO
BookRequestDTO
BookResponseDTO
```

Prevents exposing entities directly.

---

## Builder Pattern

Implemented using Lombok.

Example:

```java
Author.builder()
      .name("John")
      .email("john@gmail.com")
      .country("USA")
      .build();
```

---

# 📈 Assignment Deliverables Status

| Requirement | Status |
|-------------|---------|
| CRUD APIs | ✅ |
| Bulk Operations | ✅ |
| Validation | ✅ |
| Security | ✅ |
| BCrypt Authentication | ✅ |
| Role Based Authorization | ✅ |
| Swagger Documentation | ✅ |
| Logging | ✅ |
| Lombok | ✅ |
| Unit Testing | ✅ |
| CSV Import/Export | ✅ |
| Docker Image | ✅ |
| Docker Container | ✅ |
| Jenkins CI/CD | ✅ |
| REST API Best Practices | ✅ |
| SOLID Principles | ✅ |
| Design Patterns | ✅ |
| PostgreSQL Integration | ✅ |

---

# 👨‍💻 Author

**Arwin**

Backend Development Assignment - Module 5

Spring Boot + Hibernate/JPA + PostgreSQL + Security + Docker + Jenkins

---

# ⭐ Future Enhancements

- JWT Authentication
- Azure Deployment
- Kubernetes Deployment
- Docker Compose
- GitHub Actions
- Monitoring with Prometheus & Grafana
