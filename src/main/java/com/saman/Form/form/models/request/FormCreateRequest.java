package com.saman.Form.form.models.request;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class FormCreateRequest {
    private Map<String, List<Map<String, Map<String, Integer>>>> form;
}
