package bekhruz.com.peonix_core_task.handler;

import bekhruz.com.peonix_core_task.exception.AlreadyExistsException;
import bekhruz.com.peonix_core_task.exception.CustomNotFoundException;
import bekhruz.com.peonix_core_task.exception.GenericRuntimeException;
import bekhruz.com.peonix_core_task.exception.ValidationException;
import bekhruz.com.peonix_core_task.util.ErrorUtil;
import bekhruz.com.peonix_core_task.util.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionHandler {

    private final Translator translator;

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("AccessDeniedException on: {}", ErrorUtil.getStacktrace(ex));
        return new ResponseEntity<>(Map.of("message", List.of(translator.toLocale(ex.getMessage()))),
                HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleError(final MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException on: {}", ErrorUtil.getStacktrace(ex));
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError ->
                {
                    if (!translator.toLocale(fieldError.getDefaultMessage()).equals(fieldError.getDefaultMessage())) {
                        return Objects.requireNonNull(translator.toLocale(fieldError.getDefaultMessage()));
                    } else {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    }

                }).toList();
        return new ResponseEntity<>(Map.of("message", errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(final BadCredentialsException e) {
        log.error("BadCredentialsException on: {}", ErrorUtil.getStacktrace(e));
        return new ResponseEntity<>(Map.of("message", List.of(translator.toLocale("unauthorized"))), new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(final CustomNotFoundException e) {
        log.error("CustomNotFoundException on: {}", ErrorUtil.getStacktrace(e));
        String message = translator.toLocale(e.getMessage());
        if (StringUtils.hasText(e.getField())) {
            String translatedField = translator.toLocale(e.getField());
            message = String.format(translator.toLocale(e.getMessage()), translatedField);
        }
        return new ResponseEntity<>(Map.of("message", List.of(message)), new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidatorException(final ValidationException e) {
        log.error("ValidatorException on: {}", ErrorUtil.getStacktrace(e));
        return new ResponseEntity<>(Map.of("message", List.of(translator.toLocale(e.getMessage()))), new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleValidatorException(final AlreadyExistsException e) {
        log.error("AlreadyExistsException on: {}", ErrorUtil.getStacktrace(e));
        return new ResponseEntity<>(Map.of("message", List.of(translator.toLocale(e.getMessage()))), new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({RuntimeException.class, Throwable.class})
    public ResponseEntity<?> handleException(final Exception e) {
        log.error("Exception on: {}", ErrorUtil.getStacktrace(e));
        return new ResponseEntity<>(Map.of("message", e.getMessage()), new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(GenericRuntimeException.class)
    public final ResponseEntity<?> handleGenericRuntimeException(final GenericRuntimeException e) {
        log.error("AlreadyExistsException on: {}", ErrorUtil.getStacktrace(e));
        return new ResponseEntity<>(Map.of("message", List.of(translator.toLocale(e.getMessage()))), new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

}
