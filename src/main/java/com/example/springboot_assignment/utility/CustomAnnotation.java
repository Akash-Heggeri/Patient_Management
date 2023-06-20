package com.example.springboot_assignment.utility;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

public class CustomAnnotation {
	@ConstraintComposition(CompositionType.OR)
	@Email
	@Pattern(regexp = "\\d{10}")
	@Target({ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = {})
	@ReportAsSingleViolation
	@Documented
	public @interface EmailOrPhone {
	    String message() default "Provided value was neither a valid Email nor a valid Phone number";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {  };
	}
}
