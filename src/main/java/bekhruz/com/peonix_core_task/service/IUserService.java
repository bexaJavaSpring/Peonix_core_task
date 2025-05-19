package bekhruz.com.peonix_core_task.service;

import bekhruz.com.peonix_core_task.dto.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    DataDto<UUID> create(@Valid UserSaveRequest request);

    DataDto<UUID> update(@Valid UserUpdateRequest request);

    DataDto<List<UserDto>> getAll(UserRequest request);

    DataDto<Boolean> delete(UUID id);

    DataDto<UserDto> get(UUID id);
}
