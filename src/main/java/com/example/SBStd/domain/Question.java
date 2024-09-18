package com.example.SBStd.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter

@Entity // Entity Class 임을 명시 ( 아래 Class 와 매칭되는 Table 이 없다면, 자동으로 생성 )
@Table(name="STD_QUESTION") // Default 로 Class 명을 Table 명으로 생성하지만, 명시해줄 수 있음.
public class Question {
    @Id // PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private Integer id;

    @Column(length = 200) // VARCHAR(200)
    private String subject;

    @Column(columnDefinition = "TEXT") // TEXT 타입 지정
    private String content;
    private LocalDateTime createDate;

    // Class 형태로만 존재하는 List ( DB 반영 X )
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // 1 ( question ) : many ( answer)
    private List<Answer> answerList;
}
