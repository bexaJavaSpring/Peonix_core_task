package bekhruz.com.peonix_core_task.repository;

import bekhruz.com.peonix_core_task.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);

    @Query("select t from User t where t.id=?1 and t.deleted=false")
    User findByIdAndDeleted(UUID id);
}
