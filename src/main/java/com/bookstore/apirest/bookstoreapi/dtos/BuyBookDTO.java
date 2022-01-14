package com.bookstore.apirest.bookstoreapi.dtos;

import com.bookstore.apirest.bookstoreapi.models.BuyBook;
import com.bookstore.apirest.bookstoreapi.models.Items;
import lombok.*;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class BuyBookDTO implements Serializable {

    private static final long SerialVersionUID = 1L;

    private Long id;
    private Double amountToPay;
    private Integer buyDay;
    private String status;

    public BuyBookDTO(Long id, Double amountToPay, Integer buyDay, String status) {
        this.id = id;
        this.amountToPay = amountToPay;
        this.buyDay = buyDay;
        this.status = status;
    }

    public BuyBookDTO (BuyBook obj) {
        this.id = obj.getId();
        this.amountToPay = obj.getAmountToPay();
        this.buyDay = obj.getBuyDay();
        this.status = obj.getStatus();
    }

    public static BuyBookDTO from(BuyBook dto) {
        return BuyBookDTO
                .builder()
                .id(dto.getId())
                .amountToPay(dto.getAmountToPay())
                .buyDay(dto.getBuyDay())
                .status(dto.getStatus())
                .build();
    }

    public static List<BuyBookDTO> fromAll(List<BuyBook> buyBooks) {
        return buyBooks.stream().map(BuyBookDTO::from).collect(Collectors.toList());
    }

    public static Page<BuyBookDTO> fromPage(Page<BuyBook> pages) {
        return pages.map(BuyBookDTO::from);
    }
}
