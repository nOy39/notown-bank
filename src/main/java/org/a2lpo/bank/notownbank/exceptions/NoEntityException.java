package org.a2lpo.bank.notownbank.exceptions;

/**
 * Exception, выбрасывается если entity в optional отсутствует.
 */
public class NoEntityException extends RuntimeException {
    private final String fieldName;
    private final String objectType;

    public NoEntityException(String objectType, String fieldName) {
        super(String.format("Object %s not found with %s value", objectType, fieldName));
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
