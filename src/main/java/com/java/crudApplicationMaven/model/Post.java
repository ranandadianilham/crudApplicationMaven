package com.java.crudApplicationMaven.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "Posts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot empty")
    private String title;
    @NotBlank(message = "Body cannot empty")
    private String body;
    @NotBlank(message = "Author cannot empty")
    private String author;

}
