package org.a2lpo.bank.notownbank.exceptions;

public class NoEntityException extends RuntimeException {
    private final String fieldName;
    private final String objectType;

    public NoEntityException(String fieldName, String objectType) {
        super(String.format("Object %s not found with %s name", objectType, fieldName));
        this.fieldName = fieldName;
        this.objectType = objectType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getObjectType() {
        return objectType;
    }
}
