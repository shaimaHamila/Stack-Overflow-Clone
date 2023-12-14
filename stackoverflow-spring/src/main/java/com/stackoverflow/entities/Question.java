package com.stackoverflow.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stackoverflow.dtos.QuestionDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Lob
    @Column(name = "body", length = 512)
    private String body;

    private Date createdDate;

    @ElementCollection(targetClass = String.class)
    private List<String> tags;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public QuestionDTO getQuestionDto(){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);
        questionDTO.setTitle(title);
        questionDTO.setBody(body);
        questionDTO.setCreatedDate(createdDate);
        questionDTO.setUserId(user.getId());
        questionDTO.setTags(tags);
        questionDTO.setUsername(user.getName());
        return questionDTO;
    }
}
