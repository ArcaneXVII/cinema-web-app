# Cinema Web app
This project is a basic web application for a cinema. The app provides a clean interface for the users to explore upcoming movie screenings, check seat availability, reserve seats for a screening and more.

It is built using Spring Boot, Vue, PostreSQL.

## Feedback
Any constructive feedback is greatly appreciated, as I'm always looking for ways to improve my projects and programming knowledge. Thank you!

## Backend
The backend of the application is written in Java using the Spring Boot framework. It uses Gradle as the build tool.

### Structure

The main source code for the backend is located in the `Backend/src/main/java/ee/arcane/cinemawebapp/` directory. This directory contains several packages:

- `dto`: Contains Data Transfer Objects used to carry data to other processes.
- `controller`: Contains controllers that handle the requests.
- `respository`: Contains Spring entities and repositories used in the application for data interaction.
- `security`: Contains the configuration classes for Spring security and JWT authentication.
- `service`: Contains business logic for all the methods found in the controllers.
- `utility`: Contains utility classes. E.g. `SeatReservationUtility.java` stores the algorithm that calculates optimal positioning for a group of seats based on the available seats in a screening.

## Frontend

The frontend of the application is written in JavaScript using the Vue framework. It uses NPM as the package manager.

### Structure

The main source code for the frontend is located in the `Frontend/src/` directory. This directory contains several subdirectories:

- `components`: Contains Vue components.
- `views`: Contains Vue views.
- `router`: Contains the Vue Router configuration.
- `assets`: Contains front-end assets.

## Running the Application

To run the application, you need to start both the backend and the frontend.

### Running the Backend

To run the backend locally, navigate to the `Backend` directory and run the following commands:

A postgreSQL database docker container must be set up
```
docker-compose up -d
```

For the Spring application, it's easiest to run the `CinemaWebAppApplication.java` in either your preffered IDE or:

Build the backend application and execute the built .jar file
```bash
./gradlew build
```

### Running the Frontend

To run the frontend locally, navigate to the `Frontend` directory and run the following commands:

```bash
npm install
npm run dev
```

This will start the Vue application.

Once both the backend and the frontend are running, you can access the application by navigating to `http://localhost:5173/` in your web browser.

### Building the Backend

To build the backend, navigate to the `Backend` directory and run:

```bash
./gradlew build
```

### Building the Frontend

To build the frontend, navigate to the `Frontend` directory and run:

```bash
npm install
npm run build
```

**Detailed guide to navigating the web application can be found in the [Wiki](https://github.com/ArcaneXVII/cinema-web-app/wiki).**
