package bekhruz.com.peonix_core_task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDto {
    private UUID id;
    private String orderDate;
    private String orderStatus;
    private UUID userId;
    private List<BookDto> books;
}
