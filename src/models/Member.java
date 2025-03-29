package models;

public class Member extends User {
    private final int checkoutLimit;

    public Member(String id, String name, String password, int checkoutLimit) {
        super(id, name, password);
        if (checkoutLimit <= 0) {
            throw new IllegalArgumentException("Checkout limit must be greater than zero.");
        }
        this.checkoutLimit = checkoutLimit;
    }

    public int getCheckoutLimit() {
        return checkoutLimit;
    }
}
