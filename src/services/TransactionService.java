package services;
import models.CheckoutTransaction;
import models.LibraryItem;
import models.ReturnTransaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionService {
    private final List<CheckoutTransaction> checkouts = new ArrayList<>();
    private final List<ReturnTransaction> returns = new ArrayList<>();

    public void checkoutItem(String userId, LibraryItem item) {
        if (userId == null || item == null) {
            throw new IllegalArgumentException("User ID and Item cannot be null.");
        }
        if (!item.isAvailable()) {
            throw new RuntimeException("Item is not available for checkout.");
        }
        item.setAvailable(false);
        checkouts.add(new CheckoutTransaction("TXN_" + (checkouts.size() + 1), userId, item.getId(), new Date()));
    }

    public void returnItem(String userId, LibraryItem item) {
        if (userId == null || item == null) {
            throw new IllegalArgumentException("User ID and Item cannot be null.");
        }
        if (item.isAvailable()) {
            throw new RuntimeException("Item is already available, cannot return.");
        }
        item.setAvailable(true);
        returns.add(new ReturnTransaction("TXN_" + (returns.size() + 1), userId, item.getId(), new Date()));
    }
}
