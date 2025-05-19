package bekhruz.com.peonix_core_task.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {
    private String access_token;
    private String refresh_token;
}
