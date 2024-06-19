package com.saman.Form.form.controllers;

import com.saman.Form.form.models.Entity.Evaluation;
import com.saman.Form.form.models.Entity.EvaluationCriteria;
import com.saman.Form.form.models.Entity.EvaluationField;
import com.saman.Form.form.models.request.*;
import com.saman.Form.form.models.response.JsonResponseEvaluate;
import com.saman.Form.form.repository.EvaluationCriteriaRepository;
import com.saman.Form.form.services.EvaluationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluateController {
    Logger logger = LoggerFactory.getLogger(EvaluateController.class);

    @Autowired
    private EvaluationCriteriaRepository evaluationCriteriaRepository;

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
        logger.info("Criteria was created");
        return ResponseEntity.ok("criteria was created");
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

//    @PostMapping("/{evaluationId}/evaluate")
//    public ResponseEntity<EvaluationResponse> evaluateInputs(@PathVariable Long evaluationId, @RequestBody EvaluationRequest request) {
//        Map<String, Map<String, Integer>> scores = evaluationService.evaluateInputs(evaluationId, request.getCriteriaInputs());
//        EvaluationResponse response = new EvaluationResponse();
//        response.setCriteriaScores(scores);
//        return ResponseEntity.ok(response);
//    }
    @PostMapping("/{evaluationId}/evaluate1")
    public ResponseEntity<List<JsonResponseEvaluate>> evaluateInputs(@PathVariable Long evaluationId, @RequestBody List<CriteriaInput> request) {
        List<JsonResponseEvaluate> scores = evaluationService.evaluateInputs(evaluationId, request);
        return ResponseEntity.ok(scores);
    }
}
