package com.saman.Form.form.services.impl;

import com.saman.Form.form.models.Entity.Evaluation;
import com.saman.Form.form.models.Entity.EvaluationCriteria;
import com.saman.Form.form.models.Entity.EvaluationField;
import com.saman.Form.form.repository.EvaluationCriteriaRepository;
import com.saman.Form.form.repository.EvaluationRepository;
import com.saman.Form.form.services.EvaluationService;
import com.saman.Form.shared.FormException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public Evaluation addEvaluation(String name) {
        Evaluation evaluation = new Evaluation(name);
        return evaluationRepository.save(evaluation);
    }

    @Override
    public EvaluationCriteria addCriteriaToEvaluation(Long evaluationId, String criteriaName) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new RuntimeException("Evaluation not found"));
        EvaluationCriteria criteria = new EvaluationCriteria(criteriaName);
        evaluation.addCriteria(criteria);
        evaluationRepository.save(evaluation);
        return criteria;
    }

    @Override
    public EvaluationField addFieldToCriteria(Long evaluationId, Long criteriaId, String fieldName, int score) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new RuntimeException("Evaluation not found"));
        EvaluationCriteria criteria = evaluation.getCriteria().stream().filter(c -> c.getId().equals(criteriaId)).findFirst().orElseThrow(() -> new RuntimeException("Criteria not found"));
        EvaluationField field = new EvaluationField(fieldName, score);
        criteria.addField(field);
        evaluationRepository.save(evaluation);
        return field;
    }

    @Override
    public List<Evaluation> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationRepository.findAll();
        if (evaluations.isEmpty()){
            throw new FormException("Any Evaluation not found", HttpStatus.NOT_FOUND);
        }
        return evaluationRepository.findAll();
    }

    @Override
    public Map<String, Map<String, Integer>> evaluateInputs(Long evaluationId, Map<String, Map<String, Integer>> criteriaInputs) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new FormException("Evaluation not found", HttpStatus.NOT_FOUND));
        Map<String, Map<String, Integer>> criteriaScores = new HashMap<>();

        for (Map.Entry<String, Map<String, Integer>> criteriaEntry : criteriaInputs.entrySet()) {
            Long criteriaId = Long.parseLong(criteriaEntry.getKey());
            Map<String, Integer> inputs = criteriaEntry.getValue();
            Map<String, Integer> results = new HashMap<>();

            EvaluationCriteria criteria = evaluation.getCriteria().stream()
                    .filter(c -> c.getId().equals(criteriaId))
                    .findFirst()
                    .orElseThrow(() -> new FormException("Criteria not found: " + criteriaId, HttpStatus.NOT_FOUND));

            for (Map.Entry<String, Integer> inputEntry : inputs.entrySet()) {
                String key = inputEntry.getKey();
                int value = inputEntry.getValue();

                int highestScore = criteria.getFields().stream()
                        .filter(field -> {
                            String fieldName = field.getName();
                            if (fieldName != null && fieldName.toLowerCase().contains("less than")) {
                                int limit = Integer.parseInt(fieldName.replaceAll("[^0-9]", ""));
                                return value < limit;
                            }
                            return false;
                        })
                        .mapToInt(EvaluationField::getScore)
                        .max()
                        .orElse(0);

                results.put(key, highestScore);
            }
            criteriaScores.put(criteriaEntry.getKey(), results);
        }

        return criteriaScores;
    }


}

