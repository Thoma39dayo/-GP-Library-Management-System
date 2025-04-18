package com.librarymanagement.inventory;

public class DVD extends LibraryItem {
    public DVD(String id, String title) {
        super(id, title);
    }

    @Override
    public String toString() {
        return "DVD{id='" + getId() + "', title='" + getTitle() + "'}";
    }
}    