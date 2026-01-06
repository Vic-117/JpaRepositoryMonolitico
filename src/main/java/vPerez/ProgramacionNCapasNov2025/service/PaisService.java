/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vPerez.ProgramacionNCapasNov2025.DAO.IPaisJpaRepository;
import vPerez.ProgramacionNCapasNov2025.JPA.Result;

/**
 *
 * @author digis
 */
@Service
public class PaisService {
    
    @Autowired
    IPaisJpaRepository paisJpaRepository;
    
    public Result getAll(){
        
        Result result = new Result();
        try {
            result.Objects = paisJpaRepository.findAll();
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ex = ex;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return result;
    
    }
    
    
}
