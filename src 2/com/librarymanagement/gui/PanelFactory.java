package com.librarymanagement.gui;

import com.librarymanagement.inventory.LibraryItem;
import com.librarymanagement.service.InventoryService;
import com.librarymanagement.service.TransactionService;
import com.librarymanagement.service.UserService;
import com.librarymanagement.transaction.CheckoutTransaction;
import com.librarymanagement.user.Librarian;
import com.librarymanagement.user.Member;
import com.librarymanagement.user.User;
import com.librarymanagement.MainApplication;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PanelFactory {
    private final UserService userService;
    private final InventoryService inventoryService;
    private final TransactionService transactionService;

    public PanelFactory(UserService userService, InventoryService inventoryService, TransactionService transactionService) {
        this.userService = userService;
        this.inventoryService = inventoryService;
        this.transactionService = transactionService;
    }

    public JPanel createLoginPanel(MainApplication app) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        JButton memberLoginButton = new JButton("Member Login");
        JButton librarianLoginButton = new JButton("Librarian Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(showPasswordCheckbox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(memberLoginButton, gbc);
        gbc.gridx = 1;
        panel.add(librarianLoginButton, gbc);

        showPasswordCheckbox.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordCheckbox.isSelected() ? '\0' : (Character) UIManager.get("PasswordField.echoChar"));
        });
        //
        memberLoginButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            try {
                User user = userService.authenticate(id, password, Member.class);
                app.switchToPanel(createMemberPanel((Member) user, app));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Authentication failed: " + ex.getMessage());
            }
        });

        librarianLoginButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            try {
                User user = userService.authenticate(id, password, Librarian.class);
                app.switchToPanel(createLibrarianPanel((Librarian) user, app));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Authentication failed: " + ex.getMessage());
            }
        });

        return panel;
    }

    public JPanel createMemberPanel(Member member, MainApplication app) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + member.getName() + " (Member)");
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton browseButton = new JButton("Browse Items");
        JButton checkoutButton = new JButton("Checkout");
        JButton returnButton = new JButton("Return");
        JButton reserveButton = new JButton("Reserve Item");
        JButton viewHistoryButton = new JButton("View Borrowing History");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(browseButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(reserveButton);
        buttonPanel.add(viewHistoryButton);
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        browseButton.addActionListener(e -> {
            List<LibraryItem> items = inventoryService.getAllItems();
            StringBuilder itemList = new StringBuilder();
            for (LibraryItem item : items) {
                itemList.append(item.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(panel, itemList.toString());
        });

        checkoutButton.addActionListener(e -> {
            String itemId = JOptionPane.showInputDialog(panel, "Please enter the item ID to checkout:");
            try {
                transactionService.checkoutItem(member, itemId);
                JOptionPane.showMessageDialog(panel, "Checkout successful");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Checkout failed: " + ex.getMessage());
            }
        });

        returnButton.addActionListener(e -> {
            String itemId = JOptionPane.showInputDialog(panel, "Please enter the item ID to return:");
            try {
                transactionService.returnItem(member, itemId);
                JOptionPane.showMessageDialog(panel, "Return successful");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Return failed: " + ex.getMessage());
            }
        });

        reserveButton.addActionListener(e -> {
            String itemId = JOptionPane.showInputDialog(panel, "Please enter the item ID to reserve:");
            LibraryItem item = inventoryService.getItemById(itemId);
            if (item != null) {
                if (item.isAvailable()) {
                    JOptionPane.showMessageDialog(panel, "The item is available for borrowing. No need to reserve.");
                } else {
                    item.addReservation(member);
                    JOptionPane.showMessageDialog(panel, "Reservation successful. You will be notified when the item is returned.");
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Item not found.");
            }
        });

        viewHistoryButton.addActionListener(e -> {
            List<CheckoutTransaction> history = member.getBorrowingHistory();
            StringBuilder historyInfo = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (CheckoutTransaction transaction : history) {
                historyInfo.append("Item: ").append(transaction.getItem().getTitle())
                        .append(", Checkout Date: ").append(dateFormat.format(transaction.getCheckoutDate()));
                if (transaction.getReturnDate() != null) {
                    historyInfo.append(", Return Date: ").append(dateFormat.format(transaction.getReturnDate()));
                }
                historyInfo.append("\n");
            }
            JOptionPane.showMessageDialog(panel, historyInfo.toString());
        });

        logoutButton.addActionListener(e -> app.switchToPanel(createLoginPanel(app)));

        return panel;
    }

    public JPanel createLibrarianPanel(Librarian librarian, MainApplication app) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + librarian.getName() + " (Librarian)");
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton browseButton = new JButton("Browse Items");
        JButton addButton = new JButton("Add Item");
        JButton removeButton = new JButton("Remove Item");
        JButton memberManagementButton = new JButton("Member Management");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(browseButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(memberManagementButton);
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        JList<LibraryItem> itemList = new JList<>();
        JScrollPane itemScrollPane = new JScrollPane(itemList);
        panel.add(itemScrollPane, BorderLayout.CENTER);

        browseButton.addActionListener(e -> {
            DefaultListModel<LibraryItem> model = new DefaultListModel<>();
            List<LibraryItem> items = inventoryService.getAllItems();
            for (LibraryItem item : items) {
                model.addElement(item);
            }
            itemList.setModel(model);
        });

        addButton.addActionListener(e -> {
            String itemType = (String) JOptionPane.showInputDialog(panel, "Please select the item type:", "Add Item",
                    JOptionPane.QUESTION_MESSAGE, null, new String[]{"Book", "DVD", "Journal"}, "Book");
            String title = JOptionPane.showInputDialog(panel, "Please enter the item title:");
            String id = JOptionPane.showInputDialog(panel, "Please enter the item ID:");
            LibraryItem item = null;
            switch (itemType.toLowerCase()) {
                case "book":
                    String author = JOptionPane.showInputDialog(panel, "Please enter the book author:");
                    String publisher = JOptionPane.showInputDialog(panel, "Please enter the book publisher:");
                    item = new com.librarymanagement.inventory.Book(id, title, author, publisher);
                    break;
                case "dvd":
                    item = new com.librarymanagement.inventory.DVD(id, title);
                    break;
                case "journal":
                    item = new com.librarymanagement.inventory.Journal(id, title);
                    break;
                default:
                    JOptionPane.showMessageDialog(panel, "Invalid item type");
                    return;
            }
            inventoryService.addItem(item);
            JOptionPane.showMessageDialog(panel, "Item added successfully");
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1) {
                LibraryItem selectedItem = itemList.getSelectedValue();
                inventoryService.removeItem(selectedItem.getId());
                DefaultListModel<LibraryItem> model = (DefaultListModel<LibraryItem>) itemList.getModel();
                model.remove(selectedIndex);
                JOptionPane.showMessageDialog(panel, "Item removed successfully");
            } else {
                JOptionPane.showMessageDialog(panel, "Please select an item to remove");
            }
        });

        memberManagementButton.addActionListener(e -> {
            JList<Member> memberList = new JList<>();
            DefaultListModel<Member> memberModel = new DefaultListModel<>();
            List<Member> members = userService.getAllMembers();
            for (Member member : members) {
                memberModel.addElement(member);
            }
            memberList.setModel(memberModel);

            memberList.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        Member selectedMember = memberList.getSelectedValue();
                        String[] options = {"View Borrow Records", "Top-up/Deduct Balance", "Ban/Unban User"};
                        int choice = JOptionPane.showOptionDialog(panel, "Please select an operation", "Member Management",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                        switch (choice) {
                            case 0:
                                List<CheckoutTransaction> transactions = selectedMember.getBorrowingHistory();
                                StringBuilder transactionList = new StringBuilder();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                for (CheckoutTransaction transaction : transactions) {
                                    transactionList.append("Item: ").append(transaction.getItem().getTitle())
                                            .append(", Checkout Date: ").append(dateFormat.format(transaction.getCheckoutDate()));
                                    if (transaction.getReturnDate() != null) {
                                        transactionList.append(", Return Date: ").append(dateFormat.format(transaction.getReturnDate()));
                                    }
                                    transactionList.append("\n");
                                }
                                JOptionPane.showMessageDialog(panel, transactionList.toString());
                                break;
                            case 1:
                                String amountStr = JOptionPane.showInputDialog(panel, "Please enter the amount (positive for top-up, negative for deduction):");
                                try {
                                    double amount = Double.parseDouble(amountStr);
                                    selectedMember.setBalance(selectedMember.getBalance() + amount);
                                    JOptionPane.showMessageDialog(panel, "Operation successful. New balance: $" + selectedMember.getBalance());
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(panel, "Invalid amount input");
                                }
                                break;
                            case 2:
                                selectedMember.setBanned(!selectedMember.isBanned());
                                String status = selectedMember.isBanned() ? "banned" : "unbanned";
                                JOptionPane.showMessageDialog(panel, "User has been " + status);
                                break;
                        }
                    }
                }
            });

            JScrollPane memberScrollPane = new JScrollPane(memberList);
            JOptionPane.showMessageDialog(panel, memberScrollPane, "Member Management", JOptionPane.PLAIN_MESSAGE);
        });

        itemList.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    LibraryItem selectedItem = itemList.getSelectedValue();
                    StringBuilder status = new StringBuilder();
                    if (selectedItem.isAvailable()) {
                        status.append("Available for borrowing");
                    } else {
                        CheckoutTransaction transaction = transactionService.getTransactionByItem(selectedItem);
                        if (transaction != null) {
                            Member borrower = transaction.getMember();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            status.append("Borrowed by: ").append(borrower.getName()).append("\n");
                            status.append("Checkout Date: ").append(dateFormat.format(transaction.getCheckoutDate())).append("\n");
                            status.append("Due Date: ").append(dateFormat.format(transaction.getDueDate())).append("\n");
                            Date now = new Date();
                            long overdueDays = (now.getTime() - transaction.getDueDate().getTime()) / (24 * 60 * 60 * 1000);
                            if (overdueDays > 0) {
                                // 假設 TransactionService 提供公共方法獲取 FINE_PER_DAY
                                double fine = overdueDays * transactionService.getFinePerDay();
                                status.append("Overdue Days: ").append(overdueDays).append("\n");
                                status.append("Fine: $").append(fine).append("\n");
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(panel, status.toString());
                }
            }
        });

        logoutButton.addActionListener(e -> app.switchToPanel(createLoginPanel(app)));

        return panel;
    }
}