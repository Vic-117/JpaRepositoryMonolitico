/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import vPerez.ProgramacionNCapasNov2025.JPA.Estado;
import vPerez.ProgramacionNCapasNov2025.JPA.Municipio;

/**
 *
 * @author digis
 */
public interface IMunicipioJpaRepository extends JpaRepository<Municipio, Integer> {
    
}
