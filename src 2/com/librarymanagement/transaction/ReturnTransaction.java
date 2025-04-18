package com.librarymanagement.transaction;

import com.librarymanagement.inventory.LibraryItem;
import com.librarymanagement.user.Member;

import java.util.Date;

public class ReturnTransaction {
    private final Member member;
    private final LibraryItem item;
    private final Date returnDate;
    private String returnNote;
    private static final double FINE_PER_DAY = 1.0;

    public ReturnTransaction(Member member, LibraryItem item, Date returnDate) {
        this.member = member;
        this.item = item;
        this.returnDate = returnDate;
        this.returnNote = "";
    }

    public Member getMember() {
        return member;
    }

    public LibraryItem getItem() {
        return item;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getReturnNote() {
        return returnNote;
    }

    public void setReturnNote(String returnNote) {
        this.returnNote = returnNote;
    }

    public long calculateOverdueDays(CheckoutTransaction checkoutTransaction) {
        Date dueDate = checkoutTransaction.getDueDate();
        if (returnDate.after(dueDate)) {
            long diffInMillis = returnDate.getTime() - dueDate.getTime();
            return diffInMillis / (24 * 60 * 60 * 1000);
        }
        return 0;
    }

    public double calculateFine(CheckoutTransaction checkoutTransaction) {
        long overdueDays = calculateOverdueDays(checkoutTransaction);
        return overdueDays * FINE_PER_DAY;
    }
}    