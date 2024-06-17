package com.saman.Form.form.controllers;

import com.saman.Form.form.models.request.FormCreateRequest;
import com.saman.Form.form.services.EvaluationService;
import com.saman.Form.shared.MyApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/form")
public class FormController {
    Logger logger = LoggerFactory.getLogger(FormController.class);

    private final EvaluationService formService;

    public FormController(EvaluationService formService) {
        this.formService = formService;
    }

}
