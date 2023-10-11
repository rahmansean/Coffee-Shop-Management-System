
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeesUpdate;
import static EmployeesTable.EmployeesInfoTableController.email;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateEmployeeInfoController implements Initializable {

    @FXML
    private TextField employeeName;
    @FXML
    private Button confirmBtn;
    @FXML
    private TextField employeeEmail;
    @FXML
    private TextField employeeAddress;
    @FXML
    private TextField employeePhone;
    @FXML
    private JFXComboBox<String> employeeGender;
    ObservableList<String>genderList=FXCollections.observableArrayList("Male","Female");
    
    @FXML
    private AnchorPane UpdateEmployeeAnchor;
    private Object EmployeesTable;
    
     String emailRegex = "^(.+)@(\\S+)$";  
    Object selectedItem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeeGender.setItems(genderList);
      
    }    



    @FXML
    private void confirmBtnAction(ActionEvent event) {
  
          try{
            Connection con;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
            con = DriverManager.getConnection(connectionUrl);
            Statement stmt= con.createStatement();
            
              if (!employeeName.getText().isEmpty() && !employeeEmail.getText().isEmpty() && !employeeAddress.getText().isEmpty() && !employeePhone.getText().isEmpty() && !(employeeGender.getValue() == null)) {

                  selectedItem = employeeGender.getSelectionModel().getSelectedItem();
                  boolean nextPhase = checkInput();
                  if (nextPhase) {
                      String sql = "update EmployeeTable set employeeName='" + employeeName.getText() + "' , employeeEmail='" + employeeEmail.getText() + "', employeeAddress='" + employeeAddress.getText() + "', employeePhone='" + employeePhone.getText() + "', employeeGender='" + employeeGender.getValue() + "'where EmployeeEmail='" + email + "'";

                      System.out.println("Update" + sql);
                      stmt.executeUpdate(sql);
                      UpdateEmployeeAnchor.getScene().getWindow().hide();
                      System.out.println("Updated");

                      Alert a = new Alert(Alert.AlertType.INFORMATION);
                      a.setTitle("Alert!");
                      a.setHeaderText("Successfull");
                      a.setContentText("Database updated successfully.");
                      a.showAndWait();
                      
                      //a.show();
                  } else {
                      Alert a = new Alert(Alert.AlertType.WARNING);
                      a.setContentText("Correctly fillup Email/Address/Phone.");
                      a.show();
                  }
              } else {
                  Alert a = new Alert(Alert.AlertType.WARNING);
                  a.setContentText("Please fillup all fields.");
                  a.show();
              }
     }catch(Exception ex) {
         System.out.println("Error while updating"+ex.getMessage());
     }  
    }
    
    /*
    @FXML
    private void backBtnAction(ActionEvent event) throws IOException {
                Parent Menu = FXMLLoader.load(getClass().getResource("/EmployeesTable/EmployeesInfoTable.fxml"));
                Scene scr = new Scene(Menu);
                Stage menuPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                menuPage.setScene(scr);
                menuPage.setTitle("User Log");
                menuPage.show();
                
    }*/

    private boolean checkInput() {
        //if(employeeEmail.getText().matches(emailRegex) && ((employeePhone.getText().startsWith("01"))&&((employeePhone.getText().length()==11)) && ((employeeGender.getValue()=="Male")||(employeeGender.getValue()=="Female"))))
        if(employeeEmail.getText().matches(emailRegex) && ((employeePhone.getText().startsWith("01"))&&((employeePhone.getText().length()==11))) && ((selectedItem=="Male")||(selectedItem=="Female")))
        {
            return true;
        }
       return false;
    }


        
                   
    
}