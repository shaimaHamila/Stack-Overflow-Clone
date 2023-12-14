package com.stackoverflow.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_question_id")
//    List<Question> questions;
}
