package com.bookstore.apirest.bookstoreapi.repositories;

import com.bookstore.apirest.bookstoreapi.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
