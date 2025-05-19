package bekhruz.com.peonix_core_task.repository;

import bekhruz.com.peonix_core_task.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
