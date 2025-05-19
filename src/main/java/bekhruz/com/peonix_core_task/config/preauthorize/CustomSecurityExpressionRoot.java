package bekhruz.com.peonix_core_task.config.preauthorize;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomSecurityExpressionRoot implements MethodSecurityExpressionOperations {
    @Setter
    private Authentication authentication;

    @Setter
    private AuthenticationTrustResolver trustResolver;
    @Setter
    private RoleHierarchy roleHierarchy;
    private Set<String> roles;

    private final CustomPermissionEvaluator permissionEvaluator;
    private Object filterObject;
    private Object returnObject;

    @Override
    public final boolean hasAuthority(String authority) {
        throw new RuntimeException("method hasAuthority() not allowed");
    }

    @Override
    public final boolean hasAnyAuthority(String... authorities) {
        return hasAnyAuthorityName(null, authorities);
    }

    @Override
    public final boolean hasRole(String role) {
        return hasAnyRole(role);
    }

    @Override
    public final boolean hasAnyRole(String... roles) {
        return hasAnyAuthorityName("ROLE_", roles);
    }

    private boolean hasAnyAuthorityName(String prefix, String... roles) {
        final Set<String> roleSet = getAuthoritySet();
        for (final String role : roles) {
            final String defaultedRole = getRoleWithDefaultPrefix(prefix, role);
            if (roleSet.contains(defaultedRole))
                return true;
        }
        return false;
    }

    private Set<String> getAuthoritySet() {
        if (roles == null) {
            roles = new HashSet<>();
            Collection<? extends GrantedAuthority> userAuthorities = authentication.getAuthorities();
            if (roleHierarchy != null)
                userAuthorities = roleHierarchy.getReachableGrantedAuthorities(userAuthorities);
            roles = AuthorityUtils.authorityListToSet(userAuthorities);
        }
        return roles;
    }

    private static String getRoleWithDefaultPrefix(String defaultRolePrefix, String role) {
        if (role == null)
            return null;
        if ((defaultRolePrefix == null) || (defaultRolePrefix.isEmpty()))
            return role;
        if (role.startsWith(defaultRolePrefix))
            return role;
        return defaultRolePrefix + role;
    }

    @Override
    public final Authentication getAuthentication() {
        return this.authentication;
    }

    @Override
    public final boolean permitAll() {
        return true;
    }

    @Override
    public final boolean denyAll() {
        return false;
    }

    @Override
    public final boolean isAnonymous() {
        return trustResolver.isAnonymous(authentication);
    }

    @Override
    public final boolean isAuthenticated() {
        return !isAnonymous();
    }

    @Override
    public final boolean isRememberMe() {
        return trustResolver.isRememberMe(authentication);
    }

    @Override
    public final boolean isFullyAuthenticated() {
        return !trustResolver.isAnonymous(authentication) && !trustResolver.isRememberMe(authentication);
    }

    @Override
    public boolean hasPermission(Object target, Object permission) {
        return permissionEvaluator.hasPermission(authentication, target, permission);
    }

    @Override
    public boolean hasPermission(Object targetId, String targetType, Object permission) {
        return permissionEvaluator.hasPermission(authentication, (Serializable) targetId, targetType, permission);
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(Object obj) {
        this.returnObject = obj;
    }
}
