package models;
import java.util.Date;

public abstract class Transaction {
    private final String id;
    private final String userId;
    private final String itemId;
    private final Date date;

    public Transaction(String id, String userId, String itemId, Date date) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or empty.");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        if (itemId == null || itemId.trim().isEmpty()) {
            throw new IllegalArgumentException("Item ID cannot be null or empty.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Transaction date cannot be null.");
        }

        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.date = new Date(date.getTime()); // Defend against mutable Date
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getItemId() { return itemId; }
    public Date getDate() { return new Date(date.getTime()); } // Return defensive copy
}
