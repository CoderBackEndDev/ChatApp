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
@WebService(serviceName = "CsaServer")
public class CsaServer {

    Date now = new Date();
    String Threadno;
    /**
     * Web service operation
     */
    //create the database connection obj
    DB_Conn DB_Con = new DB_Conn();
//this is to register the user

    @WebMethod(operationName = "NewUser")
//getting the users inputs
    public String NewUser(@WebParam(name = "Nickname") String Nickname, @WebParam(name = "Firstname") String Firstname, @WebParam(name = "LastName") String LastName, @WebParam(name = "Username") String Username, @WebParam(name = "Password") String Password) {
        String Nick = Nickname;
        String Fname = Firstname;
        String Lname = LastName;
        String Uname = Username;
        String Pass = Password;

        DB_Con.connection();
//passing the user entered values to the database
        try {
            DB_Con.state.executeUpdate("INSERT INTO login VALUES('" + Uname + "','" + Fname + "','" + Lname + "','" + Pass + "','" + Nick + "')");
//error handling.
        } catch (SQLException ex) {
            Logger.getLogger(CsaServer.class.getName()).log(Level.SEVERE, null, ex);

        }
        return "User has been added";
    }

    /**
     * Web service operation
     */
    //to login user.
    @WebMethod(operationName = "LoginUser")
    //until user enter correct username and password users wont be llowed to enter
    public boolean LoginUser(@WebParam(name = "Username") String Username, @WebParam(name = "Password") String Password) {
        DB_Con.connection();
        boolean logged = false;
//check if the username and password is equal to an existing password and username in the database
        try {
            ResultSet queryLog = DB_Con.state.executeQuery("SELECT * from login");
            while (queryLog.next()) {
                String user = queryLog.getString("Username");
                String pass = queryLog.getString("Pass");
                if (Username.equals(user) && Password.equals(pass)) {
                    //if the user is an existing use he can login
                    logged = true;
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(CsaServer.class.getName()).log(Level.SEVERE, null, ex);

        }

        return logged;
    }

    /**
     * Web service operation
     *
     * @param Title
     */
    // create threads is webserver fucntion is displayed below
    @WebMethod(operationName = "CreateThreads")
    public String Threads(@WebParam(name = "Title") String Title, @WebParam(name = "CreatedBy") String CreatedBy) {
// a date object is created and is called to set to the database
        String date_time = now.toString();
// database connection is innitiated
        DB_Con.connection();
        try {// the use of a try catch block
            DB_Con.state.executeUpdate("INSERT INTO threads(title,date_time,Username) VALUES('" + Title + "','" + date_time + "','" + CreatedBy + "')");

        } catch (SQLException ex) {
            Logger.getLogger(CsaServer.class.getName()).log(Level.SEVERE, null, ex);

        }
        // this message response helps in the user with the response
        return "Thread has been created";
    }

    /**
     * Web service operation
     */
    // view thread  retrieves values from the database  thread  class and assigns it to an  arraylist
    @WebMethod(operationName = "ThreadView")
    public ArrayList<String> ThreadView() {
        ArrayList<String> arrlist = new ArrayList();

        DB_Con.connection();
        try {
            String retrieve = "SELECT * From threads";
            ResultSet fetch = DB_Con.state.executeQuery(retrieve);
            while (fetch.next()) {
                String send = fetch.getInt("ThreadNo") + "#" + fetch.getString("title") + "#" + fetch.getString("date_time") + "#"
                        + fetch.getString("Username");
                arrlist.add(send);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return arrlist;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "EditThread")
    public ArrayList<String> EditThread() {

        ArrayList<String> displayMes = new ArrayList();

        DB_Con.connection();
        try {
            String retrieve = "SELECT * From messages where messages.ThreadID= '" + Threadno + "'";
            ResultSet fetch = DB_Con.state.executeQuery(retrieve);
            while (fetch.next()) {
                String send = fetch.getString("Message") + "#" + fetch.getString("date_time") + "#" + fetch.getString("Username") + "#" + fetch.getInt("ThreadID");
                displayMes.add(send);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return displayMes;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "newMessage")
    public String newMessage(@WebParam(name = "ThreadID") int ThreadID, @WebParam(name = "Message") String Message, @WebParam(name = "Username") String Username) {
        int threadNo = ThreadID;
        String Date_time = now.toString();
        String message = Message;
        String User = Username;

        DB_Con.connection();
        try {
            
            DB_Con.state.executeUpdate("INSERT INTO messages(ThreadID,date_time,Message,Username) VALUES('" + threadNo + "','" + Date_time + "','" + message + "','" + User + "')");
             DB_Con.state.executeUpdate("UPDATE threads SET date_time='"  + Date_time + "', Username='"  + User +"' Where threads.ThreadNo='"+threadNo+"'");
        } catch (SQLException ex) {
            Logger.getLogger(CsaServer.class.getName()).log(Level.SEVERE, null, ex);

        }
        return (threadNo + Date_time + message + User);
    }

    /**
     * Web service operation
     */
    // this is a method used in the gui to capture the reponse of of the users click
    @WebMethod(operationName = "SearchMess")
    public String SearchMess(@WebParam(name = "ThreadNo") String ThreadNo) {
        Threadno = ThreadNo;

        return "sending the thread ID";
    }

    /**
     * Web service operation
     */
    // this is done to check if an user has already registed in the register page
    @WebMethod(operationName = "Usernamevalidity")
    public boolean Usernamevalidity(@WebParam(name = "username") String username) {
        ArrayList<String> results = new ArrayList();
        boolean validity = false;
        DB_Con.connection();
        try {
            String retrieve = "SELECT * From login where login.Username= '" + username + "'";
            ResultSet fetch = DB_Con.state.executeQuery(retrieve);
            while (fetch.next()) {
                String send = fetch.getString("Username");
                results.add(send);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        validity = results.isEmpty();
        return validity;
    }

}
