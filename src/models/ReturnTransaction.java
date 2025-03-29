package models;

import java.util.Date;

public class ReturnTransaction {
    private String transactionId;
    private String userId;
    private String itemId;
    private Date returnDate;

    public ReturnTransaction(String transactionId, String userId, String itemId, Date returnDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.itemId = itemId;
        this.returnDate = returnDate;
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
