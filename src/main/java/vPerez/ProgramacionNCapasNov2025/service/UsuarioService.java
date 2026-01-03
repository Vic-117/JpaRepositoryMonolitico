/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vPerez.ProgramacionNCapasNov2025.DAO.IDireccionJpaRepository;
import vPerez.ProgramacionNCapasNov2025.DAO.IUsuarioJpaRepository;
import vPerez.ProgramacionNCapasNov2025.JPA.Usuario;
import vPerez.ProgramacionNCapasNov2025.JPA.Result;

/**
 *
 * @author digis
 */
@Service
public class UsuarioService {

    @Autowired
    private IUsuarioJpaRepository usuarioJpaRepository;
    @Autowired
    private IDireccionJpaRepository direccionJpaRepository;

    @Transactional
    public Result getAll() {
        Result result = new Result();
        try {
//            result.Objects = new ArrayList<>();
            result.Objects = usuarioJpaRepository.findAll(Sort.by("idUsuario").descending());
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    public Result add(Usuario usuario) {
        Result result = new Result();
        try {
            usuarioJpaRepository.save(usuario);
            usuario.direcciones.get(0).Usuario = new Usuario();
            usuario.direcciones.get(0).Usuario.setIdUsuario(usuario.getIdUsuario());
            direccionJpaRepository.save(usuario.direcciones.get(0));
            result.Correct = true;
        } catch (Exception ex) {
            result.ex = ex;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.Correct = false;
        }
        return result;
    }

    @Transactional
    public Result update(Usuario usuario) {
        Result result = new Result();
        try {
            Optional<Usuario> user = usuarioJpaRepository.findById(usuario.getIdUsuario());
            usuario.direcciones = user.get().direcciones;
            usuario.setEstatus(1);
            usuarioJpaRepository.save(usuario);
            result.Correct = true;
        } catch (Exception e) {
            result.Correct = false;
            result.ErrorMessage = e.getLocalizedMessage();
            result.ex = e;

        }
        return result;

    }

    @Transactional
    public Result delete(int idUsuario) {
        Result result = new Result();
        try {
            usuarioJpaRepository.deleteById(idUsuario);
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Transactional
    public Result softDelete(Usuario usuario){
        Result result = new Result();
        try {
            Optional<Usuario> user =  usuarioJpaRepository.findById(usuario.getIdUsuario());
//            usuario.direcciones = user.get().direcciones;
            user.get().setEstatus(usuario.getEstatus());
            usuario = user.get();
            usuarioJpaRepository.save(usuario);
            result.Correct = true;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    
    }

}
