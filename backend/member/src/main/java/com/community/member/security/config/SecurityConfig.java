package com.community.member.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * 시큐리티 설정
         */


        //Swagger UI
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/v3/**","/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll())
        //CSRF 비활성화
            .csrf(AbstractHttpConfigurer::disable)

                .formLogin(AbstractHttpConfigurer::disable)
        //로그아웃 시 main 페이지 이동
            .logout(logout -> logout.logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID"))
        //세션 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //test를 위한 모든 사용자 권한
//            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        //관리자 페이지와 게시글 작성에 필요한 인증 요구
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasAuthority("ADMIN")                       //admin 페이지는 ADMIN 권한만 접근가능
                .requestMatchers("/document/manage").hasAnyAuthority("ADMIN", "USER")  //document 페이지는(글 작성 페이지?) 인증한 사람만(회원)
                .requestMatchers("/report/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET,"/document/manage").authenticated()              //GET 예시
                .anyRequest().permitAll());                                                 //이외에 모든 접근은 ROLE_ANONYMOUS 도 가능

        // 익명 사용자에게 부여될 권한 // 익명 사용자의 principal 이름 정의 (기본값은 'anonymousUser')
        http.anonymous( any -> any.authorities("ANONYMOUS").principal("ANONYMOUS_USER"));

        //cors 설정
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(configurationSource());
        });

        return http.build();
    }

    //CORS 설정
    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("--------------------------web configure----------------------------");
        return (web) -> web.ignoring().requestMatchers("/v3/**","/static/**","/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
