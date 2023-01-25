package br.com.grso.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordHelper passwordHelper;
    private final UserDetailsService detailsService;
    private final JwtHelper jwtHelper;


    public SecurityConfig(PasswordHelper passwordHelper, UserDetailsService detailsService, JwtHelper jwtHelper) {
        this.passwordHelper = passwordHelper;
        this.detailsService = detailsService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.detailsService).passwordEncoder(this.passwordHelper.getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JwtAuthenticationFilter(this.jwtHelper, authenticationManager()));
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(), this.jwtHelper, this.detailsService));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of("GET", "PUT", "DELETE", "POST", "HEAD", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedOriginPatterns(List.of("*"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
