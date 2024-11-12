package com.quiz.quizapp.repository;

import com.quiz.quizapp.model.entity.Completion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionDataRepository extends JpaRepository<Completion, Long> {

  @Query(value = "" +
      "SELECT c FROM completion_data c " +
      "JOIN quiz_completion_data_join qc ON c.id = qc.completion_data_id " +
      "JOIN quizzes q ON q.id = qc.quiz_id " +
      "WHERE q.public_id = :public_id", nativeQuery = true)
  Optional<Completion> findCompletionByQuizPublicId(@Param("public_id") String publicId);
}
