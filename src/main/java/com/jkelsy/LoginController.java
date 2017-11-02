/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkelsy;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jk
 */
@Named(value = "loginController")
@ViewScoped
public class LoginController implements Serializable {

    @Inject
    private LoginService loginService;
    
    private String user;
    private String password;
    
    public LoginController() {
        //this.user = "ialvarezgomez"; 
        //this.password = "Ivan2016@";
        
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        boolean valid = loginService.buscarSalto(user, password);
        if (valid) {            
            HttpSession session = SessionUtils.getSession();            
            session.setAttribute("username", user);            
            Object[] usuario = loginService.buscarPEOPLE_CODE_ID(user);
            Object[] configuracion = loginService.buscarConfiguracion();
            
            if(usuario!= null){
                session.setAttribute("PEOPLE_CODE_ID", usuario[0]);                          
                session.setAttribute("FIRST_NAME", usuario[1]);
                session.setAttribute("MIDDLE_NAME", usuario[2]);
                session.setAttribute("LAST_NAME", usuario[3]);
                
                session.setAttribute("anyoActual", configuracion[0]);
                session.setAttribute("semestreActual", configuracion[1]);
                return "pages/main?faces-redirect=true";
            }
            
            return "login";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Incorrect Username and Passowrd",
                    "Please enter correct username and Password"));
            return "login";
        }
    }

    public void logout() throws IOException, ServletException{
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();      
        
        try {
            request.logout();
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath());
             
        } catch (ServletException e) {
            context.addMessage(null,  
                    new FacesMessage("Error al Salir de la sesion"));
        }
    }
}
