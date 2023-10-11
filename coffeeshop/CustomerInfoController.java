/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class CustomerInfoController implements Initializable {
    
    Connection connection = null;
    Statement stmt;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
   
    private ObservableList<findCustomer> datalist;

    @FXML
    private TableView<findCustomer> custTableView;

    @FXML
    private TableColumn<findCustomer, String> custid;

    @FXML
    private TableColumn<findCustomer, String> custname;

    @FXML
    private TableColumn<findCustomer, String> custphone;
    @FXML
    private TextField searchid;
    @FXML
    private Button btnrefresh;
    @FXML
    private TableColumn<?, ?> custphone1;
    @FXML
    private TableColumn<?, ?> custphone2;
    @FXML
    private TableColumn<?, ?> custphone21;
    @FXML
    private Button btnback;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            
            DBConnect();
            datalist = FXCollections.observableArrayList();
                setCellTable();
                loaddataFromDatabase();
                searchCustomerInfo();
                //search_customer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerInfoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }

                
        
    }   
    
    
        private void setCellTable() {
        custid.setCellValueFactory(new PropertyValueFactory<>("custid"));
        custname.setCellValueFactory(new PropertyValueFactory<>("custname"));
        custphone.setCellValueFactory(new PropertyValueFactory<>("custphone"));
        

    }

    private void loaddataFromDatabase() throws SQLException {
        datalist.clear();
         String sql="select * from CustomerTable";
        pst = connection.prepareStatement(sql);
        
        rs = pst.executeQuery();
        
        while (rs.next()) {
            datalist.add(new findCustomer(rs.getInt(1),rs.getString(2),rs.getString(3)));

        }
      custTableView.setItems(null);  
      custTableView.setItems(datalist);
    }

    private void searchCustomerInfo() throws SQLException {

        String searchValue = searchid.getText();

        searchid.setOnKeyReleased(e -> {
            if (searchid.getText().equals("")) {
                try {
                    loaddataFromDatabase();
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerInfoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                datalist.clear();

                String query = "SELECT customerID,customerName,customerPhone FROM CustomerTable WHERE customerID LIKE '%" + searchid.getText() + "%'"
                        + "OR customerName LIKE '%" + searchid.getText() + "%'" + "OR customerPhone LIKE '%" + searchid.getText() + "%'";

                try {

                    Statement stm = connection.createStatement();
                    ResultSet rs = stm.executeQuery(query);

                    while (rs.next()) {

                        datalist.add(new findCustomer(rs.getInt(1),rs.getString(2), rs.getString(3)));
                    }

                    custTableView.setItems(datalist);
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerInfoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

    }
    
    @FXML
    private void refreshAction(ActionEvent event) throws SQLException {
        loaddataFromDatabase();
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
}


 