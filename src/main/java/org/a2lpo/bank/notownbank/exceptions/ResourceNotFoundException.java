package org.a2lpo.bank.notownbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * Exception для обработки ситуаций когда пользователь указал не корректные данные и в базе не нашелся запрашиваемый объект
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException implements Serializable {
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String message, String resourceName, String fieldName) {
        super(String.format(message,resourceName,fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = null;
    }

    public ResourceNotFoundException(String message, String resourceName) {
        super(String.format(message));
        this.resourceName = resourceName;
        this.fieldName = null;
        this.fieldValue = null;
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.resourceName = null;
        this.fieldName = null;
        this.fieldValue = null;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
