package com.shopme.admin.security;

import com.shopme.admin.paging.PagingAndSortingArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {
    @Bean
    public UserDetailsService userDetailsService(){
        return new OverclockedUserDetailsService();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .requestMatchers("/images/**","/js/**","/webjars/**","/css/**","/img/**","/assets/**","/richtext/**").permitAll()
                .requestMatchers("/users/**","/settings/**", "/countries/**", "/states/**").hasAuthority("Admin")
                .requestMatchers("/categories/**","/sections/**").hasAnyAuthority("Admin","Editor")
                .requestMatchers("/brands/**").hasAnyAuthority("Admin","Editor")
                .requestMatchers("/products/**").hasAnyAuthority("Admin","Editor","Salesperson","Shipper")
                .requestMatchers("/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Editor")
                .requestMatchers("/customers/**","/orders/**", "/get_shipping_cost" ,"/reports/**").hasAnyAuthority("Admin","Salesperson")
                .requestMatchers("/products/edit/**", "/products/save", "/products/check_unique")
                .hasAnyAuthority("Admin", "Editor", "Salesperson")
                .requestMatchers("/orders", "/orders/", "/orders/page/**", "/orders/detail/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
                .requestMatchers("/products", "/products/", "/products/detail/**", "/products/page/**")
                .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                .requestMatchers("/states/list_by_country/**").hasAnyAuthority("Admin", "Salesperson")
                .requestMatchers("/orders_shipper/update/**").hasAnyAuthority("Shipper")
                .requestMatchers("/products/**").hasAnyAuthority("Admin", "Editor")
                .anyRequest().authenticated();
        http.formLogin(formLogin->
                formLogin.loginPage("/login")
                        .usernameParameter("email")
                         .permitAll()
        );
        http.logout((logout) ->
                        logout.deleteCookies("remove")
                                .invalidateHttpSession(true)
                                .permitAll());

//        http.rememberMe(rememberMe->rememberMe
//                .key("AddlajlaAakha1291898q_jajd")
//                .tokenValiditySeconds(7*24*60*60));

        http.authenticationProvider(authenticationProvider());
        http.headers().frameOptions().sameOrigin();
        return http.build();


    }
//    @Bean
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PagingAndSortingArgumentResolver());
    }
}
