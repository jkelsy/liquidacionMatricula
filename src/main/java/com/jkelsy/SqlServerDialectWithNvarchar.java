/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkelsy;

import java.sql.Types;

/**
 *
 * @author jk
 */
public class SqlServerDialectWithNvarchar extends org.hibernate.dialect.SQLServerDialect {

    public SqlServerDialectWithNvarchar() {
        registerHibernateType(Types.NVARCHAR, 4000, "string");
    }

}
