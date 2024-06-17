package com.saman.Form.form.services;

import com.saman.Form.form.models.Entity.EvaluationCriteria;
import com.saman.Form.form.models.Entity.EvaluationField;
import com.saman.Form.form.models.request.FormCreateRequest;
import com.saman.Form.shared.MyApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EvaluationService {

    EvaluationCriteria addCriteria(String name);
    EvaluationField addFieldToCriteria(Long criteriaId, String fieldName, int score);
    List<EvaluationCriteria> getAllCriteria();
//    ResponseEntity<MyApiResponse> createForm(FormCreateRequest formCreateRequest);
}
