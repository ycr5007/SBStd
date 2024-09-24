package com.example.SBStd.dto;

import com.example.SBStd.domain.Article;
import lombok.*;

// Request, Response 클래스를 하나로 묶어 InnerStaticClass 로 관리
public class ArticleDto {

    // Article Service 요청을 위한 DTO Class
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private int id;
        private String title;
        private String body;

        // private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        /* Dto -> Entity */
        public Article toEntity() {
            Article articles = Article.builder()
                    .id(id)
                    .title(title)
                    .body(body)
                    // .createDate(createdDate)
                    .build();

            return articles;
        }
    }

    // Article 정보를 Return 할 응답(Response) Class
    @RequiredArgsConstructor
    @Getter
    public static class Response {
        private int id;
        private String title;
        private String body;
        // private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        /* Entity -> Dto */
        public Response(Article article) {
            this.id = article.getId();
            this.title = article.getTitle();
            this.body = article.getBody();
            // this.createdDate = article.getCreateDate();
        }
    }

}
