package com.bookstore.apirest.bookstoreapi.models;

import com.bookstore.apirest.bookstoreapi.dtos.CategoryDTO;
import com.fasterxml.jackson.databind.node.LongNode;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

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
        Category other = (Category) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public static Category to(CategoryDTO dto) {
        return Category
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

}
