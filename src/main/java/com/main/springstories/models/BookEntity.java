package com.main.springstories.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "BOOK")
public class BookEntity {

    @Id
    @Column(name="ISBN", nullable = false)
    private String isbn;
    @Column(name="TITLE",nullable = false,columnDefinition ="TEXT")
    private String title;

    @Column(name="AUTHORS", nullable = false, columnDefinition ="TEXT")
    private String authors;

    @Column(name="YEAR", nullable = false)
    private int year;

    @Column(name="PRICE", nullable = false)
    private double price;

    @Column(name="GENRE", nullable = false)
    private String genre;

    public BookEntity() {}

    // Constructors
    public BookEntity(String isbn, String title, String authors, int year, double price, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.price = price;
        this.genre = genre;
    }

    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthorsList() {
        return Arrays.asList(this.authors.split(","));
    }

    public String getAuthorsInPlainString() { return authors; }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1000 || year > 9999) {
            throw new IllegalArgumentException("Year must be between 1000 and 9999");
        }
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // toString method for easier debugging and logging
    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", year=" + year +
                ", price=" + price +
                ", genre='" + genre + '\'' +
                '}';
    }
}

