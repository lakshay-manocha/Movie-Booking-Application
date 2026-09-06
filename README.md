# 🎬 Movie Ticket Booking Application

A scalable **Movie Ticket Booking System** built using **Spring Boot and Microservices Architecture**. The application is designed to simulate a real-world movie booking platform with independent services for authentication, movie/catalog management, seat booking, payments, service discovery, API routing, and event-driven communication.

The project focuses on building a **distributed, scalable, and loosely coupled backend system** using modern enterprise Java technologies.

---

## 🚀 Key Features

* 🔐 User authentication and authorization
* 🎬 Movie and theatre catalog management
* 🏢 Theatre and screen management
* 💺 Seat availability and booking
* 💳 Payment service integration
* 🔄 Event-driven communication using Apache Kafka
* 🌐 Centralized API Gateway
* 🔎 Service discovery using Eureka
* 📦 Shared event models between services
* 🧩 Independent and loosely coupled microservices
* 🗄️ Polyglot persistence using different databases where appropriate
* ⚡ Scalable backend architecture

---

## 🏗️ System Architecture

The application follows a **Microservices Architecture** where individual business capabilities are implemented as independent services.

```text
                         ┌─────────────────────┐
                         │       Client        │
                         │  Web / Mobile App   │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │     API Gateway     │
                         └──────────┬──────────┘
                                    │
             ┌──────────────────────┼──────────────────────┐
             │                      │                      │
             ▼                      ▼                      ▼
      ┌─────────────┐       ┌─────────────┐       ┌─────────────┐
      │    Auth     │       │   Catalog   │       │    Seat     │
      │   Service   │       │   Service   │       │   Booking   │
      └──────┬──────┘       └──────┬──────┘       └──────┬──────┘
             │                     │                       │
             │                     │                       │
             ▼                     ▼                       ▼
      ┌─────────────┐       ┌─────────────┐       ┌─────────────┐
      │   Auth DB   │       │  Catalog DB │       │  Booking DB │
      └─────────────┘       └─────────────┘       └─────────────┘
                                   
                         ┌─────────────────────┐
                         │       Kafka         │
                         │  Event Streaming    │
                         └──────────┬──────────┘
                                    │
                     ┌──────────────┴──────────────┐
                     ▼                             ▼
              ┌─────────────┐              ┌─────────────┐
              │   Payment   │              │    Other    │
              │   Service   │              │ Consumers   │
              └─────────────┘              └─────────────┘

                         ┌─────────────────────┐
                         │ Service Discovery   │
                         │       Eureka        │
                         └─────────────────────┘
```

---

## 🧩 Microservices

The project is divided into multiple independently deployable services.

### 1. 🎬 Catalog Service

Responsible for managing movie and theatre-related information.

Responsibilities include:

* Movies
* Theatres
* Screens
* Movie metadata
* Theatre information

**Database:** MongoDB

---

### 2. 🔐 Authentication Service

Responsible for user authentication and authorization.

Responsibilities include:

* User registration
* User authentication
* Authorization
* Security-related operations
* Token-based authentication

---

### 3. 💺 Seat Booking Service

Responsible for managing movie seat reservations.

Responsibilities include:

* Seat availability
* Seat selection
* Seat reservation
* Booking management
* Preventing conflicting bookings

**Database:** PostgreSQL

---

### 4. 💳 Payment Service

Handles payment-related operations associated with movie bookings.

Responsibilities include:

* Payment processing
* Payment status management
* Booking-payment coordination
* Payment events

---

### 5. 🌐 API Gateway

Acts as the single entry point for clients.

Responsibilities include:

* Routing client requests
* Forwarding requests to appropriate microservices
* Centralized entry point
* Service abstraction
* Cross-cutting concerns

Instead of clients communicating directly with every microservice:

```text
Client → API Gateway → Microservice
```

---

### 6. 🔎 Discovery Service

The Discovery Service provides **service registration and discovery** using Eureka.

Instead of hardcoding service addresses:

```text
http://localhost:8081
http://localhost:8082
http://localhost:8083
```

