# Todo App - Setup and Running

This document provides instructions for running the Todo App Docker container using either the `docker run` command or `docker-compose`.

```bash
docker run -p 8081:8081 -e DB_URL=jdbc:mysql://host.docker.internal:3306/todoapp -e DB_USER=root -e DB_PASSWORD=blackberry@1106 todo-app-new
```
or if you are inside the directory where the docker-compose.yml file is present, you can run the following command:
```bash
docker-compose up 
```
or
```bash
docker-compose -f C:/Users/Riti/Desktop/TODO-App/docker-compose.yml up -d
```
Important instructions to run the application:
1. The application runs on port 8081. Make sure this port is available on your machine.
2. The application uses MySQL as the database. Make sure you have a MySQL server running on your machine.
