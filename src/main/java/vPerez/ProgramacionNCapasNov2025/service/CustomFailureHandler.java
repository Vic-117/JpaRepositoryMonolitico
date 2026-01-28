/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

/**
 *
 * @author digis
 */
@Service
public class CustomFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession sesionHttp = request.getSession();
        if(exception instanceof DisabledException){
            sesionHttp.setAttribute("isDisable",true);
            sesionHttp.setAttribute("ErrorMessage","Usuario desabilitado, por favor pidele al administrador que reactive tu cuenta");
            response.sendRedirect("/login/UserDisabled");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
    
}
