package com.quiz.quizapp.service;

import static com.quiz.quizapp.utils.helper.DynamicUpdateHelper.setDynamicUpdates;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.quiz.quizapp.model.entity.QOption;
import com.quiz.quizapp.model.httpmodel.request.UpdateOptionRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionService {

  @PersistenceContext
  private final EntityManager entityManager;

  @Transactional
  public void updateOptionData(List<UpdateOptionRequest> options) {
    QOption option = QOption.option;

    for (UpdateOptionRequest optionRequest : options) {
      JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, option)
          .where(option.id.eq(optionRequest.id()));

      setDynamicUpdates(updateClause, optionRequest, new PathBuilder<>(QOption.class, "option"));

      updateClause.execute();
    }
  }
}
