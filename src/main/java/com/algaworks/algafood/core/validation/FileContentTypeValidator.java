package com.algaworks.algafood.core.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedMediaTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        allowedMediaTypes = List.of(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || allowedMediaTypes.contains(value.getContentType());
    }
}
