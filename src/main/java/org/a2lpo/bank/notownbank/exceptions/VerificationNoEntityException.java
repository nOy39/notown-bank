package org.a2lpo.bank.notownbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class VerificationNoEntityException extends RuntimeException {
    private final String fieldName;
    private final String objectName;

    public VerificationNoEntityException(String message) {
        super(message);
        this.fieldName = null;
        this.objectName = null;
    }

    public VerificationNoEntityException(String message, String fieldName) {
        super(String.format(message, fieldName));
        this.fieldName = fieldName;
        this.objectName = null;
    }

    public VerificationNoEntityException(String message, String fieldName, String objectName) {
        super(String.format(message, fieldName, objectName));
        this.fieldName = fieldName;
        this.objectName = objectName;
    }

    public VerificationNoEntityException(String message, Throwable cause, String fieldName, String objectName) {
        super(message, cause);
        this.fieldName = fieldName;
        this.objectName = objectName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getObjectName() {
        return objectName;
    }
}
