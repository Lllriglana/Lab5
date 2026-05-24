package ITMO.Lab5.util;

import ITMO.Lab5.exceptions.ValidationException;

/**
 * Utility class with validation helpers for domain constraints.
 */
public final class Validators {
    private Validators() {
    }

    public static Long requireValidId(Long id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("id must not be null");
        }
        if (id <= 0) {
            throw new ValidationException("id must be greater than 0");
        }
        return id;
    }

    public static String requireNotBlank(String value, String fieldName) throws ValidationException {
        if (value == null) {
            throw new ValidationException(fieldName + " must not be null");
        }
        if (value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " must not be empty");
        }
        return value;
    }

    public static <T> T requireNotNull(T value, String fieldName) throws ValidationException {
        if (value == null) {
            throw new ValidationException(fieldName + " must not be null");
        }
        return value;
    }

    public static int requireGreaterThanZero(int value, String fieldName) throws ValidationException {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be greater than 0");
        }
        return value;
    }

    public static double requireGreaterThanZero(double value, String fieldName) throws ValidationException {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be greater than 0");
        }
        return value;
    }

    public static long requireLessOrEqual(long value, long max, String fieldName) throws ValidationException {
        if (value > max) {
            throw new ValidationException(fieldName + " must be less than or equal to " + max);
        }
        return value;
    }

    public static float requireLessOrEqual(float value, float max, String fieldName) throws ValidationException {
        if (value > max) {
            throw new ValidationException(fieldName + " must be less than or equal to " + max);
        }
        return value;
    }
}
