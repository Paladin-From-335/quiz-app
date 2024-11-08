package com.quiz.quizapp.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Table(name = "quizzes")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "quiz_name", nullable = false)
  private String quizName;

  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinTable(
      name = "quiz_questions_join",
      joinColumns = @JoinColumn(name = "quiz_id"),
      inverseJoinColumns = @JoinColumn(name = "question_id"))
  private List<Question> questions = new ArrayList<>();

  //Todo create User entity to join table
  private Long authorId;

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "public_id", length = 32)
  private String publicId;

  @Column
  private String description;

  @Column(name = "life_count")
  private Integer lifeCounter = 3;

  @Column(name = "skip_life_count")
  private Boolean skipLifeCounter = false;

  @Column(name = "time_limit")
  private Integer timeLimit;

  @OneToOne
  @JoinTable(
      name = "quiz_completion_data_join",
      joinColumns = @JoinColumn(name = "quiz_id"),
      inverseJoinColumns = @JoinColumn(name = "completion_data_id"))
  private CompletionData completionData;

  public void addQuestion(Question question) {
    questions.add(question);
  }
}
