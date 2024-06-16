package com.saman.Form.form.services.impl;

import com.saman.Form.form.models.dtos.FormDto;
import com.saman.Form.form.models.request.FormCreateRequest;
import com.saman.Form.form.services.FormService;
import com.saman.Form.shared.MyApiResponse;
import com.saman.Form.utils.FormUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FormServiceImpl implements FormService {

    private final FormUtil formUtil;

    public FormServiceImpl(FormUtil formUtil) {
        this.formUtil = formUtil;
    }

    @Override
    public ResponseEntity<MyApiResponse> createForm(FormCreateRequest formCreateRequest) {

        FormDto formDto = formUtil.convert(formCreateRequest);

        return null;
    }
}
