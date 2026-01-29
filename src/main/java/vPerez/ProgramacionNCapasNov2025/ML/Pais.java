/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.ML;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author digis
 */
public class Pais {
//    @NotNull
    @Min(value = 1, message = "Escoge un Pais")
    private int idPais;
//    @Size(min = 2, max = 50, message = "Escoge tu pais")
    private String nombre;
    
    public int getIdPais(){
        return idPais;
    }
    
    public void setIdPais(int idPais){
        this.idPais = idPais;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    
}
