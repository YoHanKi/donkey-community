package com.community.config;

import com.community.repository.MemberRepository;
import com.community.security.filter.APILoginFilter;
import com.community.security.MemberDetailsService;
import com.community.security.filter.JwtAuthenticationFilter;
import com.community.security.filter.RefreshTokenFilter;
import com.community.security.filter.TokenCheckFilter;
import com.community.security.handler.APILoginFailureHandler;
import com.community.security.handler.APILoginSuccessHandler;
import com.community.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
public class SecurityConfig {

    private final MemberDetailsService memberDetailsService;
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    public SecurityConfig(MemberDetailsService memberDetailsService, MemberRepository memberRepository, JWTUtil jwtUtil) {
        this.memberDetailsService = memberDetailsService;
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /**
         * JWT 설정
         */

        // AuthenticationManager 설정
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        // 유저 인증 관련 설정
        authenticationManagerBuilder
                .userDetailsService(memberDetailsService)
                        .passwordEncoder(passwordEncoder());

        // AuthenticationManager 빌드
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        // 설정 저장(필수)
        http.authenticationManager(authenticationManager);

        // LoginFilter --> /generateToken(변경 -> /api/login)를 호출하면 Login Filter가 실행
        APILoginFilter apiLoginFilter = new APILoginFilter("/api/login");
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        // 필터 적용 위치 조정
        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);


        // LoginSuccessHandler -> 로그인 성공 시
        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil);
        // LoginFilter --> 로그인 성공 시 successHandler로 이동
        apiLoginFilter.setAuthenticationSuccessHandler(successHandler);

        // failureHandler -> 로그인 실패 시
        APILoginFailureHandler failureHandler = new APILoginFailureHandler();
        // failureHandler -> 로그인 실패 시 failureHandler로 이동
        apiLoginFilter.setAuthenticationFailureHandler(failureHandler);

        // TokenCheckFilter --> "/document" 로 시작하는 모든 동작은 해당 필터 동작
        http.addFilterBefore(new JwtAuthenticationFilter(memberRepository,jwtUtil), UsernamePasswordAuthenticationFilter.class);


        http.addFilterBefore(tokenCheckFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // refreshToken 호출
        http.addFilterBefore(new RefreshTokenFilter("/refreshToken", jwtUtil), TokenCheckFilter.class);



        /**
         * 시큐리티 설정
         */

        //Swagger UI
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/v3/**","/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll())
        //CSRF 비활성화
            .csrf(AbstractHttpConfigurer::disable)
        //로그인 성공 이후 main 페이지 이동
//            .formLogin(login -> login.defaultSuccessUrl("/"))
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
        configuration.addAllowedOrigin("http://172.31.5.82:3000");
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    //토큰체인필터
    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil) {
        return new TokenCheckFilter(jwtUtil);
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
