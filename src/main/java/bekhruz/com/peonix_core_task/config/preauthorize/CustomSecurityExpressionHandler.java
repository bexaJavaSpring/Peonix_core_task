package bekhruz.com.peonix_core_task.config.preauthorize;

import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;
@Component
@RequiredArgsConstructor
public class CustomSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {
    private final CustomSecurityExpressionRoot expressionRoot;

    @Override
    public EvaluationContext createEvaluationContext(Supplier<Authentication> authentication, MethodInvocation mi) {
        StandardEvaluationContext context = (StandardEvaluationContext) super.createEvaluationContext(authentication, mi);
        expressionRoot.setAuthentication(authentication.get());
        expressionRoot.setFilterObject(new Object());
        expressionRoot.setReturnObject(new Object());
        expressionRoot.setTrustResolver(new AuthenticationTrustResolverImpl());
        context.setRootObject(expressionRoot);
        return context;
    }
}
