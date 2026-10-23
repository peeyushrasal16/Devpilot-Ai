package com.devpilot.ai.security;

import com.devpilot.ai.security.jwt.AuthEntryPointJwt;
import com.devpilot.ai.security.jwt.AuthTokenFilter;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthTokenFilter authTokenFilter;
    private final AuthEntryPointJwt authEntryPointJwt;

    SecurityConfig(
            AuthEntryPointJwt authEntryPointJwt,
            AuthTokenFilter authTokenFilter) {

        this.authEntryPointJwt = authEntryPointJwt;
        this.authTokenFilter = authTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChaiin(
            HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(request -> request

                .requestMatchers(
                        HttpMethod.POST,
                        "/users/register",
                        "/auth/login"
                )
                .permitAll()

                .requestMatchers(
                        HttpMethod.GET,
                        "/users/check-email"
                )
                .permitAll()

                // Added: Allow ADMIN, PROJECT_MANAGER, and DEVELOPER to get users list for dropdown assignment
                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "PROJECT_MANAGER", "DEVELOPER")
                
//.requestMatchers(HttpMethod.POST, "/users/register").permitAll()
//                
//.requestMatchers(HttpMethod.GET, "/users/check-email").permitAll()
                
                .requestMatchers("/users/**")
                .hasRole("ADMIN")

                // change the code: allow ADMIN and PROJECT_MANAGER to delete projects; DEVELOPER cannot delete
                .requestMatchers(HttpMethod.DELETE, "/projects", "/projects/**")
                .hasAnyRole("ADMIN", "PROJECT_MANAGER")

                .requestMatchers("/projects", "/projects/**")
                .hasAnyRole("ADMIN", "PROJECT_MANAGER", "DEVELOPER")

                .requestMatchers("/project-members", "/project-members/**")
                .hasAnyRole("ADMIN", "PROJECT_MANAGER")

                // change the code: allow ADMIN and PROJECT_MANAGER to delete tasks; DEVELOPER cannot delete
                .requestMatchers(HttpMethod.DELETE, "/tasks", "/tasks/**")
                .hasAnyRole("ADMIN", "PROJECT_MANAGER")

                .requestMatchers(HttpMethod.GET, "/tasks", "/tasks/**")
                .hasAnyRole("ADMIN", "PROJECT_MANAGER", "DEVELOPER")

                .requestMatchers(HttpMethod.POST, "/tasks", "/tasks/**")
                .hasAnyRole("ADMIN", "PROJECT_MANAGER", "DEVELOPER")

                .requestMatchers(HttpMethod.PUT, "/tasks", "/tasks/**")
                .hasAnyRole("ADMIN", "PROJECT_MANAGER", "DEVELOPER")

                .requestMatchers("/bugs", "/bugs/**")
                .hasAnyRole(
                        "ADMIN",
                        "PROJECT_MANAGER",
                        "DEVELOPER"
                )

                .requestMatchers("/documentation", "/documentation/**")
                .hasAnyRole(
                        "ADMIN",
                        "PROJECT_MANAGER",
                        "DEVELOPER"
                )

                // change the code: allow access to /task-assignments and /task-assignments/** for ADMIN, PROJECT_MANAGER, DEVELOPER
                .requestMatchers("/task-assignments", "/task-assignments/**")
                .hasAnyRole(
                        "ADMIN",
                        "PROJECT_MANAGER",
                        "DEVELOPER"
                )

                .requestMatchers("/activity-logs", "/activity-logs/**")
                .hasAnyRole(
                        "ADMIN",
                        "PROJECT_MANAGER"
                )

                .requestMatchers("/notifications", "/notifications/**")
                .authenticated()

                .anyRequest()
                .authenticated()
            );

        http.exceptionHandling(authentication ->
                authentication.authenticationEntryPoint(
                        authEntryPointJwt
                )
        );

        http.addFilterBefore(
                authTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        return authenticationConfiguration
                .getAuthenticationManager();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration config =new CorsConfiguration();
//    	config.setAllowedOrigins(List.of("http:localhost:5173","http:localhost:5174","http://localhost:5175"));
    	config.setAllowedOrigins(List.of(
    		    "http://localhost:5173",
    		    "http://localhost:5174",
    		    "http://localhost:5175"
    		));
    	config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
    	config.setAllowedHeaders(List.of("*"));
    	config.setAllowCredentials(true);
    	
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", config);
    	return source;
    }
}

