package bekhruz.com.peonix_core_task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSaveRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private UUID regionId;
    private UUID districtId;
    private String objectName;
    private Double longitude;
    private Double latitude;
}
