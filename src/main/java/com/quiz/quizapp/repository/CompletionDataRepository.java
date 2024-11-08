package com.quiz.quizapp.repository;

import com.quiz.quizapp.model.entity.Completion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionDataRepository extends JpaRepository<Completion, Long> {
}
