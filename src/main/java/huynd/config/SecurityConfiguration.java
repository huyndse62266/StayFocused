package huynd.config;

import huynd.security.*;
import huynd.security.jwt.*;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService, TokenProvider tokenProvider, CorsFilter corsFilter, SecurityProblemSupport problemSupport) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/swagger-ui/ListStoreGroup.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
        .and()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/users/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/users/find-role").permitAll()
            .antMatchers("/api/users").permitAll()
            .antMatchers("/api/users/{login}").permitAll()
            .antMatchers("/api/users/point/{userID}").permitAll()
            .antMatchers("/api/store-types").permitAll()
            .antMatchers("/api/store-types/{id}").permitAll()
            .antMatchers("/api/store-groups/{id}").permitAll()
            .antMatchers("/api/store-groups").permitAll()
            .antMatchers("/api/store-groups/store-types/{id}").permitAll()
            .antMatchers("/api/posts").permitAll()
            .antMatchers("/api/posts/{id}").permitAll()
            .antMatchers("/api/posts/store-groups/{storeGroupID}").permitAll()
            .antMatchers("/api/posts/store-groups/store-type/{storeTypeID}").permitAll()
            .antMatchers("/api/stores").permitAll()
            .antMatchers("/api/stores/{id}").permitAll()
            .antMatchers("/api/stores/location/{id}").permitAll()
            .antMatchers("/api/stores/store-groups/{storeGroupID}").permitAll()
            .antMatchers("/api/vouchers/get-voucher-number/").permitAll()
            .antMatchers("/api/vouchers/posts/{postID}").permitAll()
            .antMatchers("/api/vouchers/store-groups/{storeGroupID}").permitAll()
            .antMatchers("/api/voucher-logs").permitAll()
            .antMatchers("/api/voucher-logs/{id}").permitAll()
            .antMatchers("/api/voucher-logs/users/{username}").permitAll()
            .antMatchers("/api/activities-logs").permitAll()
            .antMatchers("/api/activities-logs/{id}").permitAll()
            .antMatchers("/api/activities-logs/users/total-point/{username}").permitAll()
            .antMatchers("/api/activities-logs/users/total-time/{username}").permitAll()
            .antMatchers("/api/point-logs").permitAll()
            .antMatchers("/api/point-logs/{id}").permitAll()
            .antMatchers("/api/point-logs/users/{username}").permitAll()
            .antMatchers("/api/point-logs/users/total-point/{username}").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/users/register-without-password").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .and()
            .apply(securityConfigurerAdapter());

    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
