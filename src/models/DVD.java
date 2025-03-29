package models;

public class DVD extends LibraryItem {
    private final int runtime; // in minutes

    public DVD(String id, String title, int runtime) {
        super(id, title);
        if (runtime <= 0) {
            throw new IllegalArgumentException("Runtime must be greater than zero.");
        }
        this.runtime = runtime;
    }

    public int getRuntime() {
        return runtime;
    }
}
