/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

/**
 *
 * @author digis
 */
@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
        String  redirectUrl = "/login";
        for(var authority : authentication.getAuthorities()){
            if(authority.getAuthority().equals("ROLE_Administrador")){
                redirectUrl = "/Usuario";
                break;
                
            }else if(authority.getAuthority().equals("ROLE_Usuario")){
                
                redirectUrl = "/Usuario/detail";
                break;
            
            }
        
        }
        response.sendRedirect(redirectUrl);

    }
    
}
