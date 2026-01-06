/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vPerez.ProgramacionNCapasNov2025.DAO.IColoniaJpaRepository;
import vPerez.ProgramacionNCapasNov2025.JPA.Colonia;
import vPerez.ProgramacionNCapasNov2025.JPA.Result;

/**
 *
 * @author digis
 */
@Service
public class ColoniaService {
    @Autowired
    IColoniaJpaRepository coloniaJpaRepository;
    
    public Result getByMuncipio(int idMunicipio){
        Result result = new Result();
        try {
            result.Objects = new ArrayList();
            for(Colonia colonia: coloniaJpaRepository.findAll()){
                if(colonia.municipio.getIdMunicipio() == idMunicipio){
                    result.Objects.add(colonia);
                }
            
            }
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ex=ex;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return result;
    } 
    
}
