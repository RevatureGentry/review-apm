package com.revature.review;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author William Gentry
 */
@Entity
public class Comment {

  @Id
  @GeneratedValue
  private int id;

  private String comment;

  private String commenter;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
  @CreationTimestamp
  private LocalDateTime createdOn;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
  @UpdateTimestamp
  private LocalDateTime lastModified;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
  private Comment parentComment;

  @OneToMany(mappedBy = "parentComment", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
  private Set<Comment> comments = new LinkedHashSet<>();

  public Comment() {}

  public Comment(String comment, String commenter) {
    this.comment = comment;
    this.commenter = commenter;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCommenter() {
    return commenter;
  }

  public void setCommenter(String commenter) {
    this.commenter = commenter;
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

  public Comment getParentComment() {
    return parentComment;
  }

  public void setParentComment(Comment parentComment) {
    this.parentComment = parentComment;
  }

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public Set<Comment> getComments() {
    return comments;
  }

  public void setComments(Set<Comment> comments) {
    this.comments = comments;
  }

  @JsonIgnore
  public void addComment(Comment comment) {
    this.comments.add(comment);
    comment.setParentComment(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comment comment1 = (Comment) o;
    return getId() == comment1.getId() &&
        Objects.equals(getComment(), comment1.getComment()) &&
        Objects.equals(getCommenter(), comment1.getCommenter()) &&
        Objects.equals(getCreatedOn(), comment1.getCreatedOn()) &&
        Objects.equals(getLastModified(), comment1.getLastModified());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getComment(), getCommenter(), getCreatedOn(), getLastModified());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Comment.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("comment='" + comment + "'")
        .add("commenter='" + commenter + "'")
        .add("createdOn=" + createdOn)
        .add("lastModified=" + lastModified)
        .toString();
  }
}
