package com.townhall.discourse.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthEntryPoint userAuthEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthEntryPoint))
                .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/users/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/posts/{uId}").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/delete/{pId}").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/addPost").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/addComment").authenticated()
                                .requestMatchers(HttpMethod.GET, "/tokenStatus").authenticated()
                                .anyRequest().permitAll())
//                .authorizeHttpRequests((requests) -> requests
//                        .anyRequest().permitAll())
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // Specify the logout URL
                                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()) // Handle successful logout with HTTP status
                                .permitAll() // Allow anyone to access the logout URL
                )

        ;
        return http.build();
    }
}
