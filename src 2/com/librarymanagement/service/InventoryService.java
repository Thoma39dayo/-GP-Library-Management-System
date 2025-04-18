package com.librarymanagement.service;

import com.librarymanagement.inventory.Book;
import com.librarymanagement.inventory.DVD;
import com.librarymanagement.inventory.Journal;
import com.librarymanagement.inventory.LibraryItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {
    private final List<LibraryItem> items = new ArrayList<>();
    private final Map<String, LibraryItem> itemMap = new HashMap<>();

    public InventoryService() {
        generateMultipleItems();
    }

    public void generateMultipleItems() {
        // 生成多本書籍
        for (int i = 1; i <= 5; i++) {
            Book book = new Book("book_id_" + i, "Book Title " + i, "Author " + i, "Publisher " + i);
            addItem(book);
        }
        // 生成多個DVD
        for (int i = 1; i <= 5; i++) {
            DVD dvd = new DVD("dvd_id_" + i, "DVD Title " + i);
            addItem(dvd);
        }
        // 生成多本期刊
        for (int i = 1; i <= 5; i++) {
            Journal journal = new Journal("journal_id_" + i, "Journal Title " + i);
            addItem(journal);
        }
    }

    public void addItem(LibraryItem item) {
        items.add(item);
        itemMap.put(item.getId(), item);
    }

    public void removeItem(String itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
        itemMap.remove(itemId);
    }

    public List<LibraryItem> getAllItems() {
        return items;
    }

    public LibraryItem getItemById(String itemId) {
        return itemMap.get(itemId);
    }
}    