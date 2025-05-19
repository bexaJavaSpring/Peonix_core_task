package bekhruz.com.peonix_core_task.config.preauthorize;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || !(permission instanceof String))
            return false;
        return hasPrivilege(authentication, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return hasPermission(authentication, null, permission);
    }

    private boolean hasPrivilege(Authentication auth, String permission) {
        return auth.getAuthorities().stream().anyMatch(t -> t.getAuthority().equals(permission));
    }
}
