package bekhruz.com.peonix_core_task.repository;

import bekhruz.com.peonix_core_task.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
