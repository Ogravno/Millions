# Millions
Group 16

Members:

Odin Grav | Student ID - 132023

Robin Strand Prestmo | Student ID - 142382

## Project Description
Millions is a stock trading simulation game developed as part of the course IDATT2003 – Programmering 2 at NTNU.

The application allows the player to simulate investing in stocks through a graphical user interface built with JavaFX. The player can buy and sell shares, track portfolio performance, and progress through multiple in-game weeks while stock prices change dynamically.

The project focuses on object-oriented programming principles, layered architecture, file handling, testing, and graphical user interface development.

Main features

- Create and manage a game session
- Buy and sell stocks
- Simulated stock market with changing prices
- Portfolio management
- Transaction history
- Net worth and profit/loss tracking
- Stock statistics and price history
- File handling for loading stock data
- Graphical user interface built with JavaFX

## Project Structure
The project follows a layered and modular architecture.

All source code is stored under: ``` src/main/java/edu/ntnu/idatt2003/group16/ ```

```
src/
└── main/
    ├── java/
    │   └── edu/ntnu/idatt2003/group16/
    │       ├── controller/          # Application controllers
    │       ├── factory/             # Factory classes
    │       ├── model/               # Core domain logic
    │       │   ├── exchange/        # Exchange and market logic
    │       │   ├── filemanagement/  # File handling
    │       │   ├── investment/      # Stocks and shares
    │       │   ├── player/          # Player and portfolio
    │       │   └── transaction/     # Transactions and calculators
    │       │       └── calculator/  # Transaction calculations
    │       ├── observer/            # Observer pattern interfaces/classes
    │       ├── view/                # JavaFX views and dialogs
    │       │   └── components/      # Reusable GUI components
    │       ├── GameSession.java     # Main game session class
    │       └── Main.java            # Application entry point
    │
    └── resources/
        ├── css/                     # Stylesheets
        └── stockFiles/              # CSV stock files
```

All test files are stored under: 
```
src/
└── test/
    └── java/
        └── edu/ntnu/idatt2003/group16/
            ├── factory/
            ├── model/
            │   ├── exchange/
            │   ├── filemanagement/
            │   ├── investment/
            │   ├── player/
            │   └── transaction/
            │       └── calculator/
            └── GameSessionTest.java
```

## Link to repository
https://github.com/Ogravno/Millions

## How to run the project

Requirements:
- JDK version 25
- Maven version 3.9.x

Run ```mvn clean javafx:run``` to run the application.

## How to run the tests

The test are written using JUint5.

Run ```mvn clean install``` to run the tests.

Alternatively, using Intellij:  
- Open src folder
- Open test folder
- Right-click on java folder → Run tests

An HTML test coverage report is generated when the sests are run. The report can be found under ```target/site/jacoco```.

## References


