/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vPerez.ProgramacionNCapasNov2025.DAO.IDireccionJpaRepository;
import vPerez.ProgramacionNCapasNov2025.DAO.IUsuarioJpaRepository;
import vPerez.ProgramacionNCapasNov2025.JPA.Direccion;
import vPerez.ProgramacionNCapasNov2025.JPA.Result;
import vPerez.ProgramacionNCapasNov2025.JPA.Usuario;

/**
 *
 * @author prvj1
 */
@Service
public class DireccionService{
    @Autowired
    IDireccionJpaRepository direccionJpaRepository;
    @Autowired
    IUsuarioJpaRepository usuarioJpaRepository;
    
    @Transactional
    public Result add(Direccion direccion){
        Result result = new Result();
        try {
//            Usuario usuario = new Usuario();
//            Direccion dir = direccionJpaRepository.findById(direccion.getIdDireccion()).get();
//            direccion.Usuario = dir.Usuario;
            Usuario usuario = usuarioJpaRepository.findById(direccion.Usuario.getIdUsuario()).get();
            direccion.Usuario = usuario;
            direccionJpaRepository.save(direccion);
            result.Correct = true;
           
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Transactional
    public Result update(Direccion direccion){
        Result result = new Result();
        
        try {
            direccionJpaRepository.save(direccion);
            
        } catch (Exception ex) {
        }
        return result;
    }
    
     @Transactional
    public Result getById(int idDireccion) {
        Result result = new Result();
        try {
            Optional<Direccion> direccion =direccionJpaRepository.findById(idDireccion);
            result.Object = direccion.get();
            result.Correct = true;

        } catch (Exception e) {
            result.Correct = false;
            result.ErrorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
    
    @Transactional
    public Result delete(int idDireccion){
        Result result = new Result();
        try {
            Direccion direccion = direccionJpaRepository.findById(idDireccion).get();
            direccionJpaRepository.delete(direccion);
            result.Correct = true;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ex = ex;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return result;
    }
    
    
}
