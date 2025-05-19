package bekhruz.com.peonix_core_task.service;

import bekhruz.com.peonix_core_task.config.UserSession;
import bekhruz.com.peonix_core_task.dto.BookDto;
import bekhruz.com.peonix_core_task.dto.BookRequest;
import bekhruz.com.peonix_core_task.dto.DataDto;
import bekhruz.com.peonix_core_task.entity.Book;
import bekhruz.com.peonix_core_task.entity.Order;
import bekhruz.com.peonix_core_task.exception.CustomNotFoundException;
import bekhruz.com.peonix_core_task.mapper.BookMapper;
import bekhruz.com.peonix_core_task.repository.BookRepository;
import bekhruz.com.peonix_core_task.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;
    private final OrderRepository orderRepository;

    @Override
    public DataDto<List<BookDto>> getAll() {
        List<BookDto> list = bookRepository.findAll().stream().map(mapper::toDto).toList();
        return new DataDto<>(list, (long) (!list.isEmpty() ? list.size() : 0));
    }

    @Override
    public DataDto<UUID> create(BookRequest request) {
        Book book = new Book();
        book.setName(request.getName());
        book.setPrice(request.getPrice());
        book.setDescription(request.getDescription());
        book.setPhoto(request.getPhoto());
        book.setCreatedBy(UserSession.getCurrentUser().getId());
        Book save = bookRepository.save(book);
        return new DataDto<>(save.getId());
    }

    @Override
    public DataDto<UUID> update(UUID id, BookRequest request) {
        Book book = bookRepository.findByIdAndDeleted(id);
        if (book != null) {
            book.setName(request.getName());
            book.setPrice(request.getPrice());
            book.setDescription(request.getDescription());
            book.setPhoto(request.getPhoto());
            book.setUpdatedBy(UserSession.getCurrentUser().getId());
            if (request.getOrderId() != null) {
                Order order = orderRepository.getReferenceById(request.getOrderId());
                if (order != null)
                    book.setOrder(order);
            }
            bookRepository.save(book);
        } else
            throw new CustomNotFoundException("book.not.found");
        return new DataDto<>(book.getId());
    }

    @Override
    public DataDto<Boolean> delete(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("book.not.found"));
        book.setDeleted(true);
        book.setDeletedAt(LocalDate.now());
        bookRepository.save(book);
        return new DataDto<>(true);
    }

    @Override
    public DataDto<BookDto> get(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("book.not.found"));
        return new DataDto<>(mapper.toDto(book));
    }
}
