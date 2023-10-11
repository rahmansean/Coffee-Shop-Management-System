/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class RegistrationController implements Initializable {
    PreparedStatement pst;
    private Label label;
    private JFXTextField text1;
    private JFXTextField text2;
    private JFXTextField text3;
    private JFXTextField text4;
    @FXML
    private Button btnsignup;
    private JFXTextField text5;
    @FXML
    private Button gotologin;
    private JFXPasswordField text6;
    int empId;
    
    private JFXTextField text7;
    private TextField empid;
    @FXML
    private TextField empname;
    @FXML
    private TextField empemail;
    @FXML
    private TextField emppass;
    @FXML
    private TextField empaddress;
    @FXML
    private ComboBox empgender;
    @FXML
    private TextField empphone;
    PreparedStatement ps=null;
    Connection connection=null;
    ResultSet rs=null;
    private String password;
    private Button showpassword;
    @FXML
    private CheckBox checkpass;
    private PasswordField pass;
    @FXML
    private TextField passshow;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empgender.getItems().add("Male");
        empgender.getItems().add("Female");
        
        try {
            // TODO
            DBConnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
    }    

      
    @FXML
    private void signupAction(ActionEvent event) throws SQLException {
       //String Mail=empemail.getText();
        String phnRegex="\\d{11}";
         String emailRegex = "^(.+)@(\\S+)$";  
      
        if(empname.getText().isEmpty() || empemail.getText().isEmpty() || emppass.getText().isEmpty() || empaddress.getText().isEmpty()
                || empgender.getValue()==null || empphone.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Empty field is not acceptable!");
        }else if(!empemail.getText().matches(emailRegex)){
           JOptionPane.showMessageDialog(null,"Email is not valid");
           empemail.requestFocus();
        }else if(emppass.getText().length()!=9){
            JOptionPane.showMessageDialog(null,"Password must be at least 9 characters in length.");
        }else if(!empphone.getText().matches(phnRegex)){
            JOptionPane.showMessageDialog(null,"Phone is not valid.");
        }else{
          try {
            
          
            String query ="insert into EmployeeTable(employeeName,employeeEmail,employeePassword,employeeaddress,employeeGender,employeePhone) values(?,?,?,?,?,?)";
            DBConnect();
            
            ps = connection.prepareStatement(query);
           
            ps.setString(1, empname.getText());
            ps.setString(2, empemail.getText());
            ps.setString(3, emppass.getText());
            ps.setString(4, empaddress.getText());
            ps.setString(5, empgender.getValue().toString());
            ps.setString(6, empphone.getText());
            ps.execute();
            
            JOptionPane.showMessageDialog(null,"Registration completed successfully!");  
            Clear();
                 } catch (ClassNotFoundException ex) {
             Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(ex.getMessage());
                a.show();
        }
       
    
        }
        
    }
    
    
     @FXML
    private void showPass(ActionEvent event) {
        
        if (checkpass.isSelected()) {
            passshow.setVisible(false);
            emppass.setVisible(true);
            password = emppass.getText();
            //pass.clear();
            passshow.setText(password);
            passshow.setVisible(true);
            emppass.setVisible(false);
           

        } else {
            emppass.setVisible(true);
            emppass.setText(password);
            passshow.setVisible(false);
        }
        
    }
    
    
    
    
  
    
    @FXML
    private void gotologinAction(ActionEvent event) {
        
        try {
            Parent signup = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
            Scene scn = new Scene(signup);
            Stage login = new Stage();
            
            login.setScene(scn);
            login.setTitle("Login");
            login.show();
            btnsignup.getScene().getWindow().hide();
            
            
        } catch (IOException ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        connection = DriverManager.getConnection(URL);
         System.out.println("Connected database successfully.........");
    }
    
    public void Clear()
    {
       
        empname.clear();
        empemail.clear();
        emppass.clear();
        empaddress.clear();
        empgender.setValue("Choose gender");
        empphone.clear();
    }

   

   
    
    
}
