
package coffeeshop;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LogInController implements Initializable {

    @FXML
    private TextField logintext1;
    @FXML
    private PasswordField logintext2;
    @FXML
    private Button gotosignup;
    @FXML
    private Button btnlogin;
    
    Connection connection=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    @FXML
    private ComboBox usertype;
    public static String profile;
    public static String adminprofile;
    public static String employeemail;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usertype.getItems().add("Admin");
        usertype.getItems().add("Employee");
         
          
    }

    @FXML
    private void gotosignupAction(ActionEvent event) throws IOException {
                    Parent login = FXMLLoader.load(getClass().getResource("Registration.fxml"));
                    Scene scn = new Scene(login);
                    Stage signup = new Stage();

                    signup.setScene(scn);
                    signup.setTitle("signup");
                    signup.show();
                    
                   btnlogin.getScene().getWindow().hide();

    }

    @FXML
    private void loginAction(ActionEvent event) {
        
        String emailRegex = "^(.+)@(\\S+)$";  
      
        
        if(usertype.getValue()==null){
            
            JOptionPane.showMessageDialog(null,"choose user type");
            
        }else{
              if(logintext1.getText().isEmpty() || logintext2.getText().isEmpty() )
        {
            JOptionPane.showMessageDialog(null,"Empty field is not acceptable!");
        }else if(logintext2.getText().matches(emailRegex)){
           JOptionPane.showMessageDialog(null,"Email is not valid!");
        }else if(logintext2.getText().length()!=9){
            JOptionPane.showMessageDialog(null,"Password must be at least 9 characters in length.");
        }else{
        
            if(usertype.getValue().equals("Admin")){
                String sql="Select * from AdminTable where adminEmail=? and adminPassword=?";
            try{
                DBConnect();
                
                pst=connection.prepareStatement(sql);
                pst.setString(1, logintext1.getText());
                pst.setString(2, logintext2.getText());
                
                rs=pst.executeQuery();
                
                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"Email and Password is correct!");
                   
                    adminprofile= rs.getString("adminEmail");
                    
                    //System.out.println("employeeid:"+profile);
                    
                    btnlogin.getScene().getWindow().hide();
                    
                    Parent login=FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(login);
                    mainstage.setScene(scn);
                    mainstage.show();
                    
                    
                }else{
                    JOptionPane.showMessageDialog(null,"Invalid Email and Password!");
                }
            
            }catch(Exception e){
                       JOptionPane.showMessageDialog(null,e);
                   }
        }else if(usertype.getValue().equals("Employee")){
          

                String sql="Select * from EmployeeTable where employeeEmail=? and employeePassword=?";
        try{
            DBConnect();
            
            pst=connection.prepareStatement(sql);
            pst.setString(1, logintext1.getText());
            pst.setString(2, logintext2.getText());
            
            rs=pst.executeQuery();
            
            if(rs.next()){
                profile= rs.getString("employeeEmail");
                
                
                JOptionPane.showMessageDialog(null,"Email and Password is correct!");
                
                System.out.println("GetId successfully........."+profile);
                
                 
                    
                
                btnlogin.getScene().getWindow().hide();
                
                Parent emplogin=FXMLLoader.load(getClass().getResource("EmpDashboard.fxml"));
                Stage mainstage=new Stage();
                Scene scn=new Scene(emplogin);
                mainstage.setScene(scn);
                mainstage.show();
                
                
            }else{
                JOptionPane.showMessageDialog(null,"Invalid Email and Password!");
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }    
       }else{
            JOptionPane.showMessageDialog(null,"Something Wrong");
      
      }
        
      }
    }
    }      
    
  
     void DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        connection = DriverManager.getConnection(URL);
         System.out.println("Connected database successfully.........");
    }
    
}
    
   
    

