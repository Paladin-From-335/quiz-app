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
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_questions")
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "question_name", nullable = false)
  private String questionName;

  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinTable(
      name = "question_options_join",
      joinColumns = @JoinColumn(name = "question_id"),
      inverseJoinColumns = @JoinColumn(name = "option_id"))
  private List<Option> options = new ArrayList<>();

  @Column(name = "media_url")
  private String mediaUrl;

  @Column(name = "time_limit")
  private Integer timeLimit;

  @Column
  private Integer sequence;

  @Column
  private String hint;

  @Column(name = "answer_type")
  private AnswerType answerType;

  public void addOption(Option option) {
    options.add(option);
  }

  public enum AnswerType {
    single,
    multiple
  }
}