services can discover one another dynamically through the service registry.

```text
                 ┌──────────────┐
                 │    Eureka    │
                 │   Registry   │
                 └──────┬───────┘
                        │
        ┌───────────────┼───────────────┐
        ▼               ▼               ▼
     Catalog           Auth          Booking
```

---

### 7. 📡 Common Event Module

The `CommonEvent` module contains shared event-related models used for communication between services.

This helps maintain a consistent event contract across the system.

---

### 8. 📨 Kafka Infrastructure

Apache Kafka is used for **asynchronous, event-driven communication** between services.

Example booking flow:

```text
User
 │
 ▼
Seat Booking Service
 │
 │ Booking Created
 ▼
Kafka
 │
 ├──────────────► Payment Service
 │
 └──────────────► Other Consumers
```

This reduces direct coupling between services and allows services to react to events independently.

---

# 🛠️ Technology Stack

| Category          | Technology           |
| ----------------- | -------------------- |
| Language          | Java                 |
| Backend           | Spring Boot          |
| Architecture      | Microservices        |
| API Gateway       | Spring Cloud Gateway |
| Service Discovery | Netflix Eureka       |
| Messaging         | Apache Kafka         |
| Catalog Database  | MongoDB              |
| Booking Database  | PostgreSQL           |
| Build Tool        | Maven                |
| Containerization  | Docker               |
| Version Control   | Git / GitHub         |

---

# 🔄 Event-Driven Architecture

One of the important aspects of this project is the use of **event-driven communication**.

Instead of tightly coupling services through synchronous REST calls for every operation, important business events can be published to Kafka.

For example:

```text
Booking Request
      │
      ▼
Seat Booking Service
      │
      ▼
Booking Created Event
      │
      ▼
     Kafka
      │
      ├──────────────► Payment Service
      │
      └──────────────► Other Services
```

This architecture provides:

* Loose coupling
* Asynchronous communication
* Better scalability
* Independent service development
* Improved fault isolation

---

# 🗄️ Database Strategy

The application follows a **polyglot persistence** approach.

Different services can use databases that best fit their requirements.

### Catalog Service

```text
MongoDB
```

Suitable for flexible movie, theatre, screen, and catalog documents.

### Seat Booking Service

```text
PostgreSQL
```

Suitable for transactional booking and seat-management operations.

This approach keeps databases isolated between services.

```text
Catalog Service ──────► MongoDB

Booking Service ──────► PostgreSQL

Auth Service ──────────► Auth Database

Payment Service ───────► Payment Database
```

---

# 📁 Project Structure

```text
Movie-Booking-Application/
│
├── Catelog_Service/
│
├── CommonEvent/
│
├── Kafka_Image/
│
├── apigateway/
│
├── auth/
│
├── discovery/
│
├── paymentservice/
│
└── seatbooking/
```

> **Note:** `Catelog_Service` is retained as the current repository directory name.

---

# 🔧 Prerequisites

Make sure the following are installed:

* Java JDK
* Maven
* MongoDB
* PostgreSQL
* Apache Kafka
* Docker
* Git

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

Verify Git:

```bash
git --version
```

---

# 📥 Installation

Clone the repository:

```bash
git clone https://github.com/lakshay-manocha/Movie-Booking-Application.git
```

Navigate into the project:

```bash
cd Movie-Booking-Application
```

---

# ⚙️ Configuration

Each microservice contains its own Spring Boot configuration.

Before running the services, configure:

* Database URLs
* Database credentials
* Kafka broker configuration
* Eureka server configuration
* Service ports
* Authentication secrets
* Payment configuration

Do **not** commit production credentials or secrets to GitHub.

Use environment variables or external configuration for sensitive information.

---

# ▶️ Running the Application

Start the infrastructure components first.

### 1. Start databases

Start:

```text
MongoDB
PostgreSQL
```

### 2. Start Kafka

Start the Kafka infrastructure required by the application.

### 3. Start Discovery Service

Start:

```text
discovery
```

