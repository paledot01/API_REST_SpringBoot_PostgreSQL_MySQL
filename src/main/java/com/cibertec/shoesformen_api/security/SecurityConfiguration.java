package com.cibertec.shoesformen_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // si no lo anoto en alguna de mis clases la aplicacion pedirá nombre de usuario y contraseña
public class SecurityConfiguration {

    @Autowired
    private EmpleadoDetailsService empleadoDetails;

    @Bean
    public PasswordEncoder encriptador() {
        // return NoOpPasswordEncoder.getInstance(); --> este codigo permite leer contraseñas sin codificar.
        return new BCryptPasswordEncoder(); // --> valida que las constraseñas sean del tipo BCryptPasswordEncoder
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // Tenga en cuenta que el orden de los elementos antMatchers() es significativo; las reglas más
        // específicas deben ir primero, seguidas de las más generales(permitAll)
        http.httpBasic().and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/empleados").hasAnyRole("ADMIN") // solo los ADMIN pueden utilizar los POST, PUT, DELETE
                .requestMatchers(HttpMethod.PUT,"/empleados/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/empleados/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/empresa/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/empleados").hasAnyRole("ADMIN","USER") // tanto los USER y ADMIN pueden utilizar los GET
                .requestMatchers(HttpMethod.GET,"/empleados/**").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.GET,"/distritos").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.GET,"/empresa").hasAnyRole("ADMIN","USER")
                .requestMatchers("/").hasAnyRole("ADMIN","USER")
                .requestMatchers("/img/**","/js/**","/css/**").permitAll()
//                .and().formLogin().successHandler(sucessHandler).loginPage("/login").loginProcessingUrl("/login")
//                .defaultSuccessUrl("/",true).permitAll()
//                .and().logout().clearAuthentication(true).invalidateHttpSession(true).logoutSuccessUrl("/login?logout").permitAll() //.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).deleteCookies("JSESSIONID") // url[logout es el parametro que se envia] - para que no invalide la session - eliminar cookies.
                .and().csrf().disable(); // <-------- CSRF bloquea los metodos POST, y esta habilitado por defecto
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(empleadoDetails);
        auth.setPasswordEncoder(encriptador());
        return auth;
    }

}
