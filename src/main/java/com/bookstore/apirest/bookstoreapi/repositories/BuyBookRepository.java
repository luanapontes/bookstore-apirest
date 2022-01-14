package com.bookstore.apirest.bookstoreapi.repositories;

import com.bookstore.apirest.bookstoreapi.models.BuyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyBookRepository extends JpaRepository<BuyBook, Long> {
}
