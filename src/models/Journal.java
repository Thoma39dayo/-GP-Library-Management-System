package models;

public class Journal extends LibraryItem {
    private final String publisher;

    public Journal(String id, String title, String publisher) {
        super(id, title);
        if (publisher == null || publisher.trim().isEmpty()) {
            throw new IllegalArgumentException("Publisher cannot be null or empty.");
        }
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }
}
