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
import vPerez.ProgramacionNCapasNov2025.DAO.IMunicipioJpaRepository;
import vPerez.ProgramacionNCapasNov2025.JPA.Estado;
import vPerez.ProgramacionNCapasNov2025.JPA.Municipio;
import vPerez.ProgramacionNCapasNov2025.JPA.Result;

/**
 *
 * @author digis
 */
@Service
public class MunicipioService {
    @Autowired
    IMunicipioJpaRepository municipioJpaRepository;
    
    @Autowired
    IEstadoJpaRepository estadoJpaRepository;
    
    public Result getByEstado(int idEstado){
    Result result = new Result();
        try {
                result.Objects = new ArrayList();
            for(Municipio municipio:  municipioJpaRepository.findAll()){
                if(municipio.estado.getIdEstado() == idEstado){
                    result.Objects.add(municipio);
                
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
