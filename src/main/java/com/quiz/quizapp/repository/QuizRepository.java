package com.quiz.quizapp.repository;

import com.quiz.quizapp.model.entity.Quiz;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

  Optional<Quiz> findByPublicId(String publicId);

  @Query(value = "SELECT q.id FROM quizzes q WHERE q.public_id =:publicId", nativeQuery = true)
  Optional<Long> getQuizIdByPublicId(String publicId);

  void deleteByPublicId(String publicId);
}
