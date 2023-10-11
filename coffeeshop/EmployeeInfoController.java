/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop;

import static coffeeshop.EditProfileController.emmail;
import static coffeeshop.LogInController.profile;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

    

/**
 * FXML Controller class
 *
 * @author User
 */
public class EmployeeInfoController implements Initializable {
    PreparedStatement ps=null;
    Connection connection=null;
    ResultSet rs=null;

    @FXML
    private Button btnback;
    @FXML
    private TextField empname;
    @FXML
    private TextField empmail;
    @FXML
    private TextField empaddress;
    @FXML
    private TextField empphone;
    @FXML
    private TextField empgender;
    @FXML
    private TextField empid;
    @FXML
    private Button editProfile;
    @FXML
    private Button refresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            showProfileDetails();
            // TODO
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeInfoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
     public void showProfileDetails() throws ClassNotFoundException, SQLException
    {
        DBConnect();
       

        String query = "select * from EmployeeTable";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);
        while(rs.next())
        {
            if(rs.getString("employeeEmail") == null ? profile == null : rs.getString("employeeEmail").equals(profile))
            {
                empid.setText(rs.getString("employeeID"));
                empname.setText(rs.getString("employeeName"));
                empmail.setText(rs.getString("employeeEmail"));
                empaddress.setText(rs.getString("employeeAddress"));
                empphone.setText(rs.getString("employeePhone"));
                empgender.setText(rs.getString("employeeGender"));
            }
        }
        
         rs.close();
        
      connection.close();
    }
    
     
      public void DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        connection = DriverManager.getConnection(URL);
         System.out.println("Connected database successfully.........");
    }
    
      

    @FXML
    private void backAction(ActionEvent event) throws IOException {
                    btnback.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("EmpDashboard.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
    }

    @FXML
    private void editProfileAction(ActionEvent event) throws IOException {
                    editProfile.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("EditProfile.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
    }

    @FXML
    private void refreshAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        
     DBConnect();
       

        String query = "select * from EmployeeTable where employeeEmail='"+emmail+"'";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);
        while(rs.next())
        {
                empid.setText(rs.getString("employeeID"));
                empname.setText(rs.getString("employeeName"));
                empmail.setText(rs.getString("employeeEmail"));
                empaddress.setText(rs.getString("employeeAddress"));
                empphone.setText(rs.getString("employeePhone"));
                empgender.setText(rs.getString("employeeGender"));
            
        }
    }



    
}
