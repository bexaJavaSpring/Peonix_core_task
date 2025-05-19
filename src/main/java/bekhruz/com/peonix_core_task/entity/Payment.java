package bekhruz.com.peonix_core_task.entity;

import bekhruz.com.peonix_core_task.entity.base.Auditable;
import bekhruz.com.peonix_core_task.entity.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends Auditable {

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "card_number")
    private Long cardNumber;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "user_id")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}
