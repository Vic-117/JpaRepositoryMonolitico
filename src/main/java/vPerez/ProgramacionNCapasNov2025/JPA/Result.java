/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.JPA;

import vPerez.ProgramacionNCapasNov2025.ML.*;
import java.util.List;

/**
 *
 * @author digis
 */
public class Result<T> {
    
    public boolean Correct;
    public Exception ex;
    public String ErrorMessage;
    public T Object;
    public List<T> Objects;
    
}
