package com.bookstore.apirest.bookstoreapi.models;

import com.bookstore.apirest.bookstoreapi.dtos.BuyBookDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "buyBook")
public class BuyBook implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @Column(name = "buyBook_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amountToPay;
    private Integer buyDay;
    private String status;

    @JsonIgnore
    @OneToMany
    @JoinTable(name = "buyBook_items",
            joinColumns = {@JoinColumn(name = "buyBook_id")},
            inverseJoinColumns = {@JoinColumn(name = "items_id")})
    private List<Items> items = new ArrayList<>();

    @JsonIgnore
    private Client client;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BuyBook other = (BuyBook) obj;
        if (id != other.id)
            return false;
        return true;
    }


    public static BuyBook to(BuyBookDTO dto) {
        return BuyBook
                .builder()
                .id(dto.getId())
                .amountToPay(dto.getAmountToPay())
                .status(dto.getStatus())
                .buyDay(dto.getBuyDay())
                .build();

    }
}
