package com.saman.Form.form.models.response;

import lombok.Data;

import java.util.Map;
@Data
public class EvaluationResponse {
    private Map<String, Map<String, Integer>> criteriaScores;
}
