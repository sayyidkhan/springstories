package com.main.springstories.repository;

import com.main.springstories.models.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    BookEntity findByIsbn(String isbn);

    void deleteByIsbn(String isbn);

}
