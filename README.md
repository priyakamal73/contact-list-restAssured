# Contact List API Testing with RestAssured  

This repository contains automated API tests for the Contact List application using RestAssured in Java. The framework follows best practices for API automation and integrates with reporting tools for better test analysis.  

## Features  
- **RestAssured** for API automation  
- **TestNG** for test execution  
- **Assertions for Response Validation**  
- **Logging & Reporting**  

## Project Structure  
```
ContactListApplicationAPIs/
│── .idea/                      # IDE-related settings  
│── src/  
│   ├── main/  
│   │   ├── java/               # (Empty or contains application logic)  
│   ├── test/  
│   │   ├── java/  
│   │   │   ├── base/  
│   │   │   │   ├── BaseClass.java  # Base setup for tests  
│   │   │   ├── POJO/  
│   │   │   │   ├── AddContactDetails.java  
│   │   │   │   ├── AddNewUserDetails.java  
│   │   │   │   ├── LoginUserDetails.java  
│   │   │   │   ├── UpdateFullContactDetails.java  
│   │   │   │   ├── UpdatePartialContactDetails.java  
│   │   │   │   ├── UpdateUserDetails.java  
│   │   │   ├── tests/  
│   │   │   │   ├── TC1_AddNewUser.java  
│   │   │   │   ├── TC2_GetUserProfile.java  
│   │   │   │   ├── TC3_UpdateUser.java  
│   │   │   │   ├── TC4_LoginUser.java  
│   │   │   │   ├── TC5_AddContact.java  
│   │   │   │   ├── TC6_GetContactList.java  
│   │   │   │   ├── TC7_GetContact.java  
│   │   │   │   ├── TC8_UpdateContact.java  
│   │   │   │   ├── TC9_UpdateContact.java  
│   │   │   │   ├── TC10_LogoutUser.java  
│   │   │   ├── utils/  
│   │   │   │   ├── config.properties  # Configuration file  
│   │   │   │   ├── reportInfo         # Reporting info  
│── target/                      # Compiled files and test reports
|──ExtentReport.html             # Extent report
|──pom.xml                       # pom file
|──testng.xml                    # testng file to run all the tests
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
Test execution report is generated in the name  `ExtentReport.html`.  
