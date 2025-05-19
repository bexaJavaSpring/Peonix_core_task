package bekhruz.com.peonix_core_task.service;

import bekhruz.com.peonix_core_task.dto.*;
import bekhruz.com.peonix_core_task.entity.auth.User;
import bekhruz.com.peonix_core_task.exception.CustomNotFoundException;
import bekhruz.com.peonix_core_task.mapper.UserMapper;
import bekhruz.com.peonix_core_task.repository.UserRepository;
import bekhruz.com.peonix_core_task.specification.SearchSpecification;
import bekhruz.com.peonix_core_task.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public DataDto<UserDto> get(UUID id) {
        User user = userRepository.findByIdAndDeleted(id);
        if (user == null)
            throw new CustomNotFoundException("user.not.found");

        return new DataDto<>(mapper.toDto(user));
    }

    @Override
    public DataDto<UUID> create(UserSaveRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMiddleName(request.getMiddleName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        User save = userRepository.save(user);
        return new DataDto<>(save.getId());
    }

    @Override
    public DataDto<UUID> update(UserUpdateRequest request) {
        User user = userRepository.findByIdAndDeleted(request.getId());
        if (user == null)
            throw new CustomNotFoundException("user.not.found");

        if (request.getFirstName() != null)
            user.setFirstName(request.getFirstName());
        if (request.getLastName() != null)
            user.setLastName(request.getLastName());
        if (request.getMiddleName() != null)
            user.setMiddleName(request.getMiddleName());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null)
            user.setPhoneNumber(request.getPhoneNumber());

        userRepository.save(user);
        return new DataDto<>(user.getId());
    }

    @Override
    public DataDto<List<UserDto>> getAll(UserRequest request) {
        return new DataDto<>(userRepository.findAll(new UserSpecification(request),
                        SearchSpecification.getPageable(request.getPage(), request.getLimit(), "createdAt"))
                .map(mapper::toDto)
                .toList());
    }

    @Override
    public DataDto<Boolean> delete(UUID id) {
        User user = userRepository.findByIdAndDeleted(id);
        if (user == null)
            throw new CustomNotFoundException("user.not.found");
        user.markAsDeleted();
        userRepository.save(user);
        return new DataDto<>(true);
    }

}
