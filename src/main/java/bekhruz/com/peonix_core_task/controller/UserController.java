package bekhruz.com.peonix_core_task.controller;

import bekhruz.com.peonix_core_task.dto.*;
import bekhruz.com.peonix_core_task.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "user controller")
@RequiredArgsConstructor
public class UserController {

    private final IUserService service;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataDto<UserDto>> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataDto<UUID>> create(@RequestBody @Valid UserSaveRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataDto<UUID>> update(@RequestBody @Valid UserUpdateRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataDto<List<UserDto>>> getAll(UserRequest request) {
        return ResponseEntity.ok(service.getAll(request));
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataDto<Boolean>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
