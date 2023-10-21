# Library Management System

This Java application implements a basic Library Management System with a SQL database. 
Users can perform CRUD operations on books in the library.

# Script

CREATE DATABASE library;
USE library;

CREATE TABLE books (
 id INT AUTO_INCREMENT NOT NULL,
  title VARCHAR(100) NOT NULL,
  author VARCHAR(100) NOT NULL,
  isbn VARCHAR(13) PRIMARY KEY,
  publication_year INT NOT NULL,
  genre VARCHAR(100)
);

## Setup

1. Set up a SQL database (MySQL) and create a database called `librarydb`.
2. Run the SQL script provided in `Step 2` of the project setup to create the `books` table.

## Running the Application

1. Clone this repository to your local machine.
2. Open the project in  Java IDE (e.g., Eclipse, IntelliJ IDEA).
3. Add the JDBC Driver to the project.
4. Configure the database connection in the `LibraryDatabase` class.
5. Run the `LibraryManagementApp` class to start the application.

## Usage

- Follow the console - menu to perform CRUD operations on books in the library.


## Contributors

- Manikanth Reddy



