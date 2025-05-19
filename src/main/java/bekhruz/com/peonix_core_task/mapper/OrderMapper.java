package bekhruz.com.peonix_core_task.mapper;

import bekhruz.com.peonix_core_task.dto.OrderDto;
import bekhruz.com.peonix_core_task.entity.Book;
import bekhruz.com.peonix_core_task.entity.Order;
import bekhruz.com.peonix_core_task.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public OrderDto toDto(Order order) {
        List<Book> list = bookRepository.findByOrderId(order.getId());
        return OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate().toString())
                .books(bookMapper.toDto(list))
                .orderStatus(order.getOrderStatus().toString())
                .userId(order.getUser().getId())
                .build();
    }
}
