package com.zindabad.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/users/userRole/**").hasRole("ADMIN")

                // .antMatchers("/buses/list").hasRole("ADMIN")
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/testing").permitAll()
                .antMatchers("/resources/static/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/").permitAll()

                .antMatchers("/users/register").permitAll()
                .antMatchers("/contact us").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/trips/availableTrips").permitAll()
                .antMatchers("/bookings/bookTrip/**").permitAll()
                .antMatchers("/bookings/process").permitAll()

                .antMatchers("/users/create").permitAll()
                .antMatchers("/category/create").permitAll()
                .antMatchers("/product/create").permitAll()
                .antMatchers("/products").permitAll()
                .antMatchers("/product/**").permitAll()
                .antMatchers("/about us").permitAll()
                .antMatchers("/js/sweetalert2.all.min").permitAll()
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                //.loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/", true)
                //.failureUrl("/login.html?error=true")
                //.failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                //.logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID");
        //.logoutSuccessHandler(logoutSuccessHandler());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/static/**", "/fontawesome/**", "/css/**", "/images/**", "/jquery/**", "/js/**");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
