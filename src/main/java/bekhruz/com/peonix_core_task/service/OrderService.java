package bekhruz.com.peonix_core_task.service;

import bekhruz.com.peonix_core_task.config.UserSession;
import bekhruz.com.peonix_core_task.dto.DataDto;
import bekhruz.com.peonix_core_task.dto.OrderDto;
import bekhruz.com.peonix_core_task.dto.OrderRequest;
import bekhruz.com.peonix_core_task.dto.PaymentRequest;
import bekhruz.com.peonix_core_task.entity.Book;
import bekhruz.com.peonix_core_task.entity.Order;
import bekhruz.com.peonix_core_task.entity.Payment;
import bekhruz.com.peonix_core_task.entity.auth.User;
import bekhruz.com.peonix_core_task.entity.enums.OrderStatus;
import bekhruz.com.peonix_core_task.entity.enums.PaymentType;
import bekhruz.com.peonix_core_task.exception.CustomNotFoundException;
import bekhruz.com.peonix_core_task.exception.GenericRuntimeException;
import bekhruz.com.peonix_core_task.mapper.OrderMapper;
import bekhruz.com.peonix_core_task.repository.BookRepository;
import bekhruz.com.peonix_core_task.repository.OrderRepository;
import bekhruz.com.peonix_core_task.repository.PaymentRepository;
import bekhruz.com.peonix_core_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public DataDto<UUID> create(OrderRequest request) {
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        User user = userRepository.findByIdAndDeleted(request.getUserId());
        if (user == null) {
            throw new CustomNotFoundException("user.not.found");
        }
        order.setUser(user);
        order.setCreatedBy(UserSession.getCurrentUser().getId());
        request.getBookIds().forEach(bookId -> {
            Book book = bookRepository.findByIdAndDeleted(bookId);
            if (book == null) {
                throw new CustomNotFoundException("book.not.found");
            }
            book.setOrder(order);
            bookRepository.save(book);
        });
        orderRepository.save(order);
        return new DataDto<>(order.getId());
    }

    @Override
    public DataDto<OrderDto> get(UUID id) {
        Order order = orderRepository.getReferenceById(id);
        if (order == null) {
            throw new CustomNotFoundException("order.not.found");
        }
        return new DataDto<>(orderMapper.toDto(order));
    }

    @Override
    public DataDto<UUID> update(UUID id, OrderRequest request) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("order.not.found"));
        order.setUpdatedBy(UserSession.getCurrentUser().getId());
        order.setOrderStatus(OrderStatus.valueOf(request.getOrderStatus()));
        request.getBookIds().forEach(bookId -> {
            Book book = bookRepository.findByIdAndDeleted(bookId);
            if (book == null) {
                throw new CustomNotFoundException("book.not.found");
            }
            book.setOrder(order);
            bookRepository.save(book);
        });
        orderRepository.save(order);
        return new DataDto<>(order.getId());
    }

    @Override
    public DataDto<Boolean> delete(UUID id) {
        Book book = bookRepository.findByIdAndDeleted(id);
        if (book == null) {
            throw new CustomNotFoundException("book.not.found");
        }
        book.setDeleted(true);
        book.setDeletedAt(LocalDate.now());
        bookRepository.save(book);
        return new DataDto<>(true);
    }

    @Override
    public DataDto<List<OrderDto>> all() {
        List<OrderDto> list = orderRepository.findAll().stream()
                .map(orderMapper::toDto).toList();
        return new DataDto<>(list, (long) (!list.isEmpty() ? list.size() : 0));
    }

    @Override
    public DataDto<Boolean> payment(PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() -> new CustomNotFoundException("order.not.found"));
        if (request.getCardNumber() % 2 == 0) {
            Payment payment = new Payment();
            payment.setCreatedBy(UserSession.getCurrentUser().getId());
            payment.setPaymentDate(LocalDate.now());
            payment.setCardNumber(request.getCardNumber());
            payment.setPaymentType(PaymentType.valueOf(request.getPaymentMethod()));
            payment.setUserId(request.getUserId());
            payment.setOrderId(request.getOrderId());
            order.setOrderStatus(OrderStatus.PAID);
            paymentRepository.save(payment);
            orderRepository.save(order);
        } else {
            order.setOrderStatus(OrderStatus.REJECTED);
            orderRepository.save(order);
            throw new GenericRuntimeException("payment.is.failed");
        }
        return new DataDto<>(true);
    }
}
