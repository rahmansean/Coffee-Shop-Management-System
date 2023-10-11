
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuItemsAdmin;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class OperateMenuController implements Initializable {

    @FXML
    private TableColumn<OperateMenuModel, String> itemsID;
    @FXML
    private TableColumn<OperateMenuModel, String> itemsName;
    @FXML
    private TableColumn<OperateMenuModel, Float> itemsCost;
    @FXML
    private TableColumn<OperateMenuModel, Integer> itemsQuantity;
    @FXML
    private TableColumn<OperateMenuModel, String> itemsCategory;
    @FXML
    private TableColumn<OperateMenuModel, String> itemsDescription;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXButton deleteBtn;
    @FXML
    private JFXButton refreshBtn;
    @FXML
    private TableView<OperateMenuModel> OperateMenuTable;
    @FXML
    private Button btnback;

    private ObservableList<OperateMenuModel> data;
    public static String name;
    public static String id;

    Connection con;
    PreparedStatement pst;
    ResultSet rs1;
    @FXML
    private ImageView tableImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            databaseRecord();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OperateMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OperateMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addBtnAction(ActionEvent event) throws IOException {
        Pane view = new FXMLLoader().load(getClass().getResource("/MenuItemsAdmin/AddItems.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(view));
        stage.show();

    }

    @FXML
    private void updateBtnAction(ActionEvent event) throws IOException {

        if (OperateMenuTable.getSelectionModel().getSelectedItem() != null) {

            OperateMenuModel data = OperateMenuTable.getSelectionModel().getSelectedItem();
            name = data.getItemName();
             Pane view = new FXMLLoader().load(getClass().getResource("/MenuItemsAdmin/UpdateItems.fxml"));
             Stage stage = new Stage();
             stage.setScene(new Scene(view));
             stage.show();
 
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please select a value.");
            a.show();
        }
    }

    @FXML
    private void deleteBtnAction(ActionEvent event) throws ClassNotFoundException {
          if (OperateMenuTable.getSelectionModel().getSelectedItem() != null) {
             OperateMenuModel data = OperateMenuTable.getSelectionModel().getSelectedItem();
             String id = data.itemID;
            try {
                Connection con;

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
                con = DriverManager.getConnection(connectionUrl);

                String sql = "delete from ItemsTable where itemID='" + id + "'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                con.close();

                OperateMenuTable.getItems().remove(data);
                System.out.println("SuccessfulI");

                 Alert a = new Alert(Alert.AlertType.INFORMATION);
                 a.setContentText("Removed data successfully.");
                 a.show();
                
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

    @FXML
    private void backAction(ActionEvent event) throws IOException {

        btnback.getScene().getWindow().hide();
        Parent empDashboard = FXMLLoader.load(getClass().getResource("/coffeeshop/AdminDashboard.fxml"));
        Stage mainstage = new Stage();
        Scene scn = new Scene(empDashboard);
        mainstage.setScene(scn);
        mainstage.show();
    }

    private void databaseRecord() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT itemID,itemName,itemCost,itemQuantity,itemCategory,itemImage,itemdescription FROM ItemsTable");

            while (rs.next()) {

                data.add(new OperateMenuModel(rs.getString("itemID"), rs.getString("itemName"), rs.getFloat("itemCost"),
                        rs.getInt("itemQuantity"), rs.getString("itemCategory"),rs.getString("itemdescription")));
            }

            itemsID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
            itemsName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            itemsCost.setCellValueFactory(new PropertyValueFactory<>("itemCost"));
            itemsQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
            itemsCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));   
            itemsDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
            OperateMenuTable.setItems(null);
            OperateMenuTable.setItems(data);

            con.close();

        } catch (Exception ex) {

            System.out.println("Error Occured! =" + ex.getMessage());
        }
    }

    @FXML
    private void ontableClicked(MouseEvent event) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();

        if (OperateMenuTable.getSelectionModel().getSelectedItem() != null) {
            OperateMenuModel data = OperateMenuTable.getSelectionModel().getSelectedItem();
            id = data.getItemID();
            System.out.println("Selected Id" + id);
            try {

                ResultSet rs = con.createStatement().executeQuery("SELECT itemImage FROM ItemsTable WHERE itemID ='" + id + "'");
                String sql = "SELECT itemImage FROM ItemsTable WHERE itemID ='" + id + "'";
                System.out.println(sql);

                if (rs.next()) {

                    File file = new File(rs.getString("itemImage"));
                    //byte[] bytes = new byte[(int) file.length()];
                    //FileInputStream fis = new FileInputStream(file);
                    //fis.read(bytes);
                    //fis.close();
                    //String ef = Base64.getEncoder().encodeToString(bytes);
                    Image image = new Image(file.toURI().toString());
                    tableImage.setImage(image);
                }
            } catch (Exception ex) {

                System.out.println("Error Occured! =" + ex.getMessage());
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please select a value.");
            a.show();
        }

    }

}