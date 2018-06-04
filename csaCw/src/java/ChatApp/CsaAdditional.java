/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatApp;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.swing.JOptionPane;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
@WebService(serviceName = "CsaAdditional")
public class CsaAdditional {
DB_Conn db_Connect = new DB_Conn();


    /**
     * Web service operation
     */
    @WebMethod(operationName = "changePass")
    public String forgotPass(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
      String Uname =username;
      String Pass = password;
      
        db_Connect.connection();
        try {
            db_Connect.state.executeUpdate("UPDATE login SET Pass= '"+ Pass +"'  Where Username='" + Uname+ "'");

        } catch (SQLException ex) {
            Logger.getLogger(CsaServer.class.getName()).log(Level.SEVERE, null, ex);

        }
        return "password changed";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "changePassValid")
    public boolean changePassValid(@WebParam(name = "fName") String fName, @WebParam(name = "lName") String lName,@WebParam(name = "nickname") String nickname,@WebParam(name = "username") String username) {
        boolean correct =false; 
         String Fname=fName;
      String Lname = lName;
      String Nick =nickname;
      String user =username;
      db_Connect.connection();
      ArrayList<String> retrievedb = new ArrayList<String>();
       try {
            String retrieve = "SELECT * From login where login.Username= '" + user + "'";
            ResultSet fetch = db_Connect.state.executeQuery(retrieve);
            while (fetch.next()) {
                String send =  fetch.getString("Fname")+ "#" +fetch.getString("Lname")  + "#" + fetch.getString("Nickname");
                retrievedb.add(send);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
       String []arr = new String[3];
        for(int i=0;i< retrievedb.size();i++){
        arr[0]= retrievedb.get(i).split("#")[0];
        arr[1]= retrievedb.get(i).split("#")[1];
        arr[2]= retrievedb.get(i).split("#")[2];
        if(arr[0].equals(Fname)){
            if(arr[1].equals(Lname)){
                if(arr[2].equals(Nick)){
                    correct =true;
                }
            }
        }
        
        }
        return correct;
    }
}
