package com.saman.Form.form.repository;

import com.saman.Form.form.models.Entity.EvaluationField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationFieldRepository extends JpaRepository<EvaluationField, Long> {
}
