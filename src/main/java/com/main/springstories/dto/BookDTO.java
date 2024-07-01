package com.main.springstories.dto;

import com.main.springstories.models.AuthorEntity;
import com.main.springstories.models.BookEntity;

public class BookDTO {

    private String isbn;
    private String title;
    private AuthorEntity[] authors;
    private int year;
    private double price;
    private String genre;

    public BookDTO() {}

    public BookDTO(String isbn, String title, AuthorEntity[] authors, int year, double price, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.price = price;
        this.genre = genre;
    }

    public BookDTO(BookEntity bookEntity, AuthorEntity[] authorEntities) {
        this.isbn = bookEntity.getIsbn();
        this.title = bookEntity.getTitle();
        this.year = bookEntity.getYear();
        this.authors = authorEntities;
        this.price = bookEntity.getPrice();
        this.genre = bookEntity.getGenre();
    }

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

    public AuthorEntity[] getAuthors() {
        return authors;
    }

    public void setAuthors(AuthorEntity[] authors) {
        this.authors = authors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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
}
