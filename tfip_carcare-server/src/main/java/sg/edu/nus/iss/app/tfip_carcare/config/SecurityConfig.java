package sg.edu.nus.iss.app.tfip_carcare.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import sg.edu.nus.iss.app.tfip_carcare.filters.JWTTokenGeneratorFilter;
import sg.edu.nus.iss.app.tfip_carcare.filters.JWTTokenValidatorFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // csrf enable means cannot post or put
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf"); // default is also "_csrf"
        // 1. disable Jsession to enable JWT
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// never make any JSessionID
        // 2. enable cors
        http.cors().configurationSource(new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOriginPatterns(Collections.singletonList("*")); // front end origin
                config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE")); // GET,POST,PUT,PATCH,OPTIONS,DELETE
                config.setAllowCredentials(true); // true if using Authorization header
                config.setAllowedHeaders(Collections.singletonList("*")); // what headers can we accept from frontend
                config.setExposedHeaders(Arrays.asList("Authorization")); // what headers can we send to frontend
                config.setMaxAge(3600L); // set expiration
                return config;
            }
        });
        // 3. configure CSRF + JWT Filter + authorization level
        http.csrf().disable()
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/api/myAccount","/api/car/**","/api/login","/api/item/**","/api/maintenance/**","/api/payment/**","api/customer/**","api/mq/**").authenticated()
                .requestMatchers("/api/contactUs", "/api/register").permitAll()
                .and().formLogin()
                .and().httpBasic();

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Below is for table that is defined by Spring security.
    // If want custom credential, use customUserDetailsService
    /*
     * @Bean
     * public UserDetailsService userDetailsService(DataSource dataSource){
     * return new JdbcUserDetailsManager(dataSource);
     * }
     */
}
