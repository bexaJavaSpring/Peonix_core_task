package bekhruz.com.peonix_core_task.config;


import bekhruz.com.peonix_core_task.exception.ValidationException;
import bekhruz.com.peonix_core_task.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService service;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        CustomUserDetails userDetails = service.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new ValidationException("invalid.user.details");
        return new UsernamePasswordAuthenticationToken(userDetails, authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
