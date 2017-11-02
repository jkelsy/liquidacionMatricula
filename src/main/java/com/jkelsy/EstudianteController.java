/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkelsy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jk
 */
@Named(value = "estudianteController")
@ViewScoped
public class EstudianteController implements Serializable {

    @Inject private EstudianteFacade estudianteFacade;
    @Inject private ArchivoService archivoService;
    @Inject private ArchivoFacade archivoFacade;

    private Estudiante estudiante;
    private String PEOPLE_CODE_ID;
    private int anyoActual;
    private String semestreActual;
    
    private List<Integer> anyoList;
    
    //soportes   
    private Archivo archivoIdentificacion;
    private Archivo archivoEstrato;    
    private Archivo archivoMensualidad;
    
    //soportes para patrimonio
    private Archivo archivoInstrumentosPublicos;
    private Archivo archivoDeclaracionRenta;
    private Archivo archivoBalance;    
    
    //soportes para ingresos
    private Archivo archivoIngresosRetenciones;
    private Archivo archivoPerdidasGanancias;

    public EstudianteController() {
        this.estudiante = new Estudiante();
        this.archivoIdentificacion = new Archivo();  
        this.archivoEstrato = new Archivo();
        
        this.archivoInstrumentosPublicos = new Archivo();
        this.archivoDeclaracionRenta = new Archivo();
        this.archivoBalance = new Archivo();
        
        this.archivoIngresosRetenciones = new Archivo();
        this.archivoPerdidasGanancias = new Archivo();
    }

    public Archivo getArchivoIngresosRetenciones() {
        return archivoIngresosRetenciones;
    }

    public void setArchivoIngresosRetenciones(Archivo archivoIngresosRetenciones) {
        this.archivoIngresosRetenciones = archivoIngresosRetenciones;
    }

    public Archivo getArchivoPerdidasGanancias() {
        return archivoPerdidasGanancias;
    }

    public void setArchivoPerdidasGanancias(Archivo archivoPerdidasGanancias) {
        this.archivoPerdidasGanancias = archivoPerdidasGanancias;
    }

    public Archivo getArchivoInstrumentosPublicos() {
        return archivoInstrumentosPublicos;
    }

    public void setArchivoInstrumentosPublicos(Archivo archivoInstrumentosPublicos) {
        this.archivoInstrumentosPublicos = archivoInstrumentosPublicos;
    }

    public Archivo getArchivoDeclaracionRenta() {
        return archivoDeclaracionRenta;
    }

    public void setArchivoDeclaracionRenta(Archivo archivoDeclaracionRenta) {
        this.archivoDeclaracionRenta = archivoDeclaracionRenta;
    }

    public Archivo getArchivoBalance() {
        return archivoBalance;
    }

    public void setArchivoBalance(Archivo archivoBalance) {
        this.archivoBalance = archivoBalance;
    }

    public Archivo getArchivoIdentificacion() {
        return archivoIdentificacion;
    }

    public void setArchivoIdentificacion(Archivo archivoIdentificacion) {
        this.archivoIdentificacion = archivoIdentificacion;
    }

    public Archivo getArchivoEstrato() {
        return archivoEstrato;
    }

    public void setArchivoEstrato(Archivo archivoEstrato) {
        this.archivoEstrato = archivoEstrato;
    }

    public Archivo getArchivoMensualidad() {
        return archivoMensualidad;
    }

    public void setArchivoMensualidad(Archivo archivoMensualidad) {
        this.archivoMensualidad = archivoMensualidad;
    }    

