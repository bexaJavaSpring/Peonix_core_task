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
public class BookRequest {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private String photo;
    private UUID orderId;
}
