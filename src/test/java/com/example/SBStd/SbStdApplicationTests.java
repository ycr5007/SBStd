package com.example.SBStd;

import com.example.SBStd.domain.Question;
import com.example.SBStd.dto.QuestionDto;
import com.example.SBStd.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbStdApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void testJpa() {
		// Dto Request 객체 생성
		QuestionDto.Request dto = new QuestionDto.Request();

		dto.setSubject("Question 1");
		dto.setContent("Please Answer to me");
		dto.setCreateDate(LocalDateTime.now());

		Question q1 = dto.toEntity();
		this.questionRepository.save(q1); // Id (Auto Increment) 생성 시점
	}

	@Test
	void testFindAll() {
		List<Question> all = questionRepository.findAll();
		assertEquals(2, all.size()); // 예상값과 실제 값이 같은지 확인

		Question q = all.get(0);
		assertEquals("Quetion 1", q.getSubject());

		System.out.println(q.getContent());
	}

	@Test
	void testFindBySubject() {
		String findStr = "Quetion 1";

		Question q = questionRepository.findBySubject(findStr); // 일치하는 데이터가 없을 떄 null 반환
		assertEquals("Quetion 1", q.getSubject());

		System.out.println(q.getContent());
	}

	@Test
	void testFindByContent() {
		String findStr = "Test JPA INSERT TEST";

		Question q = questionRepository.findByContent(findStr); // 일치하는 데이터가 없을 떄 null 반환
		assertEquals("Test JPA INSERT TEST", q.getContent());

		System.out.println(q.getSubject());
	}

	@Test
	void testFindBySubjectAndContent() {
		String findStr1 = "Quetion 1";
		String findStr2 = "Please Answer to me";

		Question q = questionRepository.findBySubjectAndContent(findStr1, findStr2);
		assertEquals(1, q.getId());

		System.out.println("Question Id : %d".formatted(q.getId()));
	}

	@Test
	void contextLoads() {
	}

}
