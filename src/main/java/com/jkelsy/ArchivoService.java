/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkelsy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLConnection;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jk
 */
@Named
public class ArchivoService implements Serializable{
    
    @Inject private ArchivoFacade archivoRepository;
    @Inject private ApplicationBean application;
    
    public Archivo upload(String tipo, String PEOPLE_CODE_ID, int anyo, String semestre, UploadedFile uploaded){        
        Archivo archivo = new Archivo();
        archivo.setExtension(FilenameUtils.getExtension(uploaded.getFileName()));
        archivo.setNombreOrigen(uploaded.getFileName());
        archivo.setPEOPLE_CODE_ID(PEOPLE_CODE_ID);
        archivo.setAnyo(anyo);
        archivo.setSemestre(semestre);
        archivoRepository.create(archivo);
            
        InputStream input = null;
        OutputStream output = null;        
        try {
            input = uploaded.getInputstream();
            
            archivo.setContentType(URLConnection.guessContentTypeFromStream(input));
            archivo.setNombre(tipo+"_"+archivo.getId().toString()+"."+archivo.getExtension());
            archivo.setRutaFisica(application.getRutaFisicaArchivos()+File.separator+archivo.getNombre());
            archivo.setTipoSoporte(tipo);
            archivo.setRutaWeb(archivo.getNombre());
            archivoRepository.edit(archivo);
            output = new FileOutputStream(new File(application.getRutaFisicaArchivos(), archivo.getNombre()));
            IOUtils.copy(input, output);
            
        }catch (IOException e) {            
            System.err.println("que cagada marica " + e.getMessage());
            return null;
        }finally{
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);            
        }        
        return archivo;
    }
    
}
