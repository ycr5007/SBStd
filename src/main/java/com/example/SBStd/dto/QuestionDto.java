package com.example.SBStd.dto;

import com.example.SBStd.domain.Question;
import lombok.*;

import java.time.LocalDateTime;

public class QuestionDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Integer id;
        private String subject;
        private String content;
        private LocalDateTime createDate;

        public Question toEntity() {
            Question questions = Question.builder()
                    .id(id)
                    .subject(subject)
                    .content(content)
                    .createDate(createDate)
                    .build();
            return questions;
        }

        @RequiredArgsConstructor
        @Getter
        public static class Response {
            private Integer id;
            private String subject;
            private String content;
            private LocalDateTime createDate;

            public Response (Question question) {
                this.id = question.getId();
                this.subject = question.getSubject();
                this.content = question.getContent();
                this.createDate = question.getCreateDate();
            }
        }
    }
}
