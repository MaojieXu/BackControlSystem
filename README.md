![21e147194cbcc019bd687ee7af775b9](https://github.com/user-attachments/assets/4eef0183-ec30-41e6-b3be-c66da24c0c28)
# Backend Management System

This is a backend management system designed to manage articles, users, and article categories. The project implements create, read, update, and delete (CRUD) operations for articles, users, and article categories, providing a convenient and efficient solution for content management.

## Project Structure

- **BackControlSystem-back**: Backend service, built with Spring Boot, responsible for business logic and data storage.
- **BackControlSystem-front**: Frontend component, using the Vue framework, provides the user interface for operations.

## Features

1. **User Management**
   - Add new users
   - Edit user information
   - Delete users
   - View user list and details

2. **Article Management**
   - Add new articles
   - Edit article content and details
   - Delete articles
   - View article list and details

3. **Article Category Management**
   - Create new article categories
   - Edit article category details
   - Delete article categories
   - View category list

## Technology Stack

- **Backend**: Spring Boot, MySQL, MyBatis
- **Frontend**: Vue, Element plus
- **Data Caching**: Redis
- **File Storage**: Alibaba Cloud OSS
- **API Documentation**: Knife4j

## Getting Started

1. Clone the repository:

   ```bash
   git clone <repository_url>
Configure Backend Service

Edit the application.yml file to set up the database, Redis, and Alibaba Cloud OSS configurations.
Start Backend Service

bash
Copy code
cd BackControlSystem-back
mvn spring-boot:run
Start Frontend Project

bash
Copy code
cd BackControlSystem-front
npm install
npm run serve
Access the System