The Eureka server should be available before starting dependent services.

### 4. Start Microservices

Start the services:

```text
auth
Catelog_Service
seatbooking
paymentservice
```

### 5. Start API Gateway

Finally start:

```text
apigateway
```

The client can then communicate through the gateway rather than accessing individual services directly.

---

# 🔄 Typical Booking Flow

A simplified movie booking workflow looks like:

```text
1. User authenticates
        │
        ▼
2. Browse movies
        │
        ▼
3. Select theatre
        │
        ▼
4. Select show/screen
        │
        ▼
5. Check available seats
        │
        ▼
6. Select seats
        │
        ▼
7. Create booking
        │
        ▼
8. Publish booking event
        │
        ▼
9. Process payment
        │
        ▼
10. Confirm booking
```

---

# 🎯 Design Principles

The project demonstrates several important backend engineering concepts.

### Microservice Independence

Each business domain is separated into an independent service.

### Loose Coupling

Services communicate through APIs and asynchronous events rather than sharing business logic.

### Database Per Service

Services maintain ownership of their respective data.

### Service Discovery

Services can locate one another dynamically through Eureka.

### API Gateway Pattern

Clients interact with a centralized gateway rather than individual services.

### Event-Driven Communication

Kafka enables asynchronous communication between services.

### Scalability

Individual services can be scaled independently according to their workload.

---

# 📊 Architecture Benefits

The architecture provides several advantages over a monolithic application:

| Feature              | Benefit                           |
| -------------------- | --------------------------------- |
| Independent services | Easier development and deployment |
| Kafka                | Asynchronous communication        |
| Eureka               | Dynamic service discovery         |
| API Gateway          | Centralized request routing       |
| Separate databases   | Data isolation                    |
| MongoDB              | Flexible catalog structure        |
| PostgreSQL           | Strong transactional consistency  |
| Docker               | Consistent deployment environment |

---

# 🧪 Testing

Each service can be tested independently.

Recommended testing strategy:

```text
Unit Tests
    ↓
Integration Tests
    ↓
API Tests
    ↓
Service-to-Service Tests
    ↓
End-to-End Tests
```

API testing can be performed using tools such as:

* Postman
* cURL
* Swagger/OpenAPI, if configured

---

# 🔮 Future Enhancements

Potential improvements include:

* [ ] Redis-based caching
* [ ] Distributed locking for seat booking
* [ ] Saga-based distributed transactions
* [ ] CQRS implementation
* [ ] Centralized configuration service
* [ ] Distributed tracing
* [ ] Prometheus + Grafana monitoring
* [ ] ELK-based centralized logging
* [ ] Docker Compose deployment
* [ ] Kubernetes deployment
* [ ] CI/CD pipeline
* [ ] Notification service
* [ ] Email/SMS booking confirmation
* [ ] Recommendation system
* [ ] Admin dashboard
* [ ] Advanced payment gateway integration

---

# 🔐 Security Considerations

For production deployment, the following should be implemented:

* Secure JWT configuration
* Password hashing
* HTTPS
* Role-based authorization
* Secrets management
* API rate limiting
* Input validation
* Database access restrictions
* Kafka authentication and authorization

Never commit:

```text
Passwords
API Keys
JWT Secrets
Database Credentials
Payment Credentials
Private Keys
```

---

# 📚 Concepts Demonstrated

This project is useful for learning and demonstrating:

* Java
* Spring Boot
* Spring Cloud
* Microservices Architecture
* REST APIs
* API Gateway
* Service Discovery
* Apache Kafka
* Event-Driven Architecture
* MongoDB
* PostgreSQL
* Docker
* Distributed Systems
* Database-per-Service
* Asynchronous Communication
* Scalability
* Fault Isolation

---

# 👨‍💻 Author

**Lakshay Manocha**

B.Tech Computer Science & Engineering

GitHub:
https://github.com/lakshay-manocha

---

# ⭐ Support

If you find this project useful, consider giving the repository a ⭐ on GitHub.

---

## 📄 License

This project is intended for educational and development purposes.
