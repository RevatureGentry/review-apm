package com.revature.review;

import java.util.List;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author William Gentry
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @GetMapping("/{bookId}")
  public ResponseEntity<List<Review>> getReviews(@AuthenticationPrincipal String user, @PathVariable("bookId") int bookId) {
    Transaction transaction = ElasticApm.currentTransaction();
    transaction.ensureParentId();
    logger.info("{} attempting to find reviews for book with id {}", user, bookId);
    List<Review> reviews = reviewService.findAllForBook(bookId);
    if (reviews.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/mine")
  public ResponseEntity<List<Review>> getMyReviews(@AuthenticationPrincipal String user) {
    Transaction transaction = ElasticApm.currentTransaction();
    transaction.ensureParentId();
    logger.info("Attempting to find all reviews for {}", user);
    List<Review> reviews = reviewService.findAllByReviewer(user);
    if (reviews.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(reviews);
  }

  @PostMapping
  public ResponseEntity<Review> saveReview(@AuthenticationPrincipal String user, @RequestBody ReviewDto review) {
    Transaction transaction = ElasticApm.currentTransaction();
    transaction.ensureParentId();
    logger.info("{} attempting to save {}", user, review);
    Review saved = reviewService.save(review.toModel(user));
    if (saved != null) {
      return ResponseEntity.ok(saved);
    }
    return ResponseEntity.badRequest().build();
  }

  @PatchMapping
  public ResponseEntity<Review> updateReview(@AuthenticationPrincipal String user, @RequestBody Review review) {
    Transaction transaction = ElasticApm.currentTransaction();
    transaction.ensureParentId();
    logger.info("{} attempting to update {}", user, review);
    if (review != null) {
      Review found = reviewService.findById(review.getId());
      found.setRating(review.getRating());
      found.setContent(review.getContent());
      found.setReviewer(user);
      return ResponseEntity.ok(reviewService.save(found));
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/comment")
  public ResponseEntity<Review> addComment(@AuthenticationPrincipal String user, @RequestBody CommentDto comment) {
    Transaction transaction = ElasticApm.currentTransaction();
    transaction.ensureParentId();
    logger.info("{} attempting to add a comment {}", user, comment);
    Review withNewComment = reviewService.addComment(comment, user);
    return ResponseEntity.ok(withNewComment);
  }
}