    public void inicio() {
        HttpSession session = SessionUtils.getSession();
        this.PEOPLE_CODE_ID = ((String) session.getAttribute("PEOPLE_CODE_ID"));
        this.anyoActual = ((int) session.getAttribute("anyoActual"));
        this.semestreActual = ((String) session.getAttribute("semestreActual"));
        
        this.estudiante = estudianteFacade.findByPEOPLE_CODE_ID(this.PEOPLE_CODE_ID, this.anyoActual, this.semestreActual);
        
        this.archivoIdentificacion = archivoFacade.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_IDENTIFICACION, this.PEOPLE_CODE_ID);
        this.archivoEstrato = archivoFacade.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_ESTRATO, this.PEOPLE_CODE_ID);
        this.archivoMensualidad = archivoFacade.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_MENSUALIDAD, this.PEOPLE_CODE_ID);
        
        this.archivoInstrumentosPublicos = archivoFacade.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_INSTRUMENTOS, this.PEOPLE_CODE_ID);
        this.archivoDeclaracionRenta = archivoFacade.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_DECLARACION, this.PEOPLE_CODE_ID);
        this.archivoBalance = archivoFacade.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_BALANCE, this.PEOPLE_CODE_ID);
        
        this.archivoIngresosRetenciones = archivoFacade.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_INGRESO_RETENCION, this.PEOPLE_CODE_ID);
        this.archivoPerdidasGanancias = archivoFacade.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_PERDIDA_GANANCIA, this.PEOPLE_CODE_ID);

        if (this.estudiante == null) {
            this.estudiante = new Estudiante();
        }
        
        this.anyoList = new ArrayList<>();
        for (int i = -15; i < 1; i++) {
            this.anyoList.add(Calendar.getInstance().get(Calendar.YEAR)+i );
        }
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public String getPEOPLE_CODE_ID() {
        return PEOPLE_CODE_ID;
    }

    public void setPEOPLE_CODE_ID(String PEOPLE_CODE_ID) {
        this.PEOPLE_CODE_ID = PEOPLE_CODE_ID;
    }

    public void guardar() {
        HttpSession session = SessionUtils.getSession();
        estudiante.setPEOPLE_CODE_ID((String) session.getAttribute("PEOPLE_CODE_ID"));
        estudiante.setAnyoLiquidacion(anyoActual);
        estudiante.setSemestre(semestreActual);
        estudiante.setFechaActualizacion(new Date());
        estudiante.setActualizadoPor((String) session.getAttribute("username"));
        estudianteFacade.guardar(estudiante);
        
        inicio();
    }

    public void cargarSoporteIdentificacion(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();        
        this.archivoIdentificacion = archivoService.upload(Constantes.TIPO_IDENTIFICACION, 
                (String)session.getAttribute("PEOPLE_CODE_ID"), 
                this.anyoActual,
                this.semestreActual,                
                event.getFile());      
    }
    
    public void cargarSoporteEstrato(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoEstrato = archivoService.upload(Constantes.TIPO_ESTRATO, 
                (String)session.getAttribute("PEOPLE_CODE_ID"), 
                this.anyoActual,
                this.semestreActual,
                event.getFile());       
    }
    
    public void cargarSoporteMensualidad(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoMensualidad = archivoService.upload(Constantes.TIPO_MENSUALIDAD, 
                (String)session.getAttribute("PEOPLE_CODE_ID"), 
                this.anyoActual,
                this.semestreActual,
                event.getFile());            
    }
    
    public void cargarSoporteInstrumentos(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoInstrumentosPublicos = archivoService.upload(Constantes.TIPO_INSTRUMENTOS, 
                (String)session.getAttribute("PEOPLE_CODE_ID"), 
                this.anyoActual,
                this.semestreActual,
                event.getFile());            
    }
    
    public void cargarSoporteDeclaracion(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoDeclaracionRenta = archivoService.upload(Constantes.TIPO_DECLARACION, 
                (String)session.getAttribute("PEOPLE_CODE_ID"), 
                this.anyoActual,
                this.semestreActual,
                event.getFile());            
    }
    
    public void cargarSoporteBalance(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoBalance = archivoService.upload(Constantes.TIPO_BALANCE, 
                (String)session.getAttribute("PEOPLE_CODE_ID"), 
                this.anyoActual,
                this.semestreActual,
                event.getFile());            
    }
    
    public void cargarSoporteIngreso(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoIngresosRetenciones = archivoService.upload(Constantes.TIPO_INGRESO_RETENCION, 
                (String)session.getAttribute("PEOPLE_CODE_ID"),
                this.anyoActual,
                this.semestreActual,
                event.getFile());            
    }
    
    public void cargarSoportePerdida(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoPerdidasGanancias = archivoService.upload(Constantes.TIPO_PERDIDA_GANANCIA, 
                (String)session.getAttribute("PEOPLE_CODE_ID"), 
                this.anyoActual,
                this.semestreActual,
                event.getFile());            
    }

    public List<Integer> getAnyoList() {
        return anyoList;
    }

    public void setAnyoList(List<Integer> anyoList) {
        this.anyoList = anyoList;
    }
    
}
