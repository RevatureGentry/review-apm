package com.revature.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author William Gentry
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {

  List<Review> findAllByBookId(int bookId);

  List<Review> findAllByReviewer(String reviewer);
}
