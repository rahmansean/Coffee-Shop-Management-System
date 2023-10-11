/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop;

import static coffeeshop.LogInController.profile;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class EmpDashboardController implements Initializable {

    private TextField customername;
    private TextField customerphone;
    PreparedStatement ps=null;
    Connection connection=null;
    ResultSet rs=null;
    private Button btncustinfo;
    int cust_num;
    @FXML
    private ImageView exit;
    @FXML
    private JFXButton btnCustInfo;
    @FXML
    private JFXButton btnlogout;
    @FXML
    private Label empname;
    @FXML
    private AnchorPane empmail;
    @FXML
    private JFXButton btnprofile;
    @FXML
    private Label empemail;
    @FXML
    private JFXButton menubtn;
    @FXML
    private JFXButton posbtn;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            DBConnect();
            ProfileName();
            //showProfileDetails();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmpDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
              
        
    }  
    
    public void ProfileName() throws SQLException, ClassNotFoundException
    {
        
        DBConnect();
        String query = "select employeeName,employeeEmail from EmployeeTable";
        
        

        Statement stm = connection.createStatement();

        ResultSet rs = stm.executeQuery(query);
        while(rs.next())
        {
            if(rs.getString("employeeEmail") == null ? profile == null : rs.getString("employeeEmail").equals(profile))
            {
                 empname.setText(rs.getString("employeeName"));
                 empemail.setText(rs.getString("employeeEmail"));
               
            }
        }
        
         rs.close();
        
      connection.close();
    }
    
    
    
   
    
    
    

    /*@FXML
    private void generateAction(ActionEvent event) throws SQLException {
         String phnRegex="\\d{11}";
         String emailRegex = "^(.+)@(\\S+)$";  
       
             //^(.+)@(\\S+)$
        
            //Random rand = new Random();
            //int maxNumber = 3000;

            //cust_num = (2022 + rand.nextInt(maxNumber) + 1);
        if(customername.getText().isEmpty() || customerphone.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Empty field is not acceptable!");
        }else if(!customerphone.getText().matches(phnRegex)){
            JOptionPane.showMessageDialog(null,"Phone is not valid.");
        }else{
            String sql ="Insert into CustomerTable(customerName,customerPhone) values(?,?)";
            
          try {
               
           
            DBConnect();
            
            ps = connection.prepareStatement(sql);
            
            ps.setString(1, customername.getText());
            ps.setString(2, customerphone.getText());
            
            
            ps.execute();
            
            Clear();
            
            
           JOptionPane.showMessageDialog(null,"Info saved successfully!");
           
           //orderId();


        } catch (ClassNotFoundException ex) {
             Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(ex.getMessage());
                a.show();
        }
    }
    }
    
    public void OrderTable() throws ClassNotFoundException, SQLException{
         
            
            int rand = ThreadLocalRandom.current().nextInt(100, 999 + 1);
            String randomNum = Integer.toString(rand);

            SimpleDateFormat formatter = new SimpleDateFormat("yyMdd");
            java.util.Date currDate = Calendar.getInstance().getTime();
            String date = formatter.format(currDate);
            
            currentdate.setText(date);

            String num = date + randomNum;
            int ordernum = Integer.parseInt(num);
        
        
       
        
        //JOptionPane.showMessageDialog(null,"Info saved successfully!");
        
    }*/
    
    /*public void orderId() throws ClassNotFoundException, SQLException{
    String sql="Select orderid from OrderTable";
     DBConnect();
     ps=connection.prepareStatement(sql);
     rs=ps.executeQuery();
    
     while(rs.next()){
         //orderid.setText();
        JOptionPane.showMessageDialog(null,"Info saved successfully!");
     }
        
    
    

}*/
    
    
    
    public void DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        connection = DriverManager.getConnection(URL);
         System.out.println("Connected database successfully.........");
    }
    
    public void Clear()
    {
        
        customername.clear();
        customerphone.clear();
    }

   

    @FXML
    private void CustomerInfoAction(ActionEvent event) throws IOException {
                    btnCustInfo.getScene().getWindow().hide();
                    
                    Parent custinfo=FXMLLoader.load(getClass().getResource("CustomerInfo.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(custinfo);
                    mainstage.setScene(scn);
                    mainstage.show();
    }

  
    @FXML
    private void logoutbtnAction(ActionEvent event) throws IOException {
        
        int returnValue = JOptionPane.showConfirmDialog(null, "Want to exit?", "Are You Sure?", JOptionPane.YES_NO_OPTION);
        System.out.println(returnValue);

        if (returnValue == JOptionPane.YES_OPTION) {
        
                    btnlogout.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("LogIn.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
        }
    }

    @FXML
    private void ProfileAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        
                    btnprofile.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("EmployeeInfo.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
    }

    @FXML
    private void MenubtnAction(ActionEvent event) throws IOException {
        
                    menubtn.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("/MenuItemsEmployee/ViewMenu.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
    }

    @FXML
    private void posAction(ActionEvent event) throws IOException {
        
        posbtn.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("POS.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
        
    }

 
    
}
