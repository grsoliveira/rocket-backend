package br.com.grso.api.security;

import br.com.grso.domain.exception.ExceptionDetails;
import br.com.grso.domain.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JwtHelper jwtHelper, AuthenticationManager authenticationManager) {
        this.jwtHelper = jwtHelper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserToAuth user = new ObjectMapper().readValue(request.getInputStream(), UserToAuth.class);
            UsernamePasswordAuthenticationToken springAuthToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return this.authenticationManager.authenticate(springAuthToken);
        } catch (IOException exception) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((Usuario) authResult.getPrincipal()).getUsername();
        String jwtToken = this.jwtHelper.generateToken(username);
        response.addHeader("Authorization", "Bearer " + jwtToken);
        this.returnTokenWithResponseBody(request, response, "Bearer " + jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed instanceof BadCredentialsException) {
            this.generateResponse(request, response, "Usuário ou senha incorreta");
        }
    }

    private void generateResponse(HttpServletRequest request, HttpServletResponse response, String cause) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        response.setStatus(401);
        response.setContentType("application/json");
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), 401, cause, request.getServletPath());
        response.getWriter().print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(exceptionDetails));
        response.getWriter().flush();
    }

    private void returnTokenWithResponseBody(HttpServletRequest request, HttpServletResponse response, String token) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(200);
        response.setContentType("application/json");
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        response.getWriter().print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
        response.getWriter().flush();
    }
}
