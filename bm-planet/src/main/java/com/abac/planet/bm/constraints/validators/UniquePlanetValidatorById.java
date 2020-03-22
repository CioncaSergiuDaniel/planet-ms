package com.abac.planet.bm.constraints.validators;

import com.abac.planet.bm.constraints.UniquePlanetConstraintById;
import com.abac.planet.bm.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UniquePlanetValidatorById implements ConstraintValidator<UniquePlanetConstraintById, String> {

    private final PlanetService planetService;

    @Override
    public void initialize(final UniquePlanetConstraintById constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String externalId, final ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        if (StringUtils.isNotEmpty(externalId) && planetService.existsByExternalId(externalId).block()) {
            valid = true;
        }

        return valid;
    }
}