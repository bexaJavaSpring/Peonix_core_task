package bekhruz.com.peonix_core_task.mapper;

import bekhruz.com.peonix_core_task.dto.BookDto;
import bekhruz.com.peonix_core_task.entity.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {
    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .photo(book.getPhoto())
                .price(book.getPrice())
                .orderId(book.getOrder() != null ? book.getOrder().getId().toString() : "")
                .build();
    }

    public List<BookDto> toDto(List<Book> books) {
        List<BookDto> list = new ArrayList<>();
        for (Book book : books) {
            BookDto dto = toDto(book);
            list.add(dto);
        }
        return list;
    }
}
