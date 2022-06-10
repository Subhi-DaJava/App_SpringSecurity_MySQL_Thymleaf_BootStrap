package com.ocr.patientsmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This appUser already exists in the DB")
public class appUserExistingException extends RuntimeException {
    public appUserExistingException(String s) {
    }
}
