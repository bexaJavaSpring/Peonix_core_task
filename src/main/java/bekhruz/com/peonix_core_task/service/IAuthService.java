package bekhruz.com.peonix_core_task.service;

import bekhruz.com.peonix_core_task.dto.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface IAuthService {
    LoginResponse login(@Valid AuthLoginDto dto);

    UserMeResponse me();

    Boolean logout();

    DataDto<UUID> registerWithEmailPassword(@Valid UserRegisterDto dto);

    DataDto<Boolean> verify(UserVerifyDto dto);
}
