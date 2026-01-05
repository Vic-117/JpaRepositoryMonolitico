/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vPerez.ProgramacionNCapasNov2025.DAO.IRolJpaRepository;
import vPerez.ProgramacionNCapasNov2025.JPA.Result;

/**
 *
 * @author prvj1
 */
@Service
public class RolService {

    @Autowired
    IRolJpaRepository rolJpaRepository;

    public Result getAll() {
        Result result = new Result();
        try {
            result.Objects = rolJpaRepository.findAll();
            result.Correct = true;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex= ex;
        }
        return result;
    }
}
