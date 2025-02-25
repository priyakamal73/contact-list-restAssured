# Contact List API Testing with RestAssured  

This repository contains automated API tests for the Contact List application using RestAssured in Java. The framework follows best practices for API automation and integrates with reporting tools for better test analysis.  

## Features  
- **RestAssured** for API automation  
- **TestNG** for test execution  
- **JSON Schema Validation**  
- **Assertions for Response Validation**  
- **Logging & Reporting**  

## Project Structure  
```
contact-list-restAssured/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── api/         # API request classes
│   │   │   ├── utils/       # Helper utilities
│   ├── test/
│   │   ├── java/
│   │   │   ├── tests/       # Test cases
│   │   │   ├── hooks/       # Test setup & teardown
│── pom.xml                  # Maven dependencies
│── README.md                # Project documentation
```

## Setup Instructions  

### Prerequisites  
- **Java 11+** installed  
- **Maven** installed  
- **IDE** (e.g., IntelliJ, Eclipse)  

### Installation  
1. Clone the repository:  
   ```sh
   git clone https://github.com/priyakamal73/contact-list-restAssured.git
   cd contact-list-restAssured
   ```
2. Install dependencies:  
   ```sh
   mvn clean install
   ```
3. Run tests:  
   ```sh
   mvn test
   ```

## Reporting  
Test execution reports are generated in the name  `ExtentReport.html` directory.  
