package com.abac.planet.bm.constraints.validators;

import com.abac.planet.bm.constraints.UniquePlanetMappedConstraint;
import com.abac.planet.bm.service.PlanetManagementService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UniquePlanetMappedValidatorById implements ConstraintValidator<UniquePlanetMappedConstraint, String> {

    private final PlanetManagementService planetManagementService;

    @Override
    public void initialize(final UniquePlanetMappedConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String externalPlanetId, final ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        if (StringUtils.isNotEmpty(externalPlanetId) && !planetManagementService.isPlanetMappedToCrew(externalPlanetId).block()) {
            valid = true;
        }

        return valid;
    }
}