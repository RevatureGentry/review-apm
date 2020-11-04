package com.revature.review;

import java.util.Objects;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author William Gentry
 */
public class ReviewDto {

  private int bookId;
  private double rating;
  private String content;

  public int getBookId() {
    return bookId;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @JsonIgnore
  public Review toModel(String reviewer) {
    return new Review(getBookId(), getRating(), getContent(), reviewer);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReviewDto reviewDto = (ReviewDto) o;
    return getBookId() == reviewDto.getBookId() &&
        Double.compare(reviewDto.getRating(), getRating()) == 0 &&
        Objects.equals(getContent(), reviewDto.getContent());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getBookId(), getRating(), getContent());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ReviewDto.class.getSimpleName() + "[", "]")
        .add("bookId=" + bookId)
        .add("rating=" + rating)
        .add("content='" + content + "'")
        .toString();
  }
}
