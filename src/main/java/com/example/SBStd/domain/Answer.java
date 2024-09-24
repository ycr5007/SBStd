package com.example.SBStd.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter

@Entity
@Table(name="STD_ANSWER")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // 'Basic' attribute type should not be 'Persistence Entity' (외래키를 매핑하는 과정에서 PK 지정이 안되어 발생하는 에러)
    @ManyToOne // many ( answer ) : 1 ( question )
    private Question question;
}
