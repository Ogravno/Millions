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

## Uer Manual
The staring page presents playrs with the optin of startng a new game or leading a save file. The game currently only suports 1 save file and pressing ```Load Game``` will load up the most recent save file. If the player chooses to start a new game they must select a csv file with the following format: ```# Ticker,Name,Price```. For example:
```
NVDA,Nvidia,191.27
AAPL,Apple Inc.,276.43
MSFT,Microsoft,404.68
```
A stock file can be found at src/main/recsources/stocckFiles/stocks.csv. It is this one that will be selected by pressing ```Select standard stocks``` in the new game menu.

During a game players can purchase, sell and get an overview over the stocks in the game. Purchasing shares is done on the Exchange page. The Portfolio page presents players with an oveview over their current portfolio and lets them sell their shares. The Transactions page gives users an overview of their previous transactions. Advancing to the next week can be done by pressing the ```Advance``` button in the bottom of the sidebar.

Saving the game can be done by pressing the ```Save and to Maim Menu``` button on the bottom of the sidebar. To end the game, players must press the ```End game``` button on the bottom of the sidebar and will then be given the choice between returning to the Main Menu or Closing the game. Note: This will not save any progress.

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
    │       │   ├── dto/             # Data Transfer Objects for saving and loading game data
    │       │   ├── exchange/        # Exchange and market logic
    │       │   ├── filemanagement/  # File handling
    │       │   ├── investment/      # Stocks and shares
    │       │   ├── player/          # Player and portfolio
    │       │   ├── transaction/     # Transactions and calculators
    │       │   │    └── calculator/ # Transaction calculations
    │       │   └── GameSession.java # Main game session class
    │       ├── observer/            # Observer pattern interfaces/classes
    │       ├── view/                # JavaFX views and dialogs
    │       │   └── components/      # Reusable GUI components
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
            └── model/
                ├── exchange/
                ├── filemanagement/
                ├── investment/
                ├── player/
                ├── transaction/
                │    └── calculator/
                └── GameSessionTest.java
```

## Link to repository
https://github.com/Ogravno/Millions

## How to run the project

### Windows
If you are using Windows, you can download `StockSim-windows.zip`,
extract the ZIP file, and start the application by running:

StockSim.exe

No Java installation is required.

### MacOS / Linux

Clone or download the repository before running the project.

Requirements:

- JDK version 25
- Maven version 3.9.x

Open the project folder in a terminal or IDE and run:
Run ```mvn javafx:run``` to run the application.

## How to run the tests

The test are written using JUint6.

Run ```mvn clean test``` to run the tests.

Alternatively, using Intellij:  
- Open src folder
- Open test folder
- Right-click on java folder → Run tests

An HTML test coverage report is generated when the sests are run. The report can be found under ```target/site/jacoco```.
