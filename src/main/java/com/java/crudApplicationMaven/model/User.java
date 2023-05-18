package com.java.crudApplicationMaven.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Username cannot empty")
    private String username;
    @NotNull(message = "Email cannot empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Password cannot empty")
    private String password;
}
