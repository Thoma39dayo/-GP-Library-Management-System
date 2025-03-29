import models.*;
import services.*;
import exceptions.*;
import javax.swing.*;

import java.util.Scanner;

public class MainApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final InventoryService inventoryService = new InventoryService();
    private static final TransactionService transactionService = new TransactionService();
    
    public static String getPasswordFromDialog() {
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(
            null, 
            passwordField, 
            "Enter Password", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );

        return (option == JOptionPane.OK_OPTION) ? new String(passwordField.getPassword()) : null;
    }
    public static void main(String[] args) {
        seedData(); 
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Login as Member");
            System.out.println("2. Login as Librarian");
            System.out.println("3. Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    loginAsMember();
                    break;
                case "2":
                    loginAsLibrarian();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loginAsMember() {
        System.out.print("\nEnter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = getPasswordFromDialog();
        if (password == null) {
            System.out.println("Login canceled.");
            return;
        }

        User user = userService.authenticate(id, password, Member.class);
        if (user == null) {
            System.out.println("Invalid credentials.");
            return;
        }
        System.out.println("Welcome, " + user.getName());
        memberMenu((Member) user);
    }

    private static void loginAsLibrarian() {
        System.out.print("\nEnter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = userService.authenticate(id, password, Librarian.class);
        if (user == null) {
            System.out.println("Invalid credentials.");
            return;
        }
        System.out.println("Welcome, " + user.getName());
        librarianMenu((Librarian) user);
    }

    private static void memberMenu(Member member) {
        while (true) {
            System.out.println("\nMember Menu:");
            System.out.println("1. Browse Items");
            System.out.println("2. Checkout Item");
            System.out.println("3. Return Item");
            System.out.println("4. Logout");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    browseItems();
                    break;
                case "2":
                    checkoutItem(member);
                    break;
                case "3":
                    returnItem(member);
                    break;
                case "4":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void librarianMenu(Librarian librarian) {
        while (true) {
            System.out.println("\nLibrarian Menu:");
            System.out.println("1. Browse Items");
            System.out.println("2. Add Item");
            System.out.println("3. Remove Item");
            System.out.println("4. Logout");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    browseItems();
                    break;
                case "2":
                    addItem();
                    break;
                case "3":
                    removeItem();
                    break;
                case "4":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void browseItems() {
        System.out.println("\nAvailable Items:");
        for (LibraryItem item : inventoryService.getAllItems()) {
            System.out.println(item.getId() + " - " + item.getTitle());
        }
    }

    private static void checkoutItem(Member member) {
        System.out.print("Enter Item ID to checkout: ");
        String itemId = scanner.nextLine();
        try {
            LibraryItem item = inventoryService.findItem(itemId);
            transactionService.checkoutItem(member.getId(), item);
            System.out.println("Successfully checked out: " + item.getTitle());
        } catch (ItemNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnItem(Member member) {
        System.out.print("Enter Item ID to return: ");
        String itemId = scanner.nextLine();
        try {
            LibraryItem item = inventoryService.findItem(itemId);
            transactionService.returnItem(member.getId(), item);
            System.out.println("Successfully returned: " + item.getTitle());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addItem() {
        System.out.print("Enter Item Type (Book/DVD/Journal): ");
        String type = scanner.nextLine().toLowerCase();
        System.out.print("Enter Item ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        switch (type) {
            case "book":
                System.out.print("Enter Author: ");
                String author = scanner.nextLine();
                inventoryService.addItem(new Book(id, title, author));
                break;
            case "dvd":
                System.out.print("Enter Runtime (minutes): ");
                int runtime = Integer.parseInt(scanner.nextLine());
                inventoryService.addItem(new DVD(id, title, runtime));
                break;
            case "journal":
                System.out.print("Enter Publisher: ");
                String publisher = scanner.nextLine();
                inventoryService.addItem(new Journal(id, title, publisher));
                break;
            default:
                System.out.println("Invalid item type.");
                return;
        }
        System.out.println("Item added successfully.");
    }

    private static void removeItem() {
        System.out.print("Enter Item ID to remove: ");
        String itemId = scanner.nextLine();
        try {
            inventoryService.removeItem(itemId);
            System.out.println("Item removed successfully.");
        } catch (ItemNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void seedData() {
        // Adding sample users
        userService.registerUser(new Member("MEM_123", "John Doe", "pass123", 5));
        userService.registerUser(new Librarian("LIB_456", "Alice Smith", "admin456"));

        // Adding sample items
        inventoryService.addItem(new Book("B001", "Effective Java", "Joshua Bloch"));
        inventoryService.addItem(new DVD("D001", "Inception", 148));
        inventoryService.addItem(new Journal("J001", "Science Weekly", "Nature Publishing"));
    }
}
