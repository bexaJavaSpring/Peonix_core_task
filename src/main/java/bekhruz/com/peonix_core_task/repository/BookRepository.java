package bekhruz.com.peonix_core_task.repository;

import bekhruz.com.peonix_core_task.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("select t from Book t where t.id=?1 and t.deleted = false")
    Book findByIdAndDeleted(UUID id);

    @Query("select t from Book t where t.order.id = ?1 and t.deleted = false")
    List<Book> findByOrderId(UUID orderId);
}
