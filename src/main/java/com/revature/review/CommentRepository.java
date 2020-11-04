package com.revature.review;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author William Gentry
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
