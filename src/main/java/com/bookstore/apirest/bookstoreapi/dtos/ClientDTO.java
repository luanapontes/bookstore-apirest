package com.bookstore.apirest.bookstoreapi.dtos;

import com.bookstore.apirest.bookstoreapi.models.Client;
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
public class ClientDTO implements Serializable {

    private static final long SerialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer age;
    private String contact;
    private String email;
    private char gender;

    public static ClientDTO from(Client dto) {
        return ClientDTO
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .contact(dto.getContact())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .build();
    }

    public static List<ClientDTO> fromAll(List<Client> clients) {
        return clients.stream().map(ClientDTO::from).collect(Collectors.toList());
    }

    public static Page<ClientDTO> fromPage(Page<Client> pages) {
        return pages.map(ClientDTO::from);

    }
}
