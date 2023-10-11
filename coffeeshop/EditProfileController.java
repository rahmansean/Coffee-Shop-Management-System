/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop;



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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class EditProfileController implements Initializable {

    PreparedStatement ps=null;
    Connection connection=null;
    ResultSet rs=null;

    @FXML
    private Button btnsave;
    @FXML
    private TextField empname;
    @FXML
    private TextField empmail;
    @FXML
    private TextField empaddress;
    @FXML
    private TextField empphone;
    @FXML
    private AnchorPane editprofilePane;
    @FXML
    private Button btnback;
    
   public static String emmail;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
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
    private void backAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
                    btnsave.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("EmployeeInfo.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
                    
                    
                    
                    
    }
    
    /**
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
      public void DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        connection = DriverManager.getConnection(URL);
         System.out.println("Connected database successfully.........");
    }
    
    public void Clear()
    {
       
        empname.clear();
        empmail.clear();
        empaddress.clear();
        empphone.clear();
    }

    @FXML
    private void btnsave(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        
         editEmpinfo();
       
        }
    
    
    
    public void editEmpinfo() throws IOException, SQLException, ClassNotFoundException{
         String nameFormat = "([^.\\s]([a-zA-Z\\.]){1,}\\s[a-zA-Z]{1,}\\s?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)";
        String mailFormat = "[^.\\s][a-z0-9\\.\\_]{3,64}@([a-z]){2,50}.[a-z]{2,}";
        String phoneFormat = "\\d{11}";
        //String phnRegex="\\d{11}";

         DBConnect();
      if(empname.getText().isEmpty() || empmail.getText().isEmpty() ||  empaddress.getText().isEmpty() ||  empphone.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Empty field is not acceptable!");
        }else if(!empmail.getText().matches(mailFormat)){
           JOptionPane.showMessageDialog(null,"Email is not valid");
        }else if(!empphone.getText().matches(phoneFormat)){
            JOptionPane.showMessageDialog(null,"Phone is not valid.");
        }else{
            String sql = "update EmployeeTable set employeeName='" + empname.getText() + "' , employeeEmail='" + empmail.getText() + "', employeeAddress='" + empaddress.getText() + "', employeePhone='" + empphone.getText() + "' where employeeEmail='"+profile+"'";
            Statement stmt= connection.createStatement();
            System.out.println("profile:"+sql);
            stmt.executeUpdate(sql);
            emmail=empmail.getText();
            
            Clear();
            JOptionPane.showMessageDialog(null,"Successfully Updated!");
            
            
          editprofilePane.getScene().getWindow().hide();
          Parent empinfo = FXMLLoader.load(getClass().getResource("EmployeeInfo.fxml"));
          Stage mainstage = new Stage();
          Scene scn = new Scene(empinfo);
          mainstage.setScene(scn);
          mainstage.show();
}
       
        
    
    
}

}
        
