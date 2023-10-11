/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ReportInfoController implements Initializable {
    
    Connection connection = null;
    Statement stmt;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    @FXML
    private Label dailysales;
    @FXML
    private Button btnback;
  
    private TableColumn<salesReport, String> customername;
    private TableColumn<salesReport, String> customerphone;
    @FXML
    private TableColumn<salesReport, String> orderdate;
    @FXML
    private TableColumn<salesReport, String> itemname;
    @FXML
    private TableColumn<salesReport, String> totalcost;
    
    private ObservableList<salesReport> datalist;
    @FXML
    private TableView<salesReport> salesReportTable;
    private TableColumn<salesReport, String> createdby;
    private TableColumn<salesReport, String> paymentStatus;
    @FXML
    private TableColumn<salesReport, String> orderid;
    @FXML
    private TableColumn<?, ?> itemcategory;

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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportInfoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    
       private void setCellTable() {
        orderid.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderdate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        itemname.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemcategory.setCellValueFactory(new PropertyValueFactory<>("itemcategory"));
        totalcost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        
        
        

    }

    private void loaddataFromDatabase() throws SQLException {
        datalist.clear();
         String sql="SELECT OrderTable.orderID,OrderTable.orderDate,ItemsTable.itemName,ItemsTable.itemCategory,\n" +
                    "OrderTable.totalCost FROM OrderTable INNER JOIN ItemsTable ON OrderTable.itemID=ItemsTable.itemID ;";
        pst = connection.prepareStatement(sql);
        
        rs = pst.executeQuery();
        
        while (rs.next()) {
            datalist.add(new salesReport(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getFloat(5)));

        }
      salesReportTable.setItems(null);  
      salesReportTable.setItems(datalist);
      
      String qu="select sum(totalCost) as grandTotal from Ordertable";
      pst=connection.prepareStatement(qu);
      rs=pst.executeQuery();
      
      if(rs.next()){
          dailysales.setText(rs.getString("grandTotal"));
      }
    }
    
    
    @FXML
    private void backAction(ActionEvent event) throws IOException {
                    btnback.getScene().getWindow().hide();
                    
                    Parent empDashboard=FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
                    Stage mainstage=new Stage();
                    Scene scn=new Scene(empDashboard);
                    mainstage.setScene(scn);
                    mainstage.show();
    }
    
    
    public void DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        connection = DriverManager.getConnection(URL);
         System.out.println("Connected database successfully.........");
    }
    
}
