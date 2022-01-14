package com.bookstore.apirest.bookstoreapi.models;

import com.bookstore.apirest.bookstoreapi.dtos.ClientDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clients")
public class Client implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String contact;
    private String email;
    private char gender;

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
        Client other = (Client) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public static Client to(ClientDTO dto) {
        return Client
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .contact(dto.getContact())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .build();
    }

}
