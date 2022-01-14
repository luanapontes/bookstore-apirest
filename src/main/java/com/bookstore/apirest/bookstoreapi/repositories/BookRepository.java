package com.bookstore.apirest.bookstoreapi.repositories;

import com.bookstore.apirest.bookstoreapi.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBookByCategoriesName(String categoryName);
}
