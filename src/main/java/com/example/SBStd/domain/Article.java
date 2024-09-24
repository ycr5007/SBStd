package com.example.SBStd.domain;

// JPA 연결 Entity

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
//@Entity
//@Table(name = "")
// 일반적으로 Setter는 보안상의 이유로 작성 X (학습목적 추가
@Setter
public class Article {
    private static int lastId = 0;

    private int id;
    private String title;
    private String body;

    public Article(String title, String body) {
        this(++lastId, title, body);
    }
}
