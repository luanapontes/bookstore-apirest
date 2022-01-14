package com.bookstore.apirest.bookstoreapi.dtos;

import com.bookstore.apirest.bookstoreapi.models.Book;
import com.bookstore.apirest.bookstoreapi.models.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO implements Serializable {

    private static final long SerialVersionUID = 1L;

    private Long id;
    private String name;
    private String resume;
    private Integer isbn;
    private String author;
    private Integer yearOfPublication;
    private Double sellPrice;
    private Integer quantityAvailable;

    @JsonIgnore
    private List<Category> categories;

    public static BookDTO from(Book dto) {
        return BookDTO
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

    public static List<BookDTO> fromAll(List<Book> books) {
        return books.stream().map(BookDTO::from).collect(Collectors.toList());
    }

    public static Page<BookDTO> fromPage(Page<Book> pages) {
        return pages.map(BookDTO::from);
    }

}
