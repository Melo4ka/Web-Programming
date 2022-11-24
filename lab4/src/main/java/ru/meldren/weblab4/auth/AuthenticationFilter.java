package ru.meldren.weblab4.auth;

import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import ru.meldren.weblab4.dao.UserRepository;

import java.net.URI;

import static ru.meldren.weblab4.auth.AuthenticationStatus.INVALID_TOKEN;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @EJB
    private UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext context) {
        try {
            String token = context.getCookies().get("token").getValue();

            if (!validateToken(token))
                throw new AuthenticationException(INVALID_TOKEN);
        } catch (Exception ex) {
            context.abortWith(Response
                    .seeOther(URI.create("/auth"))
                    .build()
            );
        }
    }

    private boolean validateToken(String token) {
        return token != null && userRepository.find("token", token).size() > 0;
    }
}