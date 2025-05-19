package bekhruz.com.peonix_core_task.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequest {
    @NotEmpty(message = "bookIds should not be empty")
    private List<UUID> bookIds;
    @NotNull(message = "user.id.should.not.be.null")
    private UUID userId;
    private String orderStatus;
}
