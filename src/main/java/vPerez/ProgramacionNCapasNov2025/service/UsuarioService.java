/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

//    public UsuarioService(IUsuarioJpaRepository usuarioJpaRepository) {
//        this.usuarioJpaRepository = usuarioJpaRepository;
//        
//    }
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
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            usuario.setPassword(encoder.encode(usuario.getPassword()));

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
    public Result softDelete(Usuario usuario) {
        Result result = new Result();
        try {
            Optional<Usuario> user = usuarioJpaRepository.findById(usuario.getIdUsuario());
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

    @Transactional
    public Result getById(int idUsuario) {
        Result result = new Result();
        try {
//            Usuario usuario = new Usuario();
            Optional<Usuario> usuario = usuarioJpaRepository.findById(idUsuario);
            result.Object = usuario.get();
            result.Correct = true;

        } catch (Exception e) {
            result.Correct = false;
            result.ErrorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }//AGREGADO DOMINGO

    @Transactional
    public Result getDinamico(Usuario usuario) {

        Result result = new Result();
        try {

            //Para encontrar un objeto igual
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnorePaths("idUsuario", "rol.nombre")
                    .withIgnoreNullValues()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

            usuario.setEstatus(1);

            if (usuario.rol.getIdRol() == 0) {
                matcher = matcher.withIgnorePaths("idRol");//ignora ese atributo
                usuario.rol = null;
            }
            if (usuario.getApellidoMaterno() == "") {
                matcher = matcher.withIgnorePaths("apellidoMaterno");
                usuario.setApellidoMaterno(null);
            } else {
                matcher = matcher.withMatcher("apellidoMaterno",
                        ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());//agrega regla de busqueda del atributo

            }
            if (usuario.getApellidoPaterno() == "") {
                usuario.setApellidoPaterno(null);
                matcher = matcher.withIgnorePaths("apellidoPaterno");
            } else {
                matcher = matcher.withMatcher("apellidoPaterno",
                        ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            }
            if (usuario.getNombre() == "") {
                usuario.setNombre(null);
                matcher = matcher.withIgnorePaths("nombre");
            } else {
                matcher = matcher.withMatcher("nombre",
                        ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            }
            Example<Usuario> ejemplo = Example.of(usuario, matcher);//ejemplo de busqueda, con el usuario y el buscador
            List<Usuario> listaU = usuarioJpaRepository.findAll(ejemplo);//busca varios objetos basandose en el ejemplo
            result.Objects = listaU;
            result.Correct = true;

        } catch (Exception e) {
            result.Correct = false;
        }
        return result;
    }//AGREGADO DOMINGO

    @Transactional
    public Result addAll(List<Usuario> usuarios) {//PROBAR, AGREGADO DOMINGO

        Result result = new Result();
        try {
            usuarioJpaRepository.saveAllAndFlush(usuarios);
            result.Correct = true;
        } catch (Exception e) {
            result.Correct = false;
            result.ErrorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;

    }

    @Transactional
    public Result getIdByEmail(String email) {
        Result result = new Result();
        try {
            result.Object =  usuarioJpaRepository.findByEmail(email).getIdUsuario();
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
