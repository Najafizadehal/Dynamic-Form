package com.saman.Form.form.services;

import com.saman.Form.form.models.request.FormCreateRequest;
import com.saman.Form.shared.MyApiResponse;
import org.springframework.http.ResponseEntity;

public interface FormService {

    ResponseEntity<MyApiResponse> createForm(FormCreateRequest formCreateRequest);
}
