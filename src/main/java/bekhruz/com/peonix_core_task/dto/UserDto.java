package bekhruz.com.peonix_core_task.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto implements Serializable {
    private UUID id;
//    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private String email;
    private String logoUrl;
}
