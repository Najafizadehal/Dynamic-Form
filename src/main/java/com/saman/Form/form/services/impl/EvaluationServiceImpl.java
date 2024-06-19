package com.saman.Form.form.services.impl;

import com.saman.Form.form.models.Entity.Evaluation;
import com.saman.Form.form.models.Entity.EvaluationCriteria;
import com.saman.Form.form.models.Entity.EvaluationField;
import com.saman.Form.form.models.request.CriteriaInput;
import com.saman.Form.form.models.response.JsonResponseEvaluate;
import com.saman.Form.form.repository.EvaluationRepository;
import com.saman.Form.form.services.EvaluationService;
import com.saman.Form.shared.FormException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new FormException("Evaluation not found", HttpStatus.NOT_FOUND));
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
                            if (fieldName != null) {
                                if (fieldName.toLowerCase().contains("less than")) {
                                    int limit = Integer.parseInt(fieldName.replaceAll("[^0-9]", ""));
                                    return value < limit;
                                } else if (fieldName.toLowerCase().contains("greater than")) {
                                    int limit = Integer.parseInt(fieldName.replaceAll("[^0-9]", ""));
                                    return value > limit;
                                } else if (fieldName.toLowerCase().contains("between")) {
                                    String[] limits = fieldName.toLowerCase().replaceAll("[^0-9 ]", "").trim().split("\\s+");
                                    if (limits.length == 2) {
                                        int lowerLimit = Integer.parseInt(limits[0].trim());
                                        int upperLimit = Integer.parseInt(limits[1].trim());
                                        return value >= lowerLimit && value <= upperLimit;
                                    }
                                }
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

    @Override
    public List<JsonResponseEvaluate> evaluateInputs(Long evaluationId, List<CriteriaInput> criteriaInputs) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new FormException("Evaluation not found", HttpStatus.NOT_FOUND));

        List<JsonResponseEvaluate> jsonResponseEvaluates = new ArrayList<>();

        for (CriteriaInput criteriaInput : criteriaInputs) {
            Long criteriaId = Long.parseLong(criteriaInput.getCriteriaId());

            int value;
            try {
                value = Integer.parseInt(criteriaInput.getValue());
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format for value: " + criteriaInput.getValue());
                continue;
            }

            Optional<EvaluationCriteria> optionalCriteria = evaluation.getCriteria().stream()
                    .filter(c -> c.getId().equals(criteriaId))
                    .findFirst();

            if (!optionalCriteria.isPresent()) {
                System.err.println("Criteria not found: " + criteriaId);
                continue;
            }

            EvaluationCriteria criteria = optionalCriteria.get();

            int highestScore = criteria.getFields().stream()
                    .filter(field -> {
                        String fieldName = field.getName();
                        if (fieldName != null) {
                            if (fieldName.toLowerCase().contains("less than")) {
                                int limit = Integer.parseInt(fieldName.replaceAll("[^0-9]", ""));
                                return value < limit;
                            } else if (fieldName.toLowerCase().contains("greater than")) {
                                int limit = Integer.parseInt(fieldName.replaceAll("[^0-9]", ""));
                                return value > limit;
                            } else if (fieldName.toLowerCase().contains("between")) {
                                String[] limits = fieldName.toLowerCase().replaceAll("[^0-9 ]", "").trim().split("\\s+");
                                if (limits.length == 2) {
                                    int lowerLimit = Integer.parseInt(limits[0].trim());
                                    int upperLimit = Integer.parseInt(limits[1].trim());
                                    return value >= lowerLimit && value <= upperLimit;
                                }
                            }
                        }
                        return false;
                    })
                    .mapToInt(EvaluationField::getScore)
                    .max()
                    .orElse(0);

            JsonResponseEvaluate jsonResponseEvaluate = new JsonResponseEvaluate();
            jsonResponseEvaluate.setCriteriaId(criteriaInput.getCriteriaId());
            jsonResponseEvaluate.setValue(value);
            jsonResponseEvaluate.setRate(highestScore);

            jsonResponseEvaluates.add(jsonResponseEvaluate);
        }

        return jsonResponseEvaluates;
    }


}

