package models;

import java.util.Date;

public class CheckoutTransaction {
    private String transactionId;
    private String userId;
    private String itemId;
    private Date checkoutDate;

    public CheckoutTransaction(String transactionId, String userId, String itemId, Date checkoutDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.itemId = itemId;
        this.checkoutDate = checkoutDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }
}
