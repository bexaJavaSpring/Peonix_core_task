package bekhruz.com.peonix_core_task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequest {
    private UUID orderId;
    private UUID userId;
    private String paymentMethod;
    private String paymentAmount;
    private Long cardNumber;
}
