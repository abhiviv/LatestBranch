package com.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static com.management.security.Constants.LOGIN_URL;

import com.management.security.JWTAuthenticationFilter;
import com.management.security.JWTAuthorizationFilter;
import com.management.security.JWTUserDetailsService;


@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	public WebSecurityConfig(JWTUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

     @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {		
	  	 auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	  	
	
	
   	@Bean
        public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public JwtAuthenticationEntryPoint accessDeniedHandler(){
	    return new JwtAuthenticationEntryPoint();
	}

	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable().
		requestMatchers().antMatchers("/web/**")
		.and().logout().logoutUrl("/web/logout")
		.and().authorizeRequests()
        	.antMatchers( 
        			"/h2/**", "/console/**","/actuator/**","/swagger-ui.html","/public/**")
        	.permitAll().antMatchers(HttpMethod.POST,"/web/registration")
        	.permitAll().
        	antMatchers(HttpMethod.GET,"/web/Dublicate/**","/web/location/**")
        	.permitAll()
        .anyRequest().authenticated()
        .and().exceptionHandling().authenticationEntryPoint(accessDeniedHandler()).and()
        .addFilterBefore(new JWTAuthenticationFilter(LOGIN_URL,authenticationManager()), FilterSecurityInterceptor.class)
        .addFilterBefore(new JWTAuthorizationFilter(authenticationManager()), FilterSecurityInterceptor.class)
        // this disables session creation on Spring Security
    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	  @Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	  }
	  
}