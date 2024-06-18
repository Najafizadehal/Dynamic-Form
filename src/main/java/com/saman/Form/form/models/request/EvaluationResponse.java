package com.saman.Form.form.models.request;

import java.util.Map;

public class EvaluationResponse {
    private Map<String, Map<String, Integer>> criteriaScores;

    public Map<String, Map<String, Integer>> getCriteriaScores() {
        return criteriaScores;
    }

    public void setCriteriaScores(Map<String, Map<String, Integer>> criteriaScores) {
        this.criteriaScores = criteriaScores;
    }
}
