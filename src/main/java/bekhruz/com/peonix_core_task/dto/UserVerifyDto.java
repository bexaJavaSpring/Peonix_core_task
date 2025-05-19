package bekhruz.com.peonix_core_task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserVerifyDto {
    @NotNull(message = "user.id.should.not.be.null")
    private UUID userId;

    @NotNull(message = "verification.code.should.not.be.null")
    @NotBlank(message = "verification.code.should.not.be.blank")
    private String verificationCode;
}
