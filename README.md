TFMS – Trade Finance Management System

# Overview
TFMS is a web-based **Trade Finance Management System** for managing Letters of Credit, Bank Guarantees, Trade Documents, Risk Assessments, and Compliance.  
Built using **Java 23, Spring Boot 3.5.4, Thymeleaf, MySQL**.

# Features
- Add / View / Edit / Delete Letters of Credit, Trade Documents, Compliance, and Risk Assessment records  
- Dashboard with quick access to all modules  
- Demo mode — no login required for testing  
- Hibernate auto-creates database tables  

# Technology Stack
- Backend: Spring Boot, Spring Data JPA, Hibernate  
- Frontend: Thymeleaf  
- Database: MySQL  
- Server: Embedded Tomcat (port 8083)  

# Database Tables
- `letter_of_credit`  
- `bank_guarantee`  
- `trade_document`  
- `risk_assessment`  
- `compliance`  

# How to Run
1. Open project in **Eclipse / IntelliJ**  
2. Update **application.properties** with your MySQL credentials:  
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tfms
   spring.datasource.username=root
   spring.datasource.password=YourPasswordHere
   spring.jpa.hibernate.ddl-auto=update
   server.port=8083
