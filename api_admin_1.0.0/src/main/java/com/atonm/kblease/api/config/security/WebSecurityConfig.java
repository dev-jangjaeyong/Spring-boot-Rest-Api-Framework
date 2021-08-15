package com.atonm.kblease.api.config.security;

import com.atonm.kblease.api.common.mapper.UserMapper;
import com.atonm.kblease.api.common.mapper.UserTokenMapper;
import com.atonm.kblease.api.config.property.AdditionalProperty;
import com.atonm.kblease.api.config.security.custom.CustomAccessDeniedHandler;
import com.atonm.kblease.api.config.security.custom.CustomAuthenticationEntryPoint;
import com.atonm.kblease.api.config.security.custom.CustomUserDetailsService;
import com.atonm.kblease.api.log.mapper.LogMapper;
import com.atonm.kblease.api.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * @author jang jea young
 * @since 2018-07-30
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${role.hierarchy}")
    private String roleHierarchy;

    @Autowired
    UserMapper userMapper;

    @Autowired
    LogMapper logMapper;

    @Autowired
    UserJpaRepository userJpaRepository;

    /*@Autowired
    UserTokenRepository userTokenRepository;*/

    @Autowired
    UserTokenMapper userTokenMapper;

    @Autowired
    AdditionalProperty props;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SECURITY_IGNORING_URL_PATTERN);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/docs/**").permitAll()
                //외부시스템에서 API에 접근할때의 인증방식처리
                .antMatchers(HttpMethod.GET, "/excel/**").permitAll()
                .antMatchers("/common-code/**").permitAll()
                .antMatchers("/ext/**").permitAll()
                .antMatchers("/menu-url/**").permitAll()

                // 딜러 계정생성
                .antMatchers("/user/account").permitAll()
                .accessDecisionManager(accessDecisionManager())
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                                    .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilterBefore(new JWTLoginFilter("/user/login", authenticationManager(), userMapper, logMapper, userJpaRepository, userTokenMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(userTokenMapper),
                        UsernamePasswordAuthenticationFilter.class)
                .cors();
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);

        return daoAuthenticationProvider;
    }

    @Bean
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userJpaRepository);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    AccessDecisionManager accessDecisionManager() {
        // role 계층구조 설정
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        /*roleHierarchy.setHierarchy(String.valueOf(roleHierarchy));*/
        roleHierarchy.setHierarchy("ROLE_SUPER > ROLE_ADMIN > ROLE_KB_ADMIN > ROLE_KB_USER > ROLE_USER");

        // handler
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);

        // voter
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(defaultWebSecurityExpressionHandler);

        // voter 리스트
        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(
                webExpressionVoter
        );

        // AccessDecisionManager
        AccessDecisionManager accessDecisionManager = new AffirmativeBased(voters);
        return accessDecisionManager;
    }

    public static final String[] SECURITY_IGNORING_URL_PATTERN = {
            "/",
            "/v2/api-docs",           // swagger
            "/webjars/**",            // swagger-ui webjars
            "/swagger-resources/**",  // swagger-ui resources
            "/configuration/**",      // swagger configuration
            "/*.html",
            "/**/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/docs/**",
            "/downloadFile/**",
            "/**/*.bmp", "/**/*.gif", "/**/*.jpg", "/**/*.png",
            "/upload/**",
            "/external/**"
    };
}
