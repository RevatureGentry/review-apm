package com.revature.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author William Gentry
 */
@Entity
public class Review {

  @Id
  @GeneratedValue
  private int id;

  private int bookId;

  private double rating;

  private String content;

  private String reviewer;

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
  private List<Comment> comments = new ArrayList<>();

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
  @CreationTimestamp
  private LocalDateTime createdOn;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
  @UpdateTimestamp
  private LocalDateTime lastModified;

  public Review() {}

  public Review(int bookId, double rating, String content, String reviewer) {
    this.bookId = bookId;
    this.rating = rating;
    this.content = content;
    this.reviewer = reviewer;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

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

  public String getReviewer() {
    return reviewer;
  }

  public void setReviewer(String reviewer) {
    this.reviewer = reviewer;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  @JsonIgnore
  public void addComment(Comment comment) {
    this.comments.add(comment);
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  public LocalDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(LocalDateTime lastModified) {
    this.lastModified = lastModified;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Review review = (Review) o;
    return getId() == review.getId() &&
        getBookId() == review.getBookId() &&
        Double.compare(review.getRating(), getRating()) == 0 &&
        Objects.equals(getContent(), review.getContent()) &&
        Objects.equals(getReviewer(), review.getReviewer()) &&
        Objects.equals(getCreatedOn(), review.getCreatedOn()) &&
        Objects.equals(getLastModified(), review.getLastModified());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getBookId(), getRating(), getContent(), getReviewer(), getCreatedOn(), getLastModified());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Review.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("bookId=" + bookId)
        .add("rating=" + rating)
        .add("content='" + content + "'")
        .add("reviewer='" + reviewer + "'")
        .add("createdOn=" + createdOn)
        .add("lastModified=" + lastModified)
        .toString();
  }
}
