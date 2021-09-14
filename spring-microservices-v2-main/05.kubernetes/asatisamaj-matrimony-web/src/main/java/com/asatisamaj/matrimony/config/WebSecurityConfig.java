/*
 * package com.asatisamaj.matrimony.config;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Qualifier; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.authentication.AuthenticationManager; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity public class WebSecurityConfig extends
 * WebSecurityConfigurerAdapter {
 * 
 * @Qualifier("userDetailsServiceImpl")
 * 
 * @Autowired private UserDetailsService userDetailsService;
 * 
 * @Bean public BCryptPasswordEncoder bCryptPasswordEncoder() { return new
 * BCryptPasswordEncoder(); }
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception { http
 * .authorizeRequests() .antMatchers("/resources/**",
 * "/registration").permitAll() .anyRequest().authenticated() .and()
 * .formLogin() .loginPage("/login") .permitAll() .and() .logout() .permitAll();
 * }
 * 
 * @Bean public AuthenticationManager customAuthenticationManager() throws
 * Exception { return authenticationManager(); }
 * 
 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
 * throws Exception {
 * auth.userDetailsService(userDetailsService).passwordEncoder(
 * bCryptPasswordEncoder()); }
 * 
 * @Override public void configure(WebSecurity web) {
 * web.ignoring().antMatchers( // Vaadin Flow static resources "/VAADIN/**",
 * 
 * // the standard favicon URI "/favicon.ico",
 * 
 * // the robots exclusion standard "/robots.txt",
 * 
 * // web application manifest "/manifest.webmanifest", "/sw.js",
 * "/offline-page.html",
 * 
 * // icons and images "/icons/**", "/images/**",
 * 
 * // (development mode) static resources "/frontend/**",
 * 
 * // (development mode) webjars "/webjars/**",
 * 
 * // (production mode) static resources "/frontend-es5/**",
 * "/frontend-es6/**"); }
 * 
 * }
 */