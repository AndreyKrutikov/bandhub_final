package by.krutikov.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final String VALID_PHONE_NUMBER_REGEX = "^([+]|[00])?[\\d]{12}$";
    private static final Pattern pattern = Pattern.compile(VALID_PHONE_NUMBER_REGEX);

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneString, ConstraintValidatorContext context) {
        return pattern.matcher(phoneString).matches();
    }
}
