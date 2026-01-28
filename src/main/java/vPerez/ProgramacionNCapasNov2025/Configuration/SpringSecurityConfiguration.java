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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import vPerez.ProgramacionNCapasNov2025.service.CustomFailureHandler;
import vPerez.ProgramacionNCapasNov2025.service.CustomSuccessHandler;
import vPerez.ProgramacionNCapasNov2025.service.UserDetailsService;

/**
 *
 * @author digis
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private UserDetailsService userDetailsService;
    private CustomFailureHandler customFailureHandler;
    private CustomSuccessHandler customSuccessHandler;
            
    public SpringSecurityConfiguration(UserDetailsService userDetailsService,CustomFailureHandler customFailureHandler, CustomSuccessHandler customSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.customFailureHandler = customFailureHandler;
        this.customSuccessHandler = customSuccessHandler;
    }
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config -> config.requestMatchers("/login/**","/Usuario/getMunicipioByEstado/**","/Usuario/getEstadoByPais/**","/Usuario/getColoniaByMunicipio/**","/Usuario/direccionForm/**").permitAll()               
                .requestMatchers("/Usuario/detail")
                .hasAnyRole("Usuario")
                .requestMatchers("/Usuario/**")//Rutas a las que se accede sin contraseña(las rutas despues de la principal)
                .hasAnyRole("Administrador")
                .anyRequest().authenticated()
        )
                .formLogin(
                        form -> form
                                .successHandler(customSuccessHandler)
                                .failureHandler(customFailureHandler)
                                .usernameParameter("email")
                                .loginPage("/login")//Url del endpoint de la pagina
                                .loginProcessingUrl("/signin")//endpoint para procesar formulario() manejado por sprinf security(Es lo mismo que se escribe en el formulario)
//                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")//El que dispara el formulario POST de la vista
                                .logoutSuccessUrl("/login/logout") //endpoint del controlador
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
                .userDetailsService(userDetailsService);

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
