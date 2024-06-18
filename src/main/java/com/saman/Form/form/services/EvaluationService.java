package com.saman.Form.form.services;

import com.saman.Form.form.models.Entity.Evaluation;
import com.saman.Form.form.models.Entity.EvaluationCriteria;
import com.saman.Form.form.models.Entity.EvaluationField;
import com.saman.Form.form.models.request.FormCreateRequest;
import com.saman.Form.shared.MyApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EvaluationService {

    Evaluation addEvaluation(String name);
    EvaluationCriteria addCriteriaToEvaluation(Long evaluationId, String criteriaName);
    EvaluationField addFieldToCriteria(Long evaluationId, Long criteriaId, String fieldName, int score);
    List<Evaluation> getAllEvaluations();
    Map<String, Map<String, Integer>> evaluateInputs(Long evaluationId, Map<String, Map<String, Integer>> criteriaInputs);
}
