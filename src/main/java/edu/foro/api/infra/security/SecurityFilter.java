package edu.foro.api.infra.security;
import edu.foro.api.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
          // Obtener el token del header
        //var authHeader = request.getHeader("Authorization");
        var authHeader = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJDYXJsb3MuR29tZXoiLCJpc3MiOiJmb3JvIGFwaSIsImlkIjoxfQ.wiq8FkG3dyoDiJVpbcpAxGhfEH-9YYHZOxlyv4_sHv0";

        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "").trim();
            var username = tokenService.getSubject(token); // extract username
            if (username!= null) {
                // Token valido
                var user = userRepository.findByLogin(username);
                var authentication = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities()); // Forzamos un inicio de sesión
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            System.out.println("Token doFilterInternal not valid " + authHeader);
        }
        filterChain.doFilter(request, response);
    }
}