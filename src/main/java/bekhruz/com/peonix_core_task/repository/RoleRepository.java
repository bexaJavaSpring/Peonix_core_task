package bekhruz.com.peonix_core_task.repository;

import bekhruz.com.peonix_core_task.entity.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByCode(String code);
}
