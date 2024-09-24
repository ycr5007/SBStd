package com.example.SBStd.repository;

import com.example.SBStd.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // Where
    Question findBySubject(String subject);// 단일 조건 부여
    Question findByContent(String content);
    Question findBySubjectAndContent(String subject, String content); // And 조건 부여
    Question findBySubjectOrContent(String subject, String content); // Or 조건 부여
    Question findByCreateDateBetween(LocalDateTime fromDate, LocalDateTime toDate); // Between 조건 부여
    Question findByIdLessThan(Integer id); // 비교연산( < )
    Question findByIdGreaterThanEqual(Integer id); // 비교연산 ( <= )
    Question findBySubjectLike(String subject); // Like
    List<Question> findBySubjectIn(List<String> subjects); // In

    // Order by
    List<Question> findByCreateDateLessThanOrderByCreateDateDesc(LocalDateTime createDate);
}
