/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author digis
 */
public class Estado {
    @Min(value = 1, message = "Escoge un Estado")
    private int idEstado;
     @Size(min = 2, max = 50, message = "Escoge tu Estado")
     @NotNull
    private String nombre;
//    @Valid
     @NotNull
    public Pais pais;
    
    public int getIdEstado(){
        return idEstado;
    }
    public void setIdEstado(int idEstado){
        this.idEstado = idEstado;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    
}
