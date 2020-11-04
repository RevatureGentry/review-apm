package com.revature.review;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author William Gentry
 */
@Service
public class ReviewService {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final ReviewRepository reviewRepository;
  private final CommentRepository commentRepository;

  public ReviewService(ReviewRepository reviewRepository, CommentRepository commentRepository) {
    this.reviewRepository = reviewRepository;
    this.commentRepository = commentRepository;
  }

  @Transactional(readOnly = true)
  public List<Review> findAllForBook(int bookId) {
    List<Review> reviews = reviewRepository.findAllByBookId(bookId);
    if (reviews == null || reviews.isEmpty()) {
      return Collections.emptyList();
    }
    logger.info("Found {} reviews for book with id {}", reviews.size(), bookId);
    return reviews;
  }

  @Transactional(readOnly = true)
  public List<Review> findAll() {
    List<Review> reviews = reviewRepository.findAll();
    if (reviews.isEmpty()) {
      return Collections.emptyList();
    }
    logger.info("Found {} reviews", reviews.size());
    return reviews;
  }

  @Transactional(readOnly = true)
  public List<Review> findAllByReviewer(String reviewer) {
    List<Review> reviews = reviewRepository.findAllByReviewer(reviewer);
    if (reviews == null || reviews.isEmpty()) {
      return Collections.emptyList();
    }
    logger.info("Found {} reviews for reviewer {}", reviews.size(), reviewer);
    return reviews;
  }

  @Transactional
  public Review save(Review review) {
    logger.info("Attempting to save {}", review);
    return reviewRepository.save(review);
  }

  @Transactional(readOnly = true)
  public Review findById(int id) {
    return reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
  }

  @Transactional
  public Review addComment(CommentDto comment, String user) {
    Review found = reviewRepository.findById(comment.getReviewId()).orElseThrow(ReviewNotFoundException::new);
    Comment newComment = comment.toModel(user);
    logger.info("Comment to be saved! {}", newComment);
    if (comment.getParentCommentId() == null) {
      logger.info("Review had {} comments", found.getComments().size());
      found.getComments().add(newComment);
      commentRepository.saveAll(found.getComments());
      return found;
    }
    Comment parent = commentRepository.findById(comment.getParentCommentId()).orElseThrow(CommentNotFoundException::new);
    parent.getComments().add(newComment);
    newComment.setParentComment(parent);
    newComment = commentRepository.saveAndFlush(newComment);
    logger.info("Saved: {}", newComment);
    return found;
//    if (comment.getParentCommentId() != null) {
//      Comment found = commentRepository.findById(comment.getParentCommentId()).orElseThrow(CommentNotFoundException::new);
//      logger.info("Found {}", found);
//      Comment subComment = new Comment();
//      subComment.setParentComment(found);
//      subComment.setCommenter(user);
//      subComment.setComment(comment.getContent());
//      found.addComment(subComment);
//      commentRepository.save(found);
//      return reviewRepository.findById(comment.getReviewId()).orElseThrow(ReviewNotFoundException::new);
//    }
//    Review found = reviewRepository.findById(comment.getReviewId()).orElseThrow(ReviewNotFoundException::new);
//    Comment newComment = new Comment();
//    newComment.setComment(comment.getContent());
//    newComment.setCommenter(user);
//    found.addComment(newComment);
//    return reviewRepository.save(found);
  }
}
