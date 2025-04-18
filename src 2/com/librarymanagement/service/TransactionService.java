package com.librarymanagement.service;

import com.librarymanagement.inventory.LibraryItem;
import com.librarymanagement.transaction.CheckoutTransaction;
import com.librarymanagement.transaction.ReturnTransaction;
import com.librarymanagement.user.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class.getName());
    private final List<CheckoutTransaction> checkoutTransactions = new ArrayList<>();
    private final List<ReturnTransaction> returnTransactions = new ArrayList<>();
    private final InventoryService inventoryService;
    private static final double FINE_PER_DAY = 1.0;

    public double getFinePerDay() {
        return FINE_PER_DAY;
    }

    public TransactionService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void checkoutItem(Member member, String itemId) throws ItemNotAvailableException {
        try {
            if (member.isBanned()) {
                throw new ItemNotAvailableException("You are banned and cannot borrow items.");
            }
            LibraryItem item = inventoryService.getItemById(itemId);
            if (item == null || !item.isAvailable()) {
                if (item != null) {
                    item.addReservation(member);
                    JOptionPane.showMessageDialog(null, "The item is not available. You have been added to the reservation list.");
                } else {
                    throw new ItemNotAvailableException("Item not found.");
                }
            } else {
                item.setAvailable(false);
                Date checkoutDate = new Date();
                CheckoutTransaction transaction = new CheckoutTransaction(member, item, checkoutDate);
                checkoutTransactions.add(transaction);
                member.addBorrowingRecord(transaction);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during checkout", e);
            throw new ItemNotAvailableException("Error during checkout: " + e.getMessage());
        }
    }

    public void returnItem(Member member, String itemId) {
        try {
            LibraryItem item = inventoryService.getItemById(itemId);
            if (item != null) {
                item.setAvailable(true);
                Date returnDate = new Date();
                ReturnTransaction transaction = new ReturnTransaction(member, item, returnDate);
                returnTransactions.add(transaction);
                checkoutTransactions.removeIf(t -> t.getItem().getId().equals(itemId));

                CheckoutTransaction checkoutTransaction = getTransactionByItem(item);
                if (checkoutTransaction != null) {
                    checkoutTransaction.setReturnDate(returnDate);
                    Date dueDate = checkoutTransaction.getDueDate();
                    long overdueDays = (returnDate.getTime() - dueDate.getTime()) / (24 * 60 * 60 * 1000);
                    if (overdueDays > 0) {
                        double fine = overdueDays * FINE_PER_DAY;
                        member.setBalance(member.getBalance() - fine);
                        JOptionPane.showMessageDialog(null, "You have an overdue fine of $" + fine + ". Your new balance is $" + member.getBalance());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during return", e);
            JOptionPane.showMessageDialog(null, "Error during return: " + e.getMessage());
        }
    }

    public List<CheckoutTransaction> getCheckoutTransactions(Member member) {
        List<CheckoutTransaction> memberTransactions = new ArrayList<>();
        for (CheckoutTransaction transaction : checkoutTransactions) {
            if (transaction.getMember().equals(member)) {
                memberTransactions.add(transaction);
            }
        }
        return memberTransactions;
    }

    public CheckoutTransaction getTransactionByItem(LibraryItem item) {
        for (CheckoutTransaction transaction : checkoutTransactions) {
            if (transaction.getItem().equals(item)) {
                return transaction;
            }
        }
        return null;
    }
}

class ItemNotAvailableException extends Exception {
    public ItemNotAvailableException(String message) {
        super(message);
    }
}