package br.com.grso.api.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtHelper jwtHelper, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken springAuthToken = this.getAuthentication(authorizationHeader.substring(7));
            if (springAuthToken != null) {
                SecurityContextHolder.getContext().setAuthentication(springAuthToken);
            }
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String rawJwtToken) {
        if (this.jwtHelper.isTokenValid(rawJwtToken)) {
            String username = this.jwtHelper.extractUsernameFromToken(rawJwtToken);
            UserDetails user = this.userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }
}
