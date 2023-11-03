package net.education.kyivstar;

public class ValidationService {

    public static void validateCreateAndStoreValidation(String name, String surname, int age) {
        validateName(name);
        validateName(surname);
        validateAge(age);
    }

    public static void validateReplaceUser(String surname, String name, String surnameToBePlaced, int age) {
        validateName(surname);
        validateName(name);
        validateName(surnameToBePlaced);
        validateAge(age);
    }

    public static void validateNumber(int count) {
        if (count == 0) {
            throw new IllegalArgumentException("POPULATE NUMBER CAN'T BE 0");
        }
    }

    public static void validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("WRONG NAME/SURNAME");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("WRONG NAME/SURNAME");
        }
    }

    public static void validateAge(int age) {

        if (age == 0 || age >= 100 || age < 15) {
            throw new IllegalArgumentException("WRONG AGE");
        }
    }

}
