/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import vPerez.ProgramacionNCapasNov2025.JPA.Estado;

/**
 *
 * @author digis
 */
public interface IEstadoJpaRepository extends JpaRepository<Estado, Integer>{
    
}
