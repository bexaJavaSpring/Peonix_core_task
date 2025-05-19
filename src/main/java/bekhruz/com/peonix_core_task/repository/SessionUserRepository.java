package bekhruz.com.peonix_core_task.repository;

import bekhruz.com.peonix_core_task.entity.auth.SessionUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionUserRepository extends JpaRepository<SessionUser, UUID> {
}
