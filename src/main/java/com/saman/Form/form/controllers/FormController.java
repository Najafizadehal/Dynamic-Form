package com.saman.Form.form.controllers;

import com.saman.Form.form.models.Entity.Evaluation;
import com.saman.Form.form.models.Entity.EvaluationCriteria;
import com.saman.Form.form.models.Entity.EvaluationField;
import com.saman.Form.form.models.request.FieldRequest;
import com.saman.Form.form.models.request.FormCreateRequest;
import com.saman.Form.form.models.request.Input;
import com.saman.Form.form.services.EvaluationService;
import com.saman.Form.shared.MyApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/evaluations")
public class FormController {
    Logger logger = LoggerFactory.getLogger(FormController.class);

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<String> addEvaluation(@RequestBody String name) {
        Evaluation evaluation = evaluationService.addEvaluation(name);
        return ResponseEntity.ok("evaluation created");
    }

    @PostMapping("/{evaluationId}/criteria")
    public ResponseEntity<String> addCriteriaToEvaluation(@PathVariable Long evaluationId, @RequestBody String criteriaName) {
        EvaluationCriteria criteria = evaluationService.addCriteriaToEvaluation(evaluationId, criteriaName);
        return ResponseEntity.ok("criteria created");
    }

    @PostMapping("/{evaluationId}/criteria/{criteriaId}/fields")
    public ResponseEntity<String> addFieldToCriteria(@PathVariable Long evaluationId, @PathVariable Long criteriaId, @RequestBody FieldRequest fieldRequest) {
        EvaluationField field = evaluationService.addFieldToCriteria(evaluationId, criteriaId, fieldRequest.getFieldname(), fieldRequest.getScore());
        return ResponseEntity.ok("field created");
    }

    @GetMapping
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        return ResponseEntity.ok(evaluations);
    }

    @PostMapping("/{evaluationId}/criteria/{criteriaId}/evaluate")
    public ResponseEntity<Map<String, Integer>> evaluateInputs(@PathVariable Long evaluationId, @PathVariable Long criteriaId, @RequestBody Map<String, Integer> inputs) {
        Map<String, Integer> scores = evaluationService.evaluateInputs(evaluationId, criteriaId, inputs);
        return ResponseEntity.ok(scores);
    }
}
