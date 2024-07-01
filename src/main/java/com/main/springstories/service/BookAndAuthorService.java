package com.main.springstories.service;

import com.main.springstories.models.AuthorEntity;
import com.main.springstories.dto.BookDTO;
import com.main.springstories.models.BookEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookAndAuthorService {
    private final BookService bookService;
    private final AuthorService authorservice;


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public BookAndAuthorService(
            BookService bookService,
            AuthorService authorservice) {
        this.bookService = bookService;
        this.authorservice = authorservice;
    }

    @Transactional
    public void addBook(BookDTO bookDTO) {
        // save book data
        bookService.saveBook(bookDTO);
        // save author data
        authorservice.saveAuthors(Arrays.asList(bookDTO.getAuthors()));
    }

    @Transactional
    public void updateBook(BookDTO bookDTO) throws DataAccessException {
        bookService.updateBooks(bookDTO);
        // save author data
        authorservice.saveAuthors(Arrays.asList(bookDTO.getAuthors()));
    }

    @Transactional
    public List<BookDTO> findBooksByTitleAndOrAuthor(String title, String author) {
        List<BookDTO> bookDTOList = new ArrayList<>();
        List<BookEntity> bookEntityList = findByTitleAndAuthorsInBookTable(title, author);;
        if (bookEntityList != null) bookDTOList.addAll(convertBookEntityToDAO(bookEntityList));
        return bookDTOList;
    }

    public List<BookEntity> findByTitleAndAuthorsInBookTable(String title, String authors) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM book WHERE ");

        if (title != null) {
            sb.append(String.format(" title = '%s' ", title.trim().toUpperCase()));
            // only want to add AND when title is used
            if (authors != null) sb.append(" AND ");
        }
        if (authors != null) {
            // handle scenario where with commas
            // eg. Jack Sparrow, Jackie Chan
            if (authors.contains(",")) {
                String[] authorList = authors.split(",");
                for (int i = 0; i < authorList.length; i++) {
                    String author = authorList[i];
                    sb.append(String.format("FIND_IN_SET('%s', authors) > 0", author.trim().toUpperCase()));
                    // this is to handle the filtering of many authors
                    if (i != authorList.length - 1) sb.append(" AND ");
                }
            }
            // handle scenario where no commas
            // eg. Jack Sparrow
            else {
                sb.append(String.format("FIND_IN_SET('%s', authors) > 0", authors.trim().toUpperCase()));
            }
        }

        System.out.println(sb);
        Query query = entityManager.createNativeQuery(sb.toString(), BookEntity.class);
        return query.getResultList();
    }

    public List<BookDTO> convertBookEntityToDAO(List<BookEntity> bookEntityList) {
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookEntity bookEntity : bookEntityList) {
            String authorsInPlainString = bookEntity.getAuthorsInPlainString();
            List<AuthorEntity> authorEntityList = authorservice.findByNameInAuthorsTables(authorsInPlainString);
            AuthorEntity[] authorEntityArray = authorEntityList.toArray(new AuthorEntity[0]);
            bookDTOList.add(new BookDTO(bookEntity, authorEntityArray));
        }

        return bookDTOList;
    }

}
