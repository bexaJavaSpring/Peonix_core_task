package bekhruz.com.peonix_core_task.controller;

import bekhruz.com.peonix_core_task.dto.*;
import bekhruz.com.peonix_core_task.service.IAuthService;
import bekhruz.com.peonix_core_task.util.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@Tag(name = "Auth controller")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    @Operation(description = "API for logging users")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthLoginDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register")
    @Operation(description = "API for registering users")
    public ResponseEntity<DataDto<UUID>> registerWithEmailPassword(@RequestBody @Valid UserRegisterDto dto) {
        return ResponseEntity.ok(authService.registerWithEmailPassword(dto));
    }

    @PostMapping(value = "/verify")
    @Operation(description = "Verify verification code is match or not for user")
    public ResponseEntity<DataDto<Boolean>> verify(@RequestBody @Valid UserVerifyDto dto) {
        return ResponseEntity.ok(authService.verify(dto));
    }

    @GetMapping("/me")
    @Operation(description = "API for users me")
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<UserMeResponse> me() {
        return ResponseEntity.ok(authService.me());
    }

    @PostMapping(value = "/logout")
    @PreAuthorize(value = "isAuthenticated()")
    @Operation(description = "Logging out from the platform")
    public ResponseEntity<Boolean> logout() {
        return ResponseEntity.ok(authService.logout());
    }
}
