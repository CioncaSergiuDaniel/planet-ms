package com.abac.planet.bm.constraints.validators;

import com.abac.planet.bm.constraints.UniquePlanetConstraintByName;
import com.abac.planet.bm.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UniquePlanetValidatorByName implements ConstraintValidator<UniquePlanetConstraintByName, String> {

    private final PlanetService planetService;

    @Override
    public void initialize(final UniquePlanetConstraintByName constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String name, final ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        if (StringUtils.isNotEmpty(name) && !planetService.existsByName(name).block()) {
            valid = true;
        }

        return valid;
    }
}