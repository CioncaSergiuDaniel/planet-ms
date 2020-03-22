package com.abac.planet.bm.constraints;


import com.abac.planet.bm.constraints.validators.UniquePlanetValidatorByName;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UniquePlanetValidatorByName.class,})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, })
public @interface UniquePlanetConstraintByName {

    /**
     * Used to set the error message
     *
     * @return message when the instance fails the validation
     */
    String message() default "Planet already exists ${name}";

    Class<?>[] groups() default {};

    /**
     * Optional payloads for the constraint
     *
     * @return the assigned payloads if any
     */
    Class<? extends Payload>[] payload() default {};
}
