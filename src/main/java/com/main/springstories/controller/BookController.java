package com.main.springstories.controller;

import com.main.springstories.controller.exceptions.BadRequestException;
import com.main.springstories.controller.exceptions.BookValidationException;
import com.main.springstories.controller.exceptions.DuplicateBookException;
import com.main.springstories.dto.BookDTO;
import com.main.springstories.models.BookEntity;
import com.main.springstories.service.BookAndAuthorService;
import com.main.springstories.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookAndAuthorService bookAndAuthorService;
    private final BookService bookService;

    @Autowired
    public BookController(BookAndAuthorService bookAndAuthorService, BookService bookService) {
        this.bookAndAuthorService = bookAndAuthorService;
        this.bookService = bookService;
    }

    @PostMapping("user/add")
    public ResponseEntity<String> addBook(@RequestBody BookDTO bookDTO) {
        try {
            // Validate Entity to ensure all the required data is present
            StringBuilder sb = new StringBuilder();
            if (bookDTO.getIsbn() == null || bookDTO.getIsbn().trim().length() == 0) sb.append("isbn, ");
            if (bookDTO.getTitle() == null || bookDTO.getTitle().trim().length() == 0) sb.append("title, ");
            if (bookDTO.getYear() < 1000 || bookDTO.getYear() > 9999) sb.append("year, ");
            if (bookDTO.getAuthors().length == 0) sb.append("author(s), ");
            if (bookDTO.getPrice() == 0) sb.append("price, ");
            if (bookDTO.getGenre() == null || bookDTO.getGenre().trim().length() == 0) sb.append("genre, ");
            if (sb.length() > 0) {
                throw new BookValidationException(sb.substring(0, sb.length() - 2) + " cannot be empty !");
            }

            // Check if the book already exists
            if (bookService.getBookByIsbn(bookDTO.getIsbn()) != null) {
                throw new DuplicateBookException("Book already exists in the system !");
            }

            // Save the book
            bookAndAuthorService.addBook(bookDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Book Created Successfully");
        } catch (BookValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (DuplicateBookException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (DataAccessException ex) {
            // when JPA failed to save object
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save book: " + ex.getMessage());
        }
    }

    @PutMapping("user/update")
    public ResponseEntity<String> updateBook(@RequestBody BookDTO bookDTO) {
        try {
            // Validate updated book entity
            StringBuilder sb = new StringBuilder();
            if (bookDTO.getIsbn() == null || bookDTO.getIsbn().trim().isEmpty()) sb.append("ISBN cannot be empty. \n");
            if (bookDTO.getTitle() == null || bookDTO.getTitle().trim().isEmpty()) sb.append("Title cannot be empty. ");
            if (bookDTO.getYear() < 1000 || bookDTO.getYear() > 9999) sb.append("Year must be between 1000 and 9999. \n");
            if (bookDTO.getPrice() <= 0) sb.append("Price must be greater than zero. \n");
            if (bookDTO.getAuthors().length == 0) sb.append("Book must at least have 1 Author or more. \n");
            if (bookDTO.getGenre() == null || bookDTO.getGenre().trim().isEmpty()) sb.append("Genre cannot be empty. \n");
            if (sb.length() > 0) throw new BookValidationException(sb.toString().trim());

            // Check if the book exists in the database
            BookEntity existingBookEntity = bookService.getBookByIsbn(bookDTO.getIsbn());
            if (existingBookEntity == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to update !.\nBook with ISBN " + bookDTO.getIsbn() + " not found");

            // Update the book
            bookAndAuthorService.updateBook(bookDTO);

            String successMsg = String.format("Book with ISBN %s Updated Successfully", bookDTO.getIsbn());
            return ResponseEntity.status(HttpStatus.OK).body(successMsg);
        } catch (BookValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update book: " + ex.getMessage());
        }
    }

    @GetMapping("user/find")
    public ResponseEntity<List<BookDTO>> findBooksByTitleAndOrAuthor(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "author", required = false) String author) {

        // Perform the search based on title and/or author
        List<BookDTO> foundBookEntities = bookAndAuthorService.findBooksByTitleAndOrAuthor(title, author);
        if (foundBookEntities.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(foundBookEntities);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<String> deleteBookByIsbn(@RequestParam("isbn") String isbn) {
        try {
            // Attempt to delete the book by ISBN
            bookService.deleteBookByIsbn(isbn);
            return ResponseEntity.status(HttpStatus.OK).body("Book with ISBN " + isbn + " deleted successfully");
        } catch (EmptyResultDataAccessException ex) {
            // If the book with the provided ISBN does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ISBN " + isbn + " not found");
        } catch (Exception ex) {
            // Other exceptions (database access, etc.)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete book: " + ex.getMessage());
        }
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
