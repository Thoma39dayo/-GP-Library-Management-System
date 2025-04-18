package com.librarymanagement.transaction;

import com.librarymanagement.inventory.LibraryItem;
import com.librarymanagement.user.Member;

import java.util.Date;

public class CheckoutTransaction {
    private final Member member;
    private final LibraryItem item;
    private final Date checkoutDate;
    private Date returnDate;
    private static final int BORROW_PERIOD = 14;

    public CheckoutTransaction(Member member, LibraryItem item, Date checkoutDate) {
        this.member = member;
        this.item = item;
        this.checkoutDate = checkoutDate;
        this.returnDate = null;
    }

    public Member getMember() {
        return member;
    }

    public LibraryItem getItem() {
        return item;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getDueDate() {
        long dueTime = checkoutDate.getTime() + BORROW_PERIOD * 24 * 60 * 60 * 1000;
        return new Date(dueTime);
    }
}    