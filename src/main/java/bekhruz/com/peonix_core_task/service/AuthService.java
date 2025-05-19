package bekhruz.com.peonix_core_task.service;

import bekhruz.com.peonix_core_task.config.CustomAuthenticationProvider;
import bekhruz.com.peonix_core_task.config.CustomUserDetails;
import bekhruz.com.peonix_core_task.config.UserSession;
import bekhruz.com.peonix_core_task.dto.*;
import bekhruz.com.peonix_core_task.entity.auth.SessionUser;
import bekhruz.com.peonix_core_task.entity.auth.Role;
import bekhruz.com.peonix_core_task.entity.auth.User;
import bekhruz.com.peonix_core_task.entity.enums.Status;
import bekhruz.com.peonix_core_task.exception.CustomNotFoundException;
import bekhruz.com.peonix_core_task.repository.RoleRepository;
import bekhruz.com.peonix_core_task.repository.SessionUserRepository;
import bekhruz.com.peonix_core_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final CustomAuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final SessionUserRepository sessionUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailSenderService emailSenderService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LoginResponse login(AuthLoginDto dto) {

        Authentication authenticate = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getEmail(), dto.getPassword()
        ));
        CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
        User user = userRepository.findByIdAndDeleted(userDetails.getId());
        if (user == null) {
            throw new CustomNotFoundException(userDetails.getId().toString(), "user.not.found");
        }
        Optional<SessionUser> optionalSessionUser = sessionUserRepository.findById(user.getId());
        if (optionalSessionUser.isPresent()) {
            SessionUser session = optionalSessionUser.get();
            String accessToken = jwtTokenService.generateToken(userDetails.getUsername());
            String refreshToken = jwtTokenService.generateToken(userDetails.getUsername());
            session.setAccessToken(accessToken);
            session.setRefreshToken(refreshToken);
            session.setStatus(Status.ACTIVE);
            sessionUserRepository.save(session);
            return LoginResponse.builder()
                    .access_token(session.getAccessToken())
                    .refresh_token(session.getRefreshToken())
                    .build();
        }
        String accessToken = jwtTokenService.generateToken(userDetails.getUsername());
        String refreshToken = jwtTokenService.generateToken(userDetails.getUsername());
        sessionUserRepository.save(SessionUser.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(Status.ACTIVE)
                .build());
        return LoginResponse.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserMeResponse me() {
        User user = userRepository.findByIdAndDeleted(UserSession.getCurrentUser().getId());
        if (user == null) {
            throw new CustomNotFoundException("user.not.found");
        }
        UserMeResponse userMeResponse = new UserMeResponse();
        userMeResponse.setId(user.getId());
        userMeResponse.setFullName(user.getFullName());
        userMeResponse.setLang(UserSession.getLanguage().name());
        userMeResponse.setRole(
                user.getRoles().stream().map(
                        role -> RoleShortDto.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .build()
                ).collect(Collectors.toList()
                ));
        return userMeResponse;
    }

    @Override
    public Boolean logout() {
        User user = userRepository.findByIdAndDeleted(UserSession.getCurrentUser().getId());
        if (user == null) {
            throw new CustomNotFoundException("user.not.found");
        }
        Optional<SessionUser> sessionOptional = sessionUserRepository.findById(user.getId());
        if (sessionOptional.isPresent()) {
            SessionUser session = sessionOptional.get();
            session.setStatus(Status.DISABLED);
            sessionUserRepository.save(session);
        }
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public DataDto<UUID> registerWithEmailPassword(UserRegisterDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Role role = roleRepository.findByCode("USER");
        user.setRoles(Arrays.asList(role));
        user.setUsername(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        String verificationCode = emailSenderService.generateVerificationCode(dto.getEmail());
        user.setVerificationCode(verificationCode);
        User save = userRepository.save(user);
        return new DataDto<>(save.getId());
    }

    @Override
    public DataDto<Boolean> verify(UserVerifyDto dto) {
        User user = userRepository.findByIdAndDeleted(dto.getUserId());
        if (user == null)
            throw new CustomNotFoundException("user.not.found");

        if (user.getVerificationCode().equals(dto.getVerificationCode())) {
            return new DataDto<>(true);
        }
        return new DataDto<>(false);
    }

}
