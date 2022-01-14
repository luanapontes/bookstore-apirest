package com.bookstore.apirest.bookstoreapi.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "items")
public class Items implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @Column(name = "items_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_compraLivro")
    private BuyBook buyBook;

    @ManyToOne
    @JoinColumn (name = "id_livro")
    private Book book;

    private Integer quantidade;

    public Items(Long id, Integer quantidade ) {
        this.id = id;
        this.quantidade = quantidade;
    }

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
        Items other = (Items) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
