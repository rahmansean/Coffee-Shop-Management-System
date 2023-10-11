
package MenuItemsEmployee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.ImageIcon;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ViewMenuController implements Initializable {

    @FXML
    private TableColumn<ViewMenuModel, String> itemsID;
    @FXML
    private TableColumn<ViewMenuModel, String> itemsName;
    @FXML
    private TableColumn<ViewMenuModel, Float> itemsCost;
    @FXML
    private TableColumn<ViewMenuModel, Integer> itemsQuantity;
    @FXML
    private TableColumn<ViewMenuModel, String> itemsCategory;
    @FXML
    private TableColumn<ViewMenuModel, String> itemsDescription;
    @FXML
    private TableView<ViewMenuModel> ViewMenuTable;
    private ObservableList<ViewMenuModel> data;

    Connection con;
    PreparedStatement pst;
    ResultSet rs1;
    @FXML
    private ImageView tableImage;
    @FXML
    private Button btnback;
    String id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            databaseRecord();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ViewMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void databaseRecord() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT itemID,itemName,itemCost,itemQuantity,itemCategory,itemImage,itemdescription FROM ItemsTable");

            while (rs.next()) {

                data.add(new ViewMenuModel(rs.getString("itemID"), rs.getString("itemName"), rs.getFloat("itemCost"),
                        rs.getInt("itemQuantity"), rs.getString("itemCategory"), rs.getString("itemdescription")));

            }

            System.out.println("Data fetched");

            itemsID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
            itemsName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            itemsCost.setCellValueFactory(new PropertyValueFactory<>("itemCost"));
            itemsQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
            itemsCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
            itemsDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
            ViewMenuTable.setItems(null);
            ViewMenuTable.setItems(data);
            System.out.println("Data showed");
            con.close();

        } catch (Exception ex) {

            System.out.println("Error Occured! =" + ex.getMessage());
        }
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        btnback.getScene().getWindow().hide();

        Parent empDashboard = FXMLLoader.load(getClass().getResource("/coffeeshop/EmpDashboard.fxml"));
        Stage mainstage = new Stage();
        Scene scn = new Scene(empDashboard);
        mainstage.setScene(scn);
        mainstage.show();
    }

    @FXML
    private void ontableClicked(MouseEvent event) throws ClassNotFoundException, SQLException {
       
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();

        if (ViewMenuTable.getSelectionModel().getSelectedItem() != null) {
            ViewMenuModel data = ViewMenuTable.getSelectionModel().getSelectedItem();
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

    
              