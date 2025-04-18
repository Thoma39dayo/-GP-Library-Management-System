package com.librarymanagement.inventory;

public class Journal extends LibraryItem {
    public Journal(String id, String title) {
        super(id, title);
    }

    @Override
    public String toString() {
        return "Journal{id='" + getId() + "', title='" + getTitle() + "'}";
    }
}    