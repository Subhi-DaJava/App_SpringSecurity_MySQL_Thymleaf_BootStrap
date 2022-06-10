package com.ocr.patientsmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This appUser doesn't exist yet")
public class AppUserNotExistingException extends RuntimeException {
    public AppUserNotExistingException(String s) {
    }
}
