package com.quiz.quizapp.utils.helper;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAUpdateClause;
import java.lang.reflect.Field;
import java.util.Collection;

public class DynamicUpdateHelper {

  public static <T> void setDynamicUpdates(JPAUpdateClause updateClause, T request, PathBuilder<?> entityPath) {
    try {
      for (Field field : request.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        Object value = field.get(request);

        //Skip nulls, collections and entities
        if (value == null) continue;
        if (Collection.class.isAssignableFrom(field.getType())) continue;
        if (!isPrimitiveOrWrapper(field.getType())) continue;

        updateClause.set(entityPath.get(field.getName()), value);
      }
    } catch (Exception e) {
      throw new RuntimeException("Error setting dynamic updates", e);
    }
  }

  private static boolean isPrimitiveOrWrapper(Class<?> type) {
    return type.isPrimitive() ||
        type.equals(String.class) ||
        type.equals(Integer.class) ||
        type.equals(Long.class) ||
        type.equals(Double.class) ||
        type.equals(Float.class) ||
        type.equals(Boolean.class) ||
        type.equals(Short.class) ||
        type.equals(Byte.class) ||
        type.equals(Character.class);
  }
}
