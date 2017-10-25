/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkelsy;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jk
 */
public class LoginService implements Serializable {

    @PersistenceContext(unitName = "LiquidacionFormularioPU")
    private EntityManager em;

    public boolean buscarSalto(String username, String password) {

        Query q = em.createNativeQuery(
                "SELECT u.UserName, m.Password, m.PasswordSalt, m.IsLockedOut "
                + "FROM aspnet_Users u "
                + "	inner join aspnet_Membership m "
                + "	on u.UserId = m.UserId "
                + "Where u.UserName = '" + username + "'");
        Object[] usuario = null;
        try {
            usuario = (Object[]) q.getSingleResult();

            if (usuario != null) {
                System.out.println(usuario[0] + " " + usuario[1] + " " + usuario[2]);

                String salt = (String) usuario[2];
                String _password = (String) usuario[1];

                byte[] bytes = password.getBytes(StandardCharsets.UTF_16LE);
                byte[] src = java.util.Base64.getDecoder().decode(salt);
                byte[] dst = new byte[bytes.length + src.length];

                System.arraycopy(src, 0, dst, 0, src.length);
                System.arraycopy(bytes, 0, dst, src.length, bytes.length);

                MessageDigest md;
                try {
                    md = MessageDigest.getInstance("SHA-1");
                    byte[] digest = md.digest(dst);
                    String passwordHash = Base64.getEncoder().encodeToString(digest);

                    if (passwordHash.equals(_password)) {
                        return true;
                    }

                } catch (NoSuchAlgorithmException ex) {
                    System.err.println("mierda");
                }
            }
        } catch (NoResultException nre) {
            return false;
        }

        return false;
    }
    
    public Object[] buscarPEOPLE_CODE_ID(String username) {

        Query q = em.createNativeQuery(
                "Select p.PEOPLE_CODE_ID, p.FIRST_NAME, p.MIDDLE_NAME, p.LAST_NAME \n" +
                "from Campus.dbo.PersonUser pu \n" +
                "	inner join Campus.dbo.PEOPLE p \n" +
                "	on pu.PersonId = p.PersonId \n" +
                "Where pu.UserName = '"+username+"'");
        Object[] usuario = null;
        try {
            usuario = (Object[]) q.getSingleResult();

            if (usuario != null) {
                System.out.println(usuario[0] + " " + usuario[1] + " " + usuario[2]);
                return usuario;                
            }
        } catch (NoResultException nre) {
            return null;
        }

        return null;
    }

}
