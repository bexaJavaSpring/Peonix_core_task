package bekhruz.com.peonix_core_task.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
