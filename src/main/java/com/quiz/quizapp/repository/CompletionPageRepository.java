package com.quiz.quizapp.repository;

import com.quiz.quizapp.model.entity.CompletionPage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionPageRepository extends JpaRepository<CompletionPage, Long> {

  @Query(value = "" +
      "SELECT c FROM completion_pages c " +
      "JOIN quiz_completion_page_join qc ON c.id = qc.completion_page_id " +
      "JOIN quizzes q ON q.id = qc.quiz_id " +
      "WHERE q.public_id = :public_id", nativeQuery = true)
  Optional<CompletionPage> findCompletionPageByQuizPublicId(@Param("public_id") String publicId);
}
