package com.saman.Form.form.repository;

import com.saman.Form.form.models.Entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

}
