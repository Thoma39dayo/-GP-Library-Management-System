# Library Management System README

## Overview

The Library Management System is a Java-based desktop application for managing library operations, including user authentication, item borrowing/returning, reservations, and inventory management.

## Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher.
- **IDE**: Any Java IDE (e.g., IntelliJ IDEA, Eclipse) or command-line tools for compilation and execution.
- **Dependencies**: The application uses Java Swing for the GUI, which is included in the standard JDK.

## How to Run the Executable

### Option 1: Using an IDE

1. **Clone or Download the Project**:

   - Copy the source files into a new project directory in your IDE.
   - Ensure the package structure (`com.librarymanagement`, `com.librarymanagement.gui`, etc.) is maintained.

2. **Set Up the Project**:

   - Create a new Java project in your IDE.
   - Import all provided `.java` files into the appropriate package structure:
     - `com.librarymanagement`: `MainApplication.java`
     - `com.librarymanagement.gui`: `PanelFactory.java`
     - `com.librarymanagement.user`: `User.java`, `Member.java`, `librarian.java`
     - `com.librarymanagement.inventory`: `LibraryItem.java`, `Book.java`, `DVD.java`, `Journal.java`
     - `com.librarymanagement.service`: `InventoryService.java`, `TransactionService.java`, `UserService.java`
     - `com.librarymanagement.transaction`: `CheckoutTransaction.java`, `ReturnTransaction.java`

3. **Compile and Run**:

   - Set `MainApplication.java` as the main class.
   - Build and run the project from the IDE.
   - The application window will open, displaying the login panel.

### Option 2: Using Command Line

1. **Organize Files**:

   - Place all `.java` files in a directory (e.g., `library-management`).
   - Maintain the package structure by creating subdirectories:
     - `com/librarymanagement`
     - `com/librarymanagement/gui`
     - `com/librarymanagement/user`
     - `com/librarymanagement/inventory`
     - `com/librarymanagement/service`
     - `com/librarymanagement/transaction`

2. **Compile the Code**:

   - Open a terminal in the project root directory.

   - Run the following command to compile all Java files:

     ```bash
     javac com/librarymanagement/*.java com/librarymanagement/gui/*.java com/librarymanagement/user/*.java com/librarymanagement/inventory/*.java com/librarymanagement/service/*.java com/librarymanagement/transaction/*.java
     ```

3. **Run the Application**:

   - Execute the main class:

     ```bash
     java com.librarymanagement.MainApplication
     ```

   - The application window will appear, starting with the login panel.

## Usage Instructions

1. **Login**:

   - Use the predefined credentials:
     - Member: ID=`member1`, Password=`password1`
     - Librarian: ID=`librarian1`, Password=`password1`
   - Select the appropriate login button (Member or Librarian).

2. **Member Interface**:

   - Browse items, checkout/return items by ID, reserve unavailable items, or view borrowing history.
   - Click "Logout" to return to the login screen.

3. **Librarian Interface**:

   - Browse items, add/remove items, or manage members (view records, adjust balances, ban/unban).
   - Double-click items or members in lists for detailed actions.
   - Click "Logout" to return to the login screen.

## Notes

- The system initializes with sample data (5 books, 5 DVDs, 5 journals, and predefined users).
- Ensure a stable JDK environment to avoid GUI rendering issues.
- No external libraries are required beyond the standard Java library.

For any issues, verify the package structure and JDK version compatibility.
