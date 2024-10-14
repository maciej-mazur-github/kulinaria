package com.kulinaria.security.config;

import jakarta.servlet.DispatcherType;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain websiteFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);
        http.requestCache((cache) -> cache.requestCache(requestCache));

        http.authorizeHttpRequests(requests -> requests
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                .requestMatchers(
                        mvc.pattern(HttpMethod.GET,"/"),
                        mvc.pattern(HttpMethod.GET, "/kategorie"),
                        mvc.pattern(HttpMethod.GET, "/kategorie/*"),
                        mvc.pattern(HttpMethod.GET, "/kategorie/*/przepis/*"))
                .permitAll()
                .requestMatchers(
                        mvc.pattern(HttpMethod.GET, "/przepis/dodaj"),
                        mvc.pattern(HttpMethod.GET, "/kategorie/*/przepis/*/edycja"),
                        mvc.pattern(HttpMethod.GET, "/kategorie/*/przepis/*/edycja/*"),
                        mvc.pattern(HttpMethod.POST, "/zapisz-zdjecie"),
                        mvc.pattern(HttpMethod.POST, "/glosuj"))
                .authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                .requestMatchers(
                        mvc.pattern(HttpMethod.GET, "/h2-console"),
                        mvc.pattern(HttpMethod.GET, "/h2-console/**")
                )
                .permitAll()
                .anyRequest().authenticated());

        http.formLogin(login -> login.loginPage("/zaloguj").permitAll());
        http.logout(logout ->
                logout.logoutRequestMatcher(new AntPathRequestMatcher("/wyloguj/**", HttpMethod.GET.name()))
                        .logoutSuccessUrl("/"));
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .ignoringRequestMatchers(
                        mvc.pattern(HttpMethod.GET, "/h2-console"),
                        mvc.pattern(HttpMethod.GET, "/h2-console/**"))
        );

        http.formLogin(login -> login.loginPage("/zaloguj").permitAll())
                .logout(logout ->
                    logout.logoutRequestMatcher(
                            new AntPathRequestMatcher("/wyloguj/**", HttpMethod.GET.name()))
                        .logoutSuccessUrl("/zaloguj?logout").permitAll())
                .headers(config ->
                config.frameOptions(options ->
                        options.sameOrigin()))
                .exceptionHandling(exception -> exception.accessDeniedHandler(new CustomAccessDeniedHandler()));

        DefaultSecurityFilterChain build = http.build();

        return build;
    }

    @Bean
    WebSecurityCustomizer configureWebSecurity(MvcRequestMatcher.Builder mvc) {
        return (web) -> web.ignoring().requestMatchers(
                mvc.pattern(HttpMethod.GET, "/style.css"),
                mvc.pattern(HttpMethod.GET, "/img/**"),
                mvc.pattern(HttpMethod.GET, "/recipes/**"),
                mvc.pattern(HttpMethod.GET, "/categories/**"),
                PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
