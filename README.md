Library Management System

Introduction

This Library Management System is a console-based application written in Java. It allows librarians and members to manage books, DVDs, and journals. The system supports user authentication, item browsing, checkout, return, and inventory management.

Features

Member Login: Members can browse items, check out, and return books.
Librarian Login: Librarians can browse, add, and remove items from the inventory.
Inventory Management: Books, DVDs, and journals can be added or removed.
Transaction Handling: Checkout and return transactions are recorded.

Project Structure
src/  
│── MainApplication.java  # Entry point of the system  
│  
├── models/               # Data Models  
│   ├── Book.java  
│   ├── DVD.java  
│   ├── Journal.java  
│   ├── LibraryItem.java  
│   ├── Member.java  
│   ├── Librarian.java  
│   ├── User.java  
│   ├── CheckoutTransaction.java  
│   ├── ReturnTransaction.java  
│  
├── services/             # Business Logic  
│   ├── UserService.java  
│   ├── InventoryService.java  
│   ├── TransactionService.java  
│  
├── exceptions/           # Custom Exceptions  
│   ├── ItemNotAvailableException.java  
Future Improvements

Add a GUI interface for better user experience.
Implement a database for persistent storage.
