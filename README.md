# Cinema Booking System – REST API

This project is the RESTful backend service developed as part of the coursework for the *Programming Web Applications* module (Winter Semester 2024/25). The application provides API endpoints to manage a cinema booking system.

## 📚 Description

The REST API allows clients to interact with the cinema system through HTTP endpoints. It is implemented using **Spring Boot 3.4.2** and provides functionality to retrieve and manage information about movies, screenings, reservations, and seats.

The application serves as a backend extension to the main Java EE web application, enabling external systems to access and modify data via XML or JSON formats.

## 🛠 Technologies Used

- **Java 21**
- **Spring Boot 3.4.2**
- **Spring Web**
- **Spring Data JPA**
- **Spring Validation**
- **Hibernate ORM**
- **MySQL**
- **Maven**

## 📁 Structure

The codebase is organized according to standard Spring Boot conventions:

```
src/
├── main/
│   ├── java/
│   │   └── com.shweit.cinema/
│   │       ├── controllers/
│   │       ├── models/
│   │       └── repositories/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com.shweit.cinema
```

## 🔌 Available Endpoints

| Method | Endpoint           | Description                        |
|--------|--------------------|------------------------------------|
| GET    | `/api/movies`      | List all available movies          |
| GET    | `/api/movies/{id}` | List a movie based on their id     |
| POST   | `/api/movies`      | Add a movie to the DB              |
| GET    | `/api/tickets`     | List all tickets                   |
| GET    | `/api/tickets/{id}`| List one tickets based on their ID |

> The API supports **JSON** with the correct `Accept` header.

## 💾 Database

- MySQL schema is named after the student ID, as required by the coursework.
- You can find a setup script [here](https://github.com/Shweit/iu-cinema/blob/master/src/main/resources/sql/script.sql)

## 🚀 Getting Started

1. Clone the repository.
2. Create a MySQL database and adjust credentials in `src/main/resources/application.properties`.
3. Run the application using Maven:
  ```bash
   ./mvnw spring-boot:run
  ```

## 📄 License
This project is part of a university coursework submission and is not licensed for public or commercial use.
**Author**: Dennis van den Brock
**Course**: Programming Web Applications (DSPWA102201)
**Instructor**: Christoph Schopp
**Submission Deadline**: March 31, 2025
