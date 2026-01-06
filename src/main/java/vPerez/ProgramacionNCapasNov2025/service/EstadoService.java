/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vPerez.ProgramacionNCapasNov2025.DAO.IEstadoJpaRepository;
import vPerez.ProgramacionNCapasNov2025.DAO.IPaisJpaRepository;
import vPerez.ProgramacionNCapasNov2025.JPA.Estado;
import vPerez.ProgramacionNCapasNov2025.JPA.Pais;
import vPerez.ProgramacionNCapasNov2025.JPA.Result;

/**
 *
 * @author digis
 */
@Service
public class EstadoService {
    @Autowired
    IEstadoJpaRepository estadoJpaRepository;
    
    @Autowired
    IPaisJpaRepository paisJpaRepository;
    
    public Result getByPais(int idPais){
        Result result = new Result();
        try {
            Pais pais = paisJpaRepository.findById(idPais).get();
            List<Estado> estados = estadoJpaRepository.findAll();
            result.Objects = new ArrayList();
            for(Estado estado: estados){
                if(estado.pais.getIdPais() == pais.getIdPais()){
                    result.Objects.add(estado);
                }
            }
            result.Correct = true;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ex = ex;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        
        return result;
    }
    
}
