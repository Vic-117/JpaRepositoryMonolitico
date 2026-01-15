/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author digis
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping
    public String login(@RequestParam(required = false) boolean error, Model model) {
        if(error){
            model.addAttribute("Error",error);
            model.addAttribute("MensajeError","Usuario o contraseña incorrectos");
            System.out.println(error);
        }else{
            System.out.println(error);
        
        }
        return "Login";
    }

}
