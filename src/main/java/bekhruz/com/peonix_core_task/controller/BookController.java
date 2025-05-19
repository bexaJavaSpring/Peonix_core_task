package bekhruz.com.peonix_core_task.controller;

import bekhruz.com.peonix_core_task.dto.BookDto;
import bekhruz.com.peonix_core_task.dto.BookRequest;
import bekhruz.com.peonix_core_task.dto.DataDto;
import bekhruz.com.peonix_core_task.service.IBookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@Tag(name = "Book controller")
@RequiredArgsConstructor
public class BookController {

    private final IBookService service;

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    private ResponseEntity<DataDto<List<BookDto>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataDto<UUID>> create(@RequestBody BookRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataDto<UUID>> update(@PathVariable(value = "id") UUID id, @RequestBody BookRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataDto<Boolean>> delete(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DataDto<BookDto>> get(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(service.get(id));
    }
}
