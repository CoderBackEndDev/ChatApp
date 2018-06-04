/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatApp;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB_Conn{

    Connection connect;
    Statement state;
    public void connection()  {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/csa_cw_database", "root", "");
            state = connect.createStatement();
        } catch (Exception ex) {
            Logger.getLogger(DB_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 
