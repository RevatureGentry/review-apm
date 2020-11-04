package com.revature.data;

import javax.annotation.PostConstruct;

import com.revature.review.Comment;
import com.revature.review.Review;
import com.revature.review.ReviewService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author William Gentry
 */
@Component
public class DataLoader {

  private final ReviewService reviewService;

  public DataLoader(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostConstruct
  public void init() {
    if (reviewService.findAll().isEmpty()) {
      Review review = new Review();
      review.setBookId(1);
      review.setContent("I have annually read this book since it's release. Just fantastic");
      review.setRating(4.5);
      review.setReviewer("test@mock.com");
      Comment comment = new Comment();
      comment.setComment("What a fantastic book!");
      comment.setCommenter("commenter@mock.com");
      Comment subComment = new Comment();
      subComment.setComment("I disagree - too much fantasy fiction for me");
      subComment.setCommenter("subcommenter@mock.com");
      comment.addComment(subComment);
      review.addComment(comment);

      reviewService.save(review);
    }
  }
}
