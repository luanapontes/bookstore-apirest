package com.bookstore.apirest.bookstoreapi.test.builder;

import com.bookstore.apirest.bookstoreapi.models.*;

import java.util.ArrayList;
import java.util.List;

public class BuyBookBuilder {

    public static BuyBook.BuyBookBuilder createBuyBook(){

        List<Items> items = new ArrayList<>();
        items.add(new Items(2L, 5));

        return BuyBook.builder()
                .id(2L)
                .amountToPay(20.0)
                .status("available")
                .buyDay(15)
                .items(items);
    }
}
