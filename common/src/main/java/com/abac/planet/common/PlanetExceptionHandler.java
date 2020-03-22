package com.abac.planet.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Generic exception handler for common status codes
 *
 * @author sergiu-daniel.cionca
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class PlanetExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CONSTRAINT_VIOLATION_STATUS_CODE = "400";
    private static final String CASSANDRA_VIOLATION_STATUS_CODE = "500";
    private static final String INTERNAL_SERVER_ERROR = "Unexpected error occurred, please try again later.";

    /**
     * Handles constrain violation exceptions
     *
     * @param ex
     *         - the {@link ConstraintViolationException}
     *
     * @return - a {@link ResponseEntity} containing a friendly format showing the cause of the exception
     */
    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<ErrorTO> handleConstraintViolationException(final ConstraintViolationException ex) {
        final Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        final ErrorTO response;
        if (constraintViolations != null) {
            final String errorMessage = constraintViolations.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            response = new ErrorTO(CONSTRAINT_VIOLATION_STATUS_CODE, errorMessage);
        } else {
            response = new ErrorTO(CONSTRAINT_VIOLATION_STATUS_CODE, ex.getMessage());
        }

        return ResponseEntity.status(Integer.valueOf(CONSTRAINT_VIOLATION_STATUS_CODE)).body(response);
    }

    /**
     * Handles cassandra/persistence exceptions exceptions
     *
     * @param ex
     *         - the {@link DataAccessException}
     *
     * @return - a {@link ResponseEntity} containing a friendly format showing the cause of the exception
     */
    @ExceptionHandler(value = {DataAccessException.class })
    public ResponseEntity<ErrorTO> handleConstraintViolationException(final DataAccessException ex) {
        log.error("Cassandra exception occurred!!!", ex.getMessage(), ex);

        return ResponseEntity.status(Integer.valueOf(CASSANDRA_VIOLATION_STATUS_CODE)).body(new ErrorTO(CASSANDRA_VIOLATION_STATUS_CODE, INTERNAL_SERVER_ERROR));
    }
}
