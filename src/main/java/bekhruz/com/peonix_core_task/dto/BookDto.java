package bekhruz.com.peonix_core_task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDto {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private String photo;
    private String  orderId;
}
