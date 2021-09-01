package ars.cs.miu.edu.authentication;

import ars.cs.miu.edu.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailService authenticationUserDetailService;

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
                //remove csrf and state in session because in jwt we don't need them
                .cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //add jwt filters (1. authentication, 2. authorization)
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .authorizeRequests()

                .antMatchers("/addresses/**","/airports/**","/airlines").permitAll()
                //configure access rules for AGENT
                .antMatchers(HttpMethod.GET,"/agents","/flights","/flights/**","/airlines","/airlines/**","/passengers/{id}",  "/reservations/byAgent/**","/tickets/**")
                .hasAnyAuthority(Role.AGENT.toString(),Role.ADMIN.toString())
                .antMatchers(HttpMethod.POST,"/reservations/**","/tickets/**").hasAnyAuthority(Role.AGENT.toString())
                .antMatchers(HttpMethod.PUT,"/reservations/**").hasAnyAuthority(Role.AGENT.toString())
                .antMatchers(HttpMethod.DELETE,"/reservations/**").hasAnyAuthority(Role.AGENT.toString())

                //configure access rules for PASSENGER
                .antMatchers(HttpMethod.GET,"/passengers/airlines/**","/passengers/{id}/reservations","/reservations/**","/tickets/**","/agents","/agents/**","/flights","/flights/**",
                        "/passengers/flights/**","/airlines","/airports","/airports/**")
                .hasAnyAuthority(Role.PASSENGER.toString())
                .antMatchers(HttpMethod.POST,"/reservations/**","/tickets/**").hasAnyAuthority(Role.PASSENGER.toString())
                .antMatchers(HttpMethod.PUT,"/reservations/**").hasAnyAuthority(Role.PASSENGER.toString())
                .antMatchers(HttpMethod.DELETE,"/reservations/**").hasAnyAuthority(Role.PASSENGER.toString())
                .antMatchers( "/**").hasAnyAuthority(Role.ADMIN.toString())
                .anyRequest().authenticated();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }
}
