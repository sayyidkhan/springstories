package com.main.springstories.service;

import com.main.springstories.models.AuthorEntity;
import com.main.springstories.dto.BookDTO;
import com.main.springstories.models.BookEntity;
import com.main.springstories.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    void saveBook(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setIsbn(bookDTO.getIsbn().toUpperCase());
        migrateFromBookDAOToBookEntity(bookDTO, bookEntity);
        // save book data
        bookRepository.save(bookEntity);
    }

    void updateBooks(BookDTO bookDTO) {
        BookEntity existingBookEntity = bookRepository.findByIsbn(bookDTO.getIsbn());
        if (existingBookEntity == null)  throw new IllegalArgumentException("Book with ISBN " + bookDTO.getIsbn() + " not found");
        migrateFromBookDAOToBookEntity(bookDTO, existingBookEntity);
        // save book data
        bookRepository.save(existingBookEntity);
    }

    public BookEntity getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Transactional
    public void deleteBookByIsbn(String isbn) throws DataAccessException {
        bookRepository.deleteByIsbn(isbn);
    }

    private static void migrateFromBookDAOToBookEntity(BookDTO bookDTO, BookEntity existingBookEntity) {
        // Update existingBook with values from updatedBook
        existingBookEntity.setTitle(bookDTO.getTitle().toUpperCase());
        existingBookEntity.setGenre(bookDTO.getGenre().toUpperCase());
        existingBookEntity.setPrice(bookDTO.getPrice());
        existingBookEntity.setYear(bookDTO.getYear());

        // Convert authors' names to uppercase
        String[] authors = Arrays.stream(bookDTO.getAuthors())
                .map(AuthorEntity::getName)
                .map(String::toUpperCase)
                .toArray(String[]::new);
        String authorDelimitedCommas = String.join(",", authors);
        existingBookEntity.setAuthors(authorDelimitedCommas);
    }

}
