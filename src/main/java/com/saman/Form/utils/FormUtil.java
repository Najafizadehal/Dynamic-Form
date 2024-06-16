package com.saman.Form.utils;

import com.saman.Form.form.models.dtos.FormDto;
import com.saman.Form.form.models.request.FormCreateRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class FormUtil {
    Logger logger = LoggerFactory.getLogger(FormUtil.class);

    private final ModelMapper modelMapper = new ModelMapper();

    public FormDto convert(FormCreateRequest formCreateRequest) {
        return createDtoModel(formCreateRequest);
    }

    private FormDto createDtoModel(FormCreateRequest formCreateRequest) {
        FormDto formDto = modelMapper.map(formCreateRequest, FormDto.class);
        formDto.setPublicId(getPublicId());
        return formDto;
    }

    private String generateRandomString() {
        logger.info("GenerateRandomString() has called");

        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder returnValue = new StringBuilder();
        int length = 32;
        for (int i = 0; i < length; i++) {
            returnValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return returnValue.toString();
    }

    public String getPublicId() {
        return generateRandomString();
    }
}
