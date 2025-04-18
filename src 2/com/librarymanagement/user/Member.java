package com.librarymanagement.user;

import com.librarymanagement.transaction.CheckoutTransaction;

import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    private double balance;
    private boolean isBanned;
    private final List<CheckoutTransaction> borrowingHistory;

    public Member(String id, String name, String password) {
        super(id, name, password);
        this.balance = 0;
        this.isBanned = false;
        this.borrowingHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public List<CheckoutTransaction> getBorrowingHistory() {
        return borrowingHistory;
    }

    public void addBorrowingRecord(CheckoutTransaction transaction) {
        borrowingHistory.add(transaction);
    }
}    