package com.saman.Form.form.models.request;

import lombok.Data;

import java.util.Map;

@Data
public class EvaluationRequest {
    private Map<String, Map<String, Integer>> criteriaInputs;

    public Map<String, Map<String, Integer>> getCriteriaInputs() {
        return criteriaInputs;
    }

    public void setCriteriaInputs(Map<String, Map<String, Integer>> criteriaInputs) {
        this.criteriaInputs = criteriaInputs;
    }
}
