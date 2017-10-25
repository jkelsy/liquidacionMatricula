/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkelsy;

import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author jk
 */
public class EstudianteService implements Serializable{
    
    @Inject
    private EstudianteFacade estudianteFacade;
    
    public void guardar(Estudiante estudiante){
        //Estudiante temporal = 
    }
    
}
