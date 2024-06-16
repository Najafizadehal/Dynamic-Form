package com.saman.Form.models;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class Forms {

    private Map<String, List<Map<String, Map<String, Integer>>>> form;
}
