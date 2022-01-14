package com.bookstore.apirest.bookstoreapi.models;

import com.bookstore.apirest.bookstoreapi.dtos.BookDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String resume;
    private Integer isbn;
    private String author;
    private Integer yearOfPublication;
    private Double sellPrice;
    private Integer quantityAvailable;

    @JsonIgnore
    @OneToMany
    @JoinTable(name = "book_category",
    joinColumns = {@JoinColumn(name = "book_id")},
    inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories;

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
        Book other = (Book) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public static Book to(BookDTO dto) {
        return Book
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .resume(dto.getResume())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .yearOfPublication(dto.getYearOfPublication())
                .sellPrice(dto.getSellPrice())
                .quantityAvailable(dto.getQuantityAvailable())
                .categories(dto.getCategories())
                .build();
    }

}
