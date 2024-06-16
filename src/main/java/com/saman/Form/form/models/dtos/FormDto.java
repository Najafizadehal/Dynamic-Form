package com.saman.Form.form.models.dtos;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FormDto {
    private long id;
    private String publicId;
    private Map<String, List<Map<String, Map<String, Integer>>>> form;
}
