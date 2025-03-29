package services;
import models.LibraryItem;
import exceptions.ItemNotAvailableException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private final Map<String, LibraryItem> items = new HashMap<>();

    public void addItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        items.put(item.getId(), item);
    }

    public LibraryItem findItem(String id) throws ItemNotAvailableException {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        LibraryItem item = items.get(id);
        if (item == null) {
            throw new ItemNotAvailableException("Item with ID " + id + " not found.");
        }
        return item;
    }

    public void removeItem(String id) throws ItemNotAvailableException {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        if (!items.containsKey(id)) {
            throw new ItemNotAvailableException("Item with ID " + id + " not found.");
        }
        items.remove(id);
    }

    public Collection<LibraryItem> getAllItems() {
        return items.values();
    }
}
