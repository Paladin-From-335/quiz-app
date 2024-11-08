package com.quiz.quizapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "completion_data")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CompletionData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String header = "Congratulations!";

  @Column
  private String body;

  @Column(name = "time_spent")
  private Integer timeSpent;
}
