package com.timelymd.timelymd_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF since we're using JWTs
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()  // Public endpoints
                        .requestMatchers("/api/owner/**").hasAuthority("OWNER")
                        .requestMatchers("/api/doctor/**").hasAnyAuthority("DOCTOR", "OWNER")
                        .requestMatchers("/api/staff/**").hasAnyAuthority("STAFF", "DOCTOR", "OWNER")
                        .requestMatchers("/api/patient/**").hasAnyAuthority("PATIENT", "DOCTOR", "STAFF", "OWNER")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String jwksUrl = System.getenv("SUPABASE_JWKS_URL");
        if (jwksUrl == null) {
            throw new IllegalStateException("SUPABASE_JWKS_URL environment variable not set");
        }
        return NimbusJwtDecoder.withJwkSetUri(jwksUrl).build();
    }
}
