package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    final
    MemberService memberService;

    public SecurityConfig(MemberService memberService) {
        this.memberService = memberService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");

        http
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository
                        .withHttpOnlyFalse());

        /*http.authorizeRequests()
                .mvcMatchers("/css/**","/js/**","/img/**","/assets/**","/pages/**","/scss/**").permitAll()
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenicationEntryPoint());*/

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//
////  WebSecurityConfigurerAdapter 를 상속 받는 클래스에 @EnableWebSecurity 어노테이션을 선언하면
////  SpringSecurityFilterChain 이 자동으로 포함된다. WebSecurityConfigurerAdapter 를 상속 받아서
////  메서드 오버라이딩을 통해 보안 설정을 커스터마이징 할 수 있다.
////  deprecated 이슈 확인 필요
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception { // http 요청에 대한 보안 설정
//
//        http
//                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository
//                        .withHttpOnlyFalse());
//
//        http
//                .authorizeRequests()
//                .antMatchers("/h2-console/*")
//                .permitAll();
//
////        h2 db 접근시
////        http
////                .csrf()
////                .disable();
//
//        http
//                .headers()
//                .frameOptions()
//                .disable();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {  // 비밀번호 암호화
//        return new BCryptPasswordEncoder();
//    }
//}
