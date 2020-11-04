package com.revature;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.review.Review;
import com.revature.review.ReviewRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
class ReviewServiceApplicationTests {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void contextLoads() {
	}

	@Transactional
	@Test
	void shouldLoad() throws JsonProcessingException {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		List<Review> reviews = reviewRepository.findAll();
		System.out.println(mapper.writeValueAsString(reviews));
	}
}
