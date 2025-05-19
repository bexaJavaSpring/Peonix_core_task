package bekhruz.com.peonix_core_task.service;

import bekhruz.com.peonix_core_task.dto.BookDto;
import bekhruz.com.peonix_core_task.dto.BookRequest;
import bekhruz.com.peonix_core_task.dto.DataDto;

import java.util.List;
import java.util.UUID;

public interface IBookService {
    DataDto<List<BookDto>> getAll();

    DataDto<UUID> create(BookRequest request);

    DataDto<UUID> update(UUID id, BookRequest request);

    DataDto<Boolean> delete(UUID id);

    DataDto<BookDto> get(UUID id);
}
