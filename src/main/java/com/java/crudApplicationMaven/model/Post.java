package com.java.crudApplicationMaven.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "Posts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Title cannot empty")
    private String title;
    @NotNull(message = "Body cannot empty")
    private String body;
    @NotNull(message = "Author cannot empty")
    private String author;

}
