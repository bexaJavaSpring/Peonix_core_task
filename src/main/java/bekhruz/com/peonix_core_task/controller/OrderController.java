package bekhruz.com.peonix_core_task.controller;

import bekhruz.com.peonix_core_task.dto.DataDto;
import bekhruz.com.peonix_core_task.dto.OrderDto;
import bekhruz.com.peonix_core_task.dto.OrderRequest;
import bekhruz.com.peonix_core_task.dto.PaymentRequest;
import bekhruz.com.peonix_core_task.service.IOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order controller")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService service;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DataDto<UUID>> create(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DataDto<List<OrderDto>>> getAll() {
        return ResponseEntity.ok(service.all());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    private ResponseEntity<DataDto<OrderDto>> get(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DataDto<UUID>> update(@PathVariable(value = "id") UUID id, @RequestBody OrderRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DataDto<Boolean>> delete(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PostMapping("/payment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DataDto<Boolean>> payment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(service.payment(request));
    }
}
