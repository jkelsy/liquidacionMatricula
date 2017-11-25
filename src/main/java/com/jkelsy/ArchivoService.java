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
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class ArchivoService implements Serializable {

    @Inject
    private ArchivoFacade archivoRepository;
    @Inject
    private ApplicationBean application;

    public Archivo upload(String tipo, String PEOPLE_CODE_ID, UploadedFile uploaded) {
        
        Archivo archivo = archivoRepository.findByTipoAndPEOPLE_CODE_ID(tipo, PEOPLE_CODE_ID);
        
        if(archivo == null){
            archivo = new Archivo();
        }
        
        archivo.setExtension(FilenameUtils.getExtension(uploaded.getFileName()));
        archivo.setNombreOrigen(uploaded.getFileName());
        archivo.setPEOPLE_CODE_ID(PEOPLE_CODE_ID);       
        archivo.setTipoSoporte(tipo);
        
        if(archivo.getId() == null){
            archivoRepository.create(archivo);
        }else{
            archivoRepository.edit(archivo);
        }       

        InputStream input = null;
        OutputStream output = null;
        try {
            input = uploaded.getInputstream();

            archivo.setNombre(tipo + "_" + archivo.getId().toString() + "." + archivo.getExtension());
            archivo.setRutaFisica(application.getRutaFisicaArchivos() + File.separator + archivo.getNombre());            
            archivo.setRutaWeb(archivo.getNombre());
            archivoRepository.edit(archivo);
            output = new FileOutputStream(new File(application.getRutaFisicaArchivos(), archivo.getNombre()));
            IOUtils.copy(input, output);
            

        } catch (IOException e) {
            System.err.println("Error al subir el archivo al servidor " + e.getMessage());
            return null;
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
        return archivo;
    }

    public void remove(Archivo archivo) {
        Path path = Paths.get(archivo.getRutaFisica());
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

}
