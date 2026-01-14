/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vPerez.ProgramacionNCapasNov2025.DAO.IUsuarioJpaRepository;
import vPerez.ProgramacionNCapasNov2025.JPA.Usuario;

/**
 *
 * @author digis
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

    private IUsuarioJpaRepository iUsuarioJpaRepository;
    
    public UserDetailsService(IUsuarioJpaRepository iUsuarioJpaRepository){
        this.iUsuarioJpaRepository = iUsuarioJpaRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = iUsuarioJpaRepository.findByEmail(username);
        
        return User.withUsername(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.rol.getNombre()).build();
        
    }
    
    
}
