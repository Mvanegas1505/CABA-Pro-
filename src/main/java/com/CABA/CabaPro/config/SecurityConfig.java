package com.CABA.CabaPro.config;

import com.CABA.CabaPro.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UsuarioService usuarioService,
            PasswordEncoder passwordEncoder) {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String correo = authentication.getName();
                String contrasena = authentication.getCredentials().toString();

                var usuario = usuarioService.findByCorreo(correo);
                if (usuario == null) {
                    return null;
                }
                if (usuario.getContrasena() == null) {
                    return null;
                }
                if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                    return null;
                }

                List<GrantedAuthority> authorities = new ArrayList<>();
                if (usuario.getRol() != null) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
                } else {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ARBITRO"));
                }

                return new UsernamePasswordAuthenticationToken(correo, usuario.getContrasena(), authorities);
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(UsuarioService usuarioService) {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                String correo = authentication.getName();
                var usuario = usuarioService.findByCorreo(correo);
                if (usuario != null) {
                    request.getSession().setAttribute("usuario", usuario);
                    if (usuario.getRol() != null && usuario.getRol().name().equals("ADMIN")) {
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                        return;
                    }
                }
                response.sendRedirect(request.getContextPath() + "/arbitro/dashboard");
            }
        };
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider,
            com.CABA.CabaPro.security.JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/partidos/**", "/api/torneos/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider,
            AuthenticationSuccessHandler successHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/h2-console/**",
                                "/login",
                                "/registro",
                                "/perfil",
                                "/",
                                "/home",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .usernameParameter("correo")
                        .passwordParameter("contrasena")
                        .successHandler(successHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());

        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}
