# Mukerarugendo RW – Backend

- **NAME:** Schimea NIYITWUMVA
- **ID:** 28424
- **Project:** Mukerarugendo RW Backend (Mid-Term)
- **Stack:** Spring Boot, Spring Data JPA, PostgreSQL

## Run the API

Requirements: Java 17+, PostgreSQL (15+), Maven (the wrapper `./mvnw` is included).

1. Create the database user and database (as the PostgreSQL superuser, e.g. `sudo -u postgres psql`):
   ```sql
   CREATE USER mukerarugendo_user WITH PASSWORD 'Mukerarugendo2026';
   CREATE DATABASE mukerarugendo_db OWNER mukerarugendo_user;
   ```
2. Check `src/main/resources/application.properties` (URL `jdbc:postgresql://localhost:5432/mukerarugendo_db`, username, password).
3. Start the API from the project folder:
   ```bash
   ./mvnw spring-boot:run
   ```
   The API runs on http://localhost:8080 and Hibernate creates the tables (`ddl-auto=update`).
4. Import `mukerarugendo-r-w-midterm.postman_collection.json` into Postman and enter the seed data with the save requests.