package bekhruz.com.peonix_core_task.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomNotFoundException extends RuntimeException {
    private String field;

    public CustomNotFoundException(String message) {
        super(message);
    }

    public CustomNotFoundException(String field, String message) {
        super(message);
        this.field = field;
    }
}
