/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop;

import static coffeeshop.LogInController.adminprofile;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AdminDashboardController implements Initializable {
    PreparedStatement ps=null;
    Connection connection=null;
    ResultSet rs=null;

    @FXML
    private ImageView exit;
    @FXML
    private JFXButton btnreport;
    @FXML
    private JFXButton empBtn;
    @FXML
    private JFXButton btnlogout;
    private JFXButton btnmenu;
    @FXML
    private JFXButton btnadminmenu;
    @FXML
    private Label adminname;
    @FXML
    private Label adminmail;
    @FXML
    private JFXButton saleschart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ProfileName();
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void ReportbtnAction(ActionEvent event) throws IOException {
                    btnreport.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("ReportInfo.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
    }

    @FXML
    private void empBtnAction(ActionEvent event) throws IOException {
        
        empBtn.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("/EmployeesTable/EmployeesInfoTable.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
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
    private void MenuadminAction(ActionEvent event) throws IOException {
        
         btnadminmenu.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("/MenuItemsAdmin/OperateMenu.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
    }
    
    
    public void ProfileName() throws SQLException, ClassNotFoundException
    {
        
        DBConnect();
        String query = "select adminName,adminEmail from AdminTable";
        
        

        Statement stm = connection.createStatement();

        ResultSet rs = stm.executeQuery(query);
        while(rs.next())
        {
            if(rs.getString("adminEmail") == null ? adminprofile == null : rs.getString("adminEmail").equals(adminprofile))
            {
                 adminname.setText(rs.getString("adminName"));
                 adminmail.setText(rs.getString("adminEmail"));
               
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
    private void salesChartAction(ActionEvent event) throws IOException {
        
                    saleschart.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("SalesInfo.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
    }
    
}
