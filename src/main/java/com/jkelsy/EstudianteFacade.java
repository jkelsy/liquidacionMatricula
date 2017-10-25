/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkelsy;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jk
 */
@Stateless
public class EstudianteFacade extends AbstractFacade<Estudiante> {

    @PersistenceContext(unitName = "LiquidacionPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudianteFacade() {
        super(Estudiante.class);
    }
    
    public Estudiante findByPEOPLE_CODE_ID(String PEOPLE_CODE_ID){
        Estudiante temporal;       
        try{
            temporal = em.createNamedQuery("Estudiante.findByCode", Estudiante.class)
                    .setParameter("PEOPLE_CODE_ID", PEOPLE_CODE_ID)
                    .getSingleResult();
        }catch(Exception e){            
            temporal = null;
        }            
        return temporal;
    }
    
    public void guardar(Estudiante estudiante){
        if(estudiante.getId() == null){            
            this.create(estudiante);
        }else{            
            this.edit(estudiante);
        }        
    }
    
}
