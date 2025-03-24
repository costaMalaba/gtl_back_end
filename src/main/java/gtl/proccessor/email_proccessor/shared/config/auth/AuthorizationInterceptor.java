package gtl.proccessor.email_proccessor.shared.config.auth;

import gtl.proccessor.email_proccessor.shared.config.EnvConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final EnvConfig envConfig;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {

        String apiKey = request.getHeader("x-api-key");

        if (apiKey != null && apiKey.equals(envConfig.getApiKey())) {
            return true;
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
        return false;
    }
}
