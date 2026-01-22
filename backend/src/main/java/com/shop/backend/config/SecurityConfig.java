package com.shop.backend.config;

// import com.shop.backend.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    // 1. Mã hóa mật khẩu (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            UserDetailsService userDetailsService
    ) throws Exception {

        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }

    // 4. Cấu hình các quy tắc bảo mật (Bộ lọc)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Tắt CSRF để test API dễ hơn
            .cors(cors -> cors.disable()) // Tắt CORS mặc định để dùng cấu hình Custom bên dưới
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Không lưu session (vì dùng Token)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Cho phép Login/Register thoải mái
                .requestMatchers("/api/parking-slots/**").permitAll() // Cho phép xem bãi đỗ xe không cần login
                .anyRequest().authenticated() // Còn lại phải đăng nhập mới được vào
            );
        
        return http.build();
    }
    
    // 5. Cấu hình CORS (Cho phép Frontend gọi vào)
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // Cho phép tất cả các nguồn (localhost:3000, mobile...)
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}