package com.bookstore.apirest.bookstoreapi.test.builder;

import com.bookstore.apirest.bookstoreapi.models.Book;
import com.bookstore.apirest.bookstoreapi.models.Category;

import java.util.ArrayList;
import java.util.List;

public class BookBuilder {

    public static Book.BookBuilder createBook(){

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(2L, "Mangá"));
        return Book.builder()
                .id(2L)
                .isbn(20)
                .name("Test")
                .author("Luana")
                .yearOfPublication(2021)
                .quantityAvailable(2)
                .resume("Test")
                .categories(categories)
                .sellPrice(20.00);



    }
}
