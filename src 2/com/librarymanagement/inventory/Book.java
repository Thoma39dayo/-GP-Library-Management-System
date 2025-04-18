package com.librarymanagement.inventory;

import java.util.Objects;

public class Book extends LibraryItem {
    private final String author;
    private final String publisher;

    public Book(String id, String title, String author, String publisher) {
        super(id, title);
        this.author = author;
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return "Book{id='" + getId() + "', title='" + getTitle() + "', author='" + author + "', publisher='" + publisher + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.author) && Objects.equals(publisher, book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, publisher);
    }
}    