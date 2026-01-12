# Car-Rental-System
How to create a modern car rental management system? This article will list what you need to understand and follow.

🚗 Car Rental Management System
A robust desktop application designed to streamline vehicle rental operations. Built with Java Swing for the frontend and Microsoft SQL Server for data persistence, this system implements a clean Service-DAO architecture.

🌟 Key Features
Real-time Rental Management:

Browse available vehicles in a dynamic table.

Automatic price calculation as you type the rental duration.

Tiered discount system: 10% (10+ days), 30% (20+ days), and 50% (30+ days).

Intelligent Return System:

Search active rentals by License Number.

Automated Penalty Calculation: Detects late returns and applies a $70/day fine automatically.

Generates detailed digital receipts upon return.

Database Integration: Secure transaction handling with SQL Server, ensuring vehicle status (AVAILABLE vs RENTED) is always synchronized.

User Experience: Uses Modal JDialogs to prevent interface clutter and enforce a logical workflow.

🛠 Tech Stack
Language: Java (JDK 11 or higher)

GUI Framework: Java Swing / AWT

Database: Microsoft SQL Server

Persistence: JDBC (Java Database Connectivity)

Architecture: MVC-inspired (Model, View, Service, DAO)

📂 Project Structure
Plaintext

src/
 ├── MainFrame.java       # Main entry point & Navigation Menu
 ├── RentalScreen.java    # Dialog for renting vehicles & price calculation
 ├── ReturnScreen.java    # Dialog for processing returns & penalties
 ├── Service.java         # Business logic layer (Price calculation, penalty rules)
 ├── DAO.java             # Data Access Object (SQL queries & updates)
 ├── DBConnection.java    # Database driver and connection configuration
 └── Models/              # Entity classes (Customer, Vehicle, Rental)
 
🚀 Getting Started
Prerequisites
Install SQL Server and SQL Server Management Studio (SSMS).

Have a Java IDE ready (IntelliJ IDEA, Eclipse, or NetBeans).

Download the Microsoft JDBC Driver for SQL Server (mssql-jdbc.jar).

Database Setup
Create a database named CarRentalDB.

Execute the table creation scripts for Vehicle, Customer, and Rental.

Ensure the connection string in DBConnection.java matches your local server instance:

Java

private static final String url = "jdbc:sqlserver://YOUR_SERVER_NAME:1433;databaseName=CarRentalDB;...";
Running the App
Clone this repository.

Add the JDBC driver to your project's libraries.

Run MainFrame.java.

📝 Future Roadmap & Reflections
Currently, the project focuses on core functionalities. However, I plan to introduce the following enhancements in future updates:

Payment Gateway Integration: Supporting multiple payment methods for a seamless checkout experience.

Advanced Filtering: Ability to filter vehicles by brand, price range, and type.

User Authentication: Secure login for administrators and customers.

Note: This project was developed primarily to solidify my understanding of Object-Oriented Programming (OOP) principles and the DAO pattern. It serves as a strong foundation for my journey into learning more advanced frameworks and libraries in the future.

Thank you for taking the time to review my work!

📄 License
This project is licensed under the Apache License - feel free to use it for educational purposes.

Author: [Huong Nguyen Quoc]
