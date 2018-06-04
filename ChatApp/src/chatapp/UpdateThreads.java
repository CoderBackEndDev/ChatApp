/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

import java.util.ArrayList;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class UpdateThreads {
    ArrayList<String> List;
    DefaultTableModel model;
    
    
    UpdateThreads(DefaultTableModel jTable1){
        this.model = jTable1;
    }
    
   public void retrieve() {

        ArrayList<String> List = (ArrayList<String>) threadView();
        String[] arr = new String[4];
        for (int i = 0; i < List.size(); i++) {
            arr[0] = List.get(i).split("#")[0];
            arr[1] = List.get(i).split("#")[1];
            arr[2] = List.get(i).split("#")[2];
            arr[3] = List.get(i).split("#")[3];
            model.addRow(arr);
        }   
}

    private static java.util.List<java.lang.String> threadView() {
        chatapp.CsaServer_Service service = new chatapp.CsaServer_Service();
        chatapp.CsaServer port = service.getCsaServerPort();
        return port.threadView();
    }
   
   
}
