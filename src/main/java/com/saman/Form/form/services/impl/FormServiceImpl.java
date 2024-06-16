package com.saman.Form.form.services.impl;

import com.saman.Form.form.models.request.FormCreateRequest;
import com.saman.Form.form.services.FormService;
import com.saman.Form.shared.MyApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FormServiceImpl implements FormService {
    @Override
    public ResponseEntity<MyApiResponse> createForm(FormCreateRequest formCreateRequest) {

        return null;
    }
}
