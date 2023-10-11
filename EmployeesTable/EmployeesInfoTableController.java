
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeesTable;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class EmployeesInfoTableController implements Initializable {

    @FXML
    private TableView<EmployeesTableModel> EmployeesTable;
    @FXML
    private TableColumn<EmployeesTableModel,String> employeesID;
    @FXML
    private TableColumn<EmployeesTableModel,String> employeesName;
    @FXML
    private TableColumn<EmployeesTableModel,String> employeesEmail;
    @FXML
    private TableColumn<EmployeesTableModel,String> employeesPhone;
    @FXML
    private TableColumn<EmployeesTableModel,String> employeesAddress;
    @FXML
    private TableColumn<EmployeesTableModel,String> employeesGender;
   
    private ObservableList<EmployeesTableModel> data;
    
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs1;
    
    public static String email;
    public static String phone;
    @FXML
    private JFXButton backBtn;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXButton deleteBtn;
    @FXML
    private JFXButton refreshBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            // TODO
            databaseRecord();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeesInfoTableController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeesInfoTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void databaseRecord() throws ClassNotFoundException, SQLException  {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();
        
        try{
        ResultSet rs = con.createStatement().executeQuery
        ("SELECT employeeID,employeeName,employeeEmail,employeeAddress,employeePhone,employeeGender FROM employeeTable");

        while (rs.next()) {

            data.add(new EmployeesTableModel(rs.getInt("employeeID"),rs.getString("employeeName"),rs.getString("employeeEmail"),
                    rs.getString("employeeAddress"),rs.getString("employeePhone"),rs.getString("employeeGender")));
      
        }
        employeesID.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeesEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeesAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        employeesPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        employeesGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        
        EmployeesTable.setItems(null);
        EmployeesTable.setItems(data);
        
        con.close();
            
        }  
        catch(Exception ex){
     
         System.out.println("Error Occured! ="+ex.getMessage());
     }
    }
    
    

    @FXML
    private void backBtnAction(ActionEvent event) throws IOException {
        Parent Menu = FXMLLoader.load(getClass().getResource("/coffeeshop/AdminDashboard.fxml"));
                Scene scr = new Scene(Menu);
                Stage menuPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                menuPage.setScene(scr);
                menuPage.setTitle("User Log");
                menuPage.show();
    }

    @FXML
    private void updateBtnAction(ActionEvent event) throws IOException {
        
        if (EmployeesTable.getSelectionModel().getSelectedItem() != null) {

            
            EmployeesTableModel data = EmployeesTable.getSelectionModel().getSelectedItem();
            email = data.getEmail();
            
            Pane view = new FXMLLoader().load(getClass().getResource("/EmployeesUpdate/UpdateEmployeeInfo.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(view));
            stage.show();
            
            
            /*Parent Menu = FXMLLoader.load(getClass().getResource("/EmployeesUpdate/UpdateEmployeeInfo.fxml"));
            Scene scr = new Scene(Menu);
            Stage menuPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            menuPage.setScene(scr);
            menuPage.setTitle("User Log");
            menuPage.show();*/

        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please select a value.");
            a.show();
        }
    }

    
    @FXML
    private void deleteBtnAction(ActionEvent event) throws ClassNotFoundException {

        if (EmployeesTable.getSelectionModel().getSelectedItem() != null) {
             EmployeesTableModel data = EmployeesTable.getSelectionModel().getSelectedItem();
             String name = data.getName();
            try {
                Connection con;

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
                con = DriverManager.getConnection(connectionUrl);

                String sql = "delete from EmployeeTable where employeeName='" + name + "'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                con.close();

                EmployeesTable.getItems().remove(data);
                System.out.println("SuccessfulI");

               /* Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Removed data from Database.");
                a.show();*/
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Alert!");
                a.setHeaderText("Successfull");
                a.setContentText("Removed data from Database successfully.");
                a.showAndWait();
                
            } catch (SQLException ex) {
                System.out.println("Error:" + ex);

            }
        }
         else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Please select an item.");
                a.show();
                }
    }

    @FXML
    private void refreshBtnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        databaseRecord();
    }

}

        




              
  