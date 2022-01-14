package com.bookstore.apirest.bookstoreapi.test.builder;

import com.bookstore.apirest.bookstoreapi.models.Category;

public class CategoryBuilder {

    public static Category.CategoryBuilder createCategory(){
        return Category.builder()
                .id(2L)
                .name("Mangá");
    }
}
