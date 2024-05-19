package fit.nlu.cnpmbookshopweb.utils.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EmailValidator implements ConstraintValidator<Email, String> {
    private Pattern pattern;

    @Override
    public void initialize(Email constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        try {
            pattern = Pattern.compile(constraintAnnotation.regex());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Given regex is invalid", e);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.equals("")) throw new IllegalArgumentException("Email incorrect format");;

        return pattern.matcher(value).matches();
    }

}
