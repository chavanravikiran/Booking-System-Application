# 📘 Booking System Application

A RESTful **Booking System** built with **Spring Boot 3.x** and **Java 17**, using **PostgreSQL** as the default database.  
The system supports **JWT authentication** and **Role-Based Access Control (RBAC)** for managing resources and reservations.

---

## 🚀 Features
- **Authentication** with JWT
- **Role-Based Access Control**
  - `ADMIN`: full CRUD on resources and reservations
  - `USER`: view resources, create reservations, view own reservations
- **Resources Management**
  - CRUD for Admin, Read-only for User
- **Reservations Management**
  - Filter by status & price
  - Pagination & sorting support
  - Optional prevention of overlapping confirmed bookings
- **Swagger/OpenAPI Documentation**
---

## 🛠 Tech Stack
- Java 17+
- Spring Boot 3.x
- PostgreSQL
- Spring Security (JWT)
- Spring Data JPA
- Lombok (optional)
- Springdoc OpenAPI (Swagger UI)

---

## ⚙️ Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/chavanravikiran/Booking-System-Application.git
cd Booking-System-Application

⚙️ Configure Environment Variables
  # PostgreSQL Database Config
  server.port=8085
  
  spring.datasource.url=jdbc:postgresql://localhost:5432/BookingSystemApplication
  spring.datasource.username=postgres
  spring.datasource.password=root123
  spring.datasource.driver-class-name=org.postgresql.Driver
  spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
  spring.jpa.generate-ddl=true
  spring.main.allow-circular-references=true
  
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
  
  #springdoc.api-docs.path=/v3/api-docs
  #springdoc.swagger-ui.path=/swagger-ui.html
  springdoc.swagger-ui.operationsSorter=method
  springdoc.show-actuator=true
  
  springdoc.api-docs.path=/v3/api-docs
  springdoc.swagger-ui.path=/swagger-ui.html

👍 JWT Config in JwtUtil
  private static final String SECRET_KEY = "EcommerceProjectSecretKeyForJwtAuthentication123456789012345678901234567890"; 
  private static final int TOKEN_VALIDITY= 3600 * 5;

👉Build and Run the Application
  ./mvnw clean install
  ./mvnw spring-boot:run

🔑 Seed Users
  | Username   | Password  | Role       |
  | --------   | --------  | ---------- |
  | ADMIN      | Admin@123 | ROLE_ADMIN |
  |ravikiran123| Ravi@1997 | ROLE_USER  |

API Documentation
Once the app is running, open:
👉 http://localhost:8085/swagger-ui/index.html#/

📂 API Endpoints
Authentication
  POST /auth/login → authenticate and receive JWT token

Resources
  GET /resources → list all resources (paginated)
  GET /resources/{id} → get resource by ID
  POST /resources → ADMIN only, create resource
  PUT /resources/{id} → ADMIN only, update resource
  DELETE /resources/{id} → ADMIN only, delete resource

Reservations
GET /reservations →
  ADMIN: list all reservations
  USER: list only own reservations
Query params supported: status, minPrice, maxPrice, page, size, sort
GET /reservations/{id} →
  ADMIN: any reservation
  USER: only own reservations

POST /reservations → create reservation
(for USER, userId is derived from JWT, not request body)
PUT /reservations/{id} → update reservation
DELETE /reservations/{id} → delete reservation

🧪 Tests (Optional but Recommended)
Run unit tests:
  ./mvnw test

📌 Assumptions & Trade-offs
  Stateless JWT-based authentication (no sessions maintained)
  Passwords stored securely using BCrypt
  Default database is PostgreSQL, but MySQL can be used with minor config changes
  Reservation filters & pagination are implemented using Spring Data JPA
  Overlapping confirmed reservations prevention is optional
  Global exception handling and meaningful commit history are followed

✅ Deliverables
  Source Code
  README with setup steps & API details
  Swagger/OpenAPI docs
  Seed Users
  Documented assumptions & trade-offs

## 📄 Swagger Documentation

You can view or download the Swagger documentation PDF here:

[Swagger Documentation PDF](src/main/java/docs/Swagger-Documentation-BookingSystem.pdf)
