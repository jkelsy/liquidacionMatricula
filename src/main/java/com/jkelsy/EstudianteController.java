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
import javax.faces.application.FacesMessage;
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

    @Inject
    private EstudianteFacade estudianteFacade;
    @Inject
    private ArchivoService archivoService;
    @Inject
    private ArchivoFacade archivoFacade;

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
            this.anyoList.add(Calendar.getInstance().get(Calendar.YEAR) + i);
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

    public boolean validar() {

        //validar la nacionalidad
        if (estudiante.getNacionalidad() == null) {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Debe seleccionar la nacionalidad",
                            "Debe seleccionar la nacionalidad")
            );
            return false;
        }
        
        //validar el soporte para la nacionalidad
        if(archivoIdentificacion == null){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar un soporte para la nacionalidad",
                            "Debe ingresar un soporte para la nacionalidad")
            );
            return false;
        }
        
        //si la nacionalidad es extrangera no necesita más soportes
        if(estudiante.getNacionalidad().equals("Extrangero")){
            return true;
        }
        
        //validar estrato
        if(estudiante.getEstrato() == 0){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Debe seleccionar su estrato",
                            "Debe seleccionar su estrato")
            );
            return false;
        }
        
        //validar soporte para estrato
        if(archivoEstrato == null){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar un soporte para su estrato",
                            "Debe ingresar un soporte para su estrato")
            );
            return false;
        }
        
        //validar tipo institución educativa
        if(estudiante.getTipoColegio() == null){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Debe seleccionar el tipo de institución educativa",
                            "Debe seleccionar el tipo de institución educativa")
            );
            return false;
        }
        
        //validar último año de pago del volante
        if(estudiante.getUltimoAnyoPago() == 0){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Debe seleccionar el último año de estudio de bachillerato",
                            "Debe seleccionar el último año de estudio de bachillerato")
            );
            return false;
        }
        
        //validar soporte para ultima mensualidad
        if(archivoMensualidad == null){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar un soporte para volante de pago última mensualidad",
                            "Debe ingresar un soporte para volante de pago última mensualidad")
            );
            return false;
        }
        
        //validar patrimonio        
        if(estudiante.getPatrimonio() <= 0){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar un patrimonio válido",
                            "Debe ingresar un patrimonio válido")
            );
            return false;
        }
        
        //validar soportes para el patrimonio
        //validar soporte para instrumentos públicos
        if(archivoInstrumentosPublicos == null){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Para el Patrimonio debe ingresar un soporte para certificado de bienes de instrumentos públicos",
                            "Para el Patrimonio debe ingresar un soporte para certificado de bienes de instrumentos públicos")
            );
            return false;
        }
        
        //validar soportes para el patrimonio
        //declaracion de renta o balance firmado por un contador
        if((archivoDeclaracionRenta == null) && (archivoBalance == null)){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "para el Patrimonio debe ingresar un soporte para la declaración de renta o un soporte de balance firmado por un contador",
                            "para el Patrimonio debe ingresar un soporte para la declaración de renta o un soporte de balance firmado por un contador")
            );
            return false;
        }
        
        //validar ingresos        
        if(estudiante.getIngreso()<= 0){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Debe intruducir un valor de ingreso válido",
                            "Debe intruducir un valor de ingreso válido")
            );
            return false;
        }
        
        //validar soportes para ingreso
        //validar soporte para instrumentos públicos
        if((archivoDeclaracionRenta == null) && (archivoIngresosRetenciones == null) && (archivoPerdidasGanancias == null) ){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "para los Ingresos  Debe presentar los siguientes documentos: " +
"                                                La declaración de renta, en caso de ser asalariado el certificado\n" +
"                                                de ingresos y retenciones, sino presenta declaración de renta ni es\n" +
"                                                asalariado presentará estado de pérdidas y ganancias firmado por un \n" +
"                                                contador titulado.",
                            "para los Ingresos   Debe presentar los siguientes documentos: " +
"                                                La declaración de renta, en caso de ser asalariado el certificado\n" +
"                                                de ingresos y retenciones, sino presenta declaración de renta ni es\n" +
"                                                asalariado presentará estado de pérdidas y ganancias firmado por un \n" +
"                                                contador titulado.")
            );
            return false;
        }
        
        return true;
    }

    public void guardar() {

        if (validar()) {
            HttpSession session = SessionUtils.getSession();
            estudiante.setPEOPLE_CODE_ID((String) session.getAttribute("PEOPLE_CODE_ID"));
            estudiante.setAnyoLiquidacion(anyoActual);
            estudiante.setSemestre(semestreActual);
            estudiante.setFechaActualizacion(new Date());
            estudiante.setActualizadoPor((String) session.getAttribute("username"));
            estudianteFacade.guardar(estudiante);

            inicio();
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Datos actualizados de manera exitosa",
                            "Datos actualizados de manera exitosa")
            );
        }

    }

    public void cargarSoporteIdentificacion(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoIdentificacion = archivoService.upload(Constantes.TIPO_IDENTIFICACION,
                (String) session.getAttribute("PEOPLE_CODE_ID"),                
                event.getFile());
    }
    
    public void eliminarSoporteIdentificacion(){
        if(archivoIdentificacion != null){
            archivoService.remove(archivoIdentificacion);
            archivoFacade.remove(archivoIdentificacion);
            archivoIdentificacion = null;
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Soporte de nacionalidad eliminado de manera exitosa",
                            "Soporte de nacionalidad eliminado de manera exitosa")
            );
        }        
    }

    public void cargarSoporteEstrato(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoEstrato = archivoService.upload(Constantes.TIPO_ESTRATO,
                (String) session.getAttribute("PEOPLE_CODE_ID"),               
                event.getFile());
    }
    
    public void eliminarSoporteEstrato(){
        if(archivoEstrato != null){
            archivoService.remove(archivoEstrato);
            archivoFacade.remove(archivoEstrato);
            archivoEstrato = null;
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Soporte de estrato eliminado de manera exitosa",
                            "Soporte de estrato eliminado de manera exitosa")
            );
        }        
    }

    public void cargarSoporteMensualidad(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoMensualidad = archivoService.upload(Constantes.TIPO_MENSUALIDAD,
                (String) session.getAttribute("PEOPLE_CODE_ID"),                
                event.getFile());
    }
    
    public void eliminarSoporteMensualidad(){
        if(archivoMensualidad != null){
            archivoService.remove(archivoMensualidad);
            archivoFacade.remove(archivoMensualidad);
            archivoMensualidad = null;
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Soporte de pago mensualidad eliminado de manera exitosa",
                            "Soporte de pago mensualidad eliminado de manera exitosa")
            );
        }        
    }

    public void cargarSoporteInstrumentos(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoInstrumentosPublicos = archivoService.upload(Constantes.TIPO_INSTRUMENTOS,
                (String) session.getAttribute("PEOPLE_CODE_ID"),              
                event.getFile());
    }
    
    public void eliminarSoporteInstrumentos(){
        if(archivoInstrumentosPublicos != null){
            archivoService.remove(archivoInstrumentosPublicos);
            archivoFacade.remove(archivoInstrumentosPublicos);
            archivoInstrumentosPublicos = null;
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Soporte de instrumentos públicos eliminado de manera exitosa",
                            "Soporte de instrumentos públicos eliminado de manera exitosa")
            );
        }        
    }

    public void cargarSoporteDeclaracion(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoDeclaracionRenta = archivoService.upload(Constantes.TIPO_DECLARACION,
                (String) session.getAttribute("PEOPLE_CODE_ID"),               
                event.getFile());
    }
    
    public void eliminarSoporteDeclaracion(){
        if(archivoDeclaracionRenta != null){
            archivoService.remove(archivoDeclaracionRenta);
            archivoFacade.remove(archivoDeclaracionRenta);
            archivoDeclaracionRenta = null;
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Soporte de declración de renta eliminado de manera exitosa",
                            "Soporte de declración de renta eliminado de manera exitosa")
            );
        }        
    }

    public void cargarSoporteBalance(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoBalance = archivoService.upload(Constantes.TIPO_BALANCE,
                (String) session.getAttribute("PEOPLE_CODE_ID"),                
                event.getFile());
    }
    
    public void eliminarSoporteBalance(){
        if(archivoBalance != null){
            archivoService.remove(archivoBalance);
            archivoFacade.remove(archivoBalance);
            archivoBalance = null;
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Soporte de balance eliminado de manera exitosa",
                            "Soporte de balance eliminado de manera exitosa")
            );
        }        
    }

    public void cargarSoporteIngreso(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoIngresosRetenciones = archivoService.upload(Constantes.TIPO_INGRESO_RETENCION,
                (String) session.getAttribute("PEOPLE_CODE_ID"),              
                event.getFile());
    }
    
    public void eliminarSoporteIngreso(){
        if(archivoIngresosRetenciones != null){
            archivoService.remove(archivoIngresosRetenciones);
            archivoFacade.remove(archivoIngresosRetenciones);
            archivoIngresosRetenciones = null;
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Soporte de ingresos y retenciones eliminado de manera exitosa",
                            "Soporte de ingresos y retenciones eliminado de manera exitosa")
            );
        }        
    }

    public void cargarSoportePerdida(FileUploadEvent event) {
        HttpSession session = SessionUtils.getSession();
        this.archivoPerdidasGanancias = archivoService.upload(Constantes.TIPO_PERDIDA_GANANCIA,
                (String) session.getAttribute("PEOPLE_CODE_ID"),               
                event.getFile());
    }
    
    public void eliminarSoportePerdida(){
        if(archivoPerdidasGanancias != null){
            archivoService.remove(archivoPerdidasGanancias);
            archivoFacade.remove(archivoPerdidasGanancias);
            archivoPerdidasGanancias = null;
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Soporte de pérdidas y ganancias eliminado de manera exitosa",
                            "Soporte de pérdidas y ganancias eliminado de manera exitosa")
            );
        }        
    }

    public List<Integer> getAnyoList() {
        return anyoList;
    }

    public void setAnyoList(List<Integer> anyoList) {
        this.anyoList = anyoList;
    }

}
