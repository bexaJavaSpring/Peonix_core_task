package bekhruz.com.peonix_core_task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthLoginDto {
    @NotNull(message = "email.not.null")
    @NotBlank(message = "email.not.blank")
    private String email;

    @NotNull(message = "password.not.null")
    @NotBlank(message = "password.not.blank")
    private String password;
}
