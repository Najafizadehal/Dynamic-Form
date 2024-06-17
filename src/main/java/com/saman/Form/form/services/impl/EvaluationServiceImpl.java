package com.saman.Form.form.services.impl;

import com.saman.Form.form.models.Entity.EvaluationCriteria;
import com.saman.Form.form.models.Entity.EvaluationField;
import com.saman.Form.form.repository.EvaluationCriteriaRepository;
import com.saman.Form.form.services.EvaluationService;
import com.saman.Form.utils.FormUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final FormUtil formUtil;

    private final EvaluationCriteriaRepository criteriaRepository;

    public EvaluationServiceImpl(FormUtil formUtil, EvaluationCriteriaRepository criteriaRepository) {
        this.formUtil = formUtil;
        this.criteriaRepository = criteriaRepository;
    }

    @Override
    public EvaluationCriteria addCriteria(String name) {
        EvaluationCriteria criteria = new EvaluationCriteria(name);
        return criteriaRepository.save(criteria);
    }

    @Override
    public EvaluationField addFieldToCriteria(Long criteriaId, String fieldName, int score) {
        EvaluationCriteria criteria = criteriaRepository.findById(criteriaId).orElseThrow(() -> new RuntimeException("Criteria not found"));
        EvaluationField field = new EvaluationField(fieldName, score);
        criteria.addField(field);
        criteriaRepository.save(criteria);
        return field;
    }

    @Override
    public List<EvaluationCriteria> getAllCriteria() {
        return criteriaRepository.findAll();
    }
}
