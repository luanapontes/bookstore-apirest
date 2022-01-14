package com.bookstore.apirest.bookstoreapi.test.builder;

import com.bookstore.apirest.bookstoreapi.models.Client;

public class ClientBuilder {

    public static Client.ClientBuilder createClient(){
        return Client.builder()
                .id(2L)
                .name("Test")
                .age(22)
                .contact("(xx) xxxxx-xxxx")
                .email("ex@email.com")
                .gender('F');
    }
}
