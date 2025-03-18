package com.quiz.quizapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "completion_pages")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CompletionPage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title = "Congratulations!";

  @Column
  private String body = "You completed the quiz!";

  @OneToOne
  @JoinTable(
      name = "quiz_completion_page_join",
      joinColumns = @JoinColumn(name = "completion_page_id"),
      inverseJoinColumns = @JoinColumn(name = "quiz_id"))
  private Quiz quiz;

}
