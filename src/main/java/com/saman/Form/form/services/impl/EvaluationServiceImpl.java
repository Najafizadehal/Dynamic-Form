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

import java.util.List;
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
        return evaluationRepository.save(evaluation);    }

    @Override
    public EvaluationCriteria addCriteriaToEvaluation(Long evaluationId, String criteriaName) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new RuntimeException("Evaluation not found"));
        EvaluationCriteria criteria = new EvaluationCriteria(criteriaName);
        evaluation.addCriteria(criteria);
        evaluationRepository.save(evaluation);
        return criteria;    }

    @Override
    public EvaluationField addFieldToCriteria(Long evaluationId, Long criteriaId, String fieldName, int score) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new RuntimeException("Evaluation not found"));
        EvaluationCriteria criteria = evaluation.getCriteria().stream().filter(c -> c.getId().equals(criteriaId)).findFirst().orElseThrow(() -> new RuntimeException("Criteria not found"));
        EvaluationField field = new EvaluationField(fieldName, score);
        criteria.addField(field);
        evaluationRepository.save(evaluation);
        return field;    }

    @Override
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Override
    public int evaluateInput(Long evaluationId, Long criteriaId, String input) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new RuntimeException("Evaluation not found"));
        EvaluationCriteria criteria = evaluation.getCriteria().stream().filter(c -> c.getId().equals(criteriaId)).findFirst().orElseThrow(() -> new RuntimeException("Criteria not found"));

        Optional<EvaluationField> matchingField = criteria.getFields().stream().filter(field -> input.matches(field.getName())).findFirst();

        return matchingField.map(EvaluationField::getScore).orElseThrow(() -> new RuntimeException("No matching criteria found for input"));
    }
}
