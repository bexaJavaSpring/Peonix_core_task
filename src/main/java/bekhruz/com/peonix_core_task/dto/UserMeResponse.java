package bekhruz.com.peonix_core_task.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserMeResponse {
    private UUID id;
    private String fullName;
    private String email;
    private String lang;
    private List<RoleShortDto> role;
}
