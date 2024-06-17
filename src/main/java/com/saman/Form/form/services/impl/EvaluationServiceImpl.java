package com.saman.Form.form.services.impl;

import com.saman.Form.form.models.Entity.Evaluation;
import com.saman.Form.form.models.Entity.EvaluationCriteria;
import com.saman.Form.form.models.Entity.EvaluationField;
import com.saman.Form.form.repository.EvaluationCriteriaRepository;
import com.saman.Form.form.repository.EvaluationRepository;
import com.saman.Form.form.services.EvaluationService;
import com.saman.Form.utils.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final FormUtil formUtil;
    @Autowired
    private EvaluationRepository evaluationRepository;
    private final EvaluationCriteriaRepository criteriaRepository;

    public EvaluationServiceImpl(FormUtil formUtil, EvaluationCriteriaRepository criteriaRepository) {
        this.formUtil = formUtil;
        this.criteriaRepository = criteriaRepository;
    }


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
        return evaluationRepository.findAll();
    }

    @Override
    public Map<String, Integer> evaluateInputs(Long evaluationId, Long criteriaId, Map<String, Integer> inputs) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new RuntimeException("Evaluation not found"));
        EvaluationCriteria criteria = evaluation.getCriteria().stream().filter(c -> c.getId().equals(criteriaId)).findFirst().orElseThrow(() -> new RuntimeException("Criteria not found"));

        Map<String, Integer> results = new HashMap<>();

        for (Map.Entry<String, Integer> entry : inputs.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

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
                    .orElse(0); // برگرداندن 0 اگر معیار مطابقت ندارد

            results.put(key, highestScore);
        }

        return results;
    }

}

