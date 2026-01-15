/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vPerez.ProgramacionNCapasNov2025.service.UserDetailsService;

/**
 *
 * @author digis
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private UserDetailsService userDetailsService;

    public SpringSecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config -> config.requestMatchers("/Usuario/**","/login")//Rutas a las que se accede sin contraseña(las rutas despues de la principal)
                .hasAnyRole("Administrador","Usuario").anyRequest().authenticated()
        )
                .formLogin(
                        form -> form
                                .defaultSuccessUrl("/Usuario", true)
                                .usernameParameter("email")
                                .loginPage("/login")//Url del endpoint de la pagina
                                .loginProcessingUrl("/signin")//endpoint para procesar formulario() manejado por sprinf security(Es lo mismo que se escribe en el formulario)
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
//                .logout(
//                        logout -> logout
//                                .logoutUrl("/logout")
//                                .logoutSuccessUrl("/login?logout=true")
//                                .permitAll()
//                )
                .userDetailsService(userDetailsService);

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
