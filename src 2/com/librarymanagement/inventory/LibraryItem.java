package com.librarymanagement.inventory;

import com.librarymanagement.user.Member;

import java.util.ArrayList;
import java.util.List;

public abstract class LibraryItem {
    private final String id;
    private final String title;
    private boolean isAvailable;
    private final List<Member> reservations;

    public LibraryItem(String id, String title) {
        this.id = id;
        this.title = title;
        this.isAvailable = true;
        this.reservations = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public List<Member> getReservations() {
        return reservations;
    }

    public void addReservation(Member member) {
        reservations.add(member);
    }
}    