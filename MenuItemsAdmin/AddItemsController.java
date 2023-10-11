
package MenuItemsAdmin;

import com.jfoenix.controls.JFXButton;
import javafx.scene.image.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;



public class AddItemsController implements Initializable {

    @FXML
    private TextField itemName;
    @FXML
    private Button confirmBtn;
    @FXML
    private TextField itemCost;
    @FXML
    private TextField itemQuantity;
    @FXML
    private TextField itemCategory;
    @FXML
    private TextField itemDescription;
    
    @FXML
    private Button btnback;
    private ImageView itemImageShow;
    @FXML
    private JFXButton uploadItemImage;
    @FXML
    private Label itemImageUrl;
   
    File file;
    

    @FXML
    private Label itemImageLabel;
    @FXML
    private ImageView itemImage;
    
    final FileChooser chooser=new FileChooser();
    @FXML
    private AnchorPane addItemsAnchor;
    String path=null;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    


    @FXML
    private void confirmBtnAction(ActionEvent event) {
        try {
            Connection con;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=COFFEESHOP;";
            con = DriverManager.getConnection(connectionUrl);
            Statement stmt = con.createStatement();

            if (!itemName.getText().isEmpty() && !itemCost.getText().isEmpty() && !itemQuantity.getText().isEmpty() && !itemCategory.getText().isEmpty() && !itemImageUrl.getText().isEmpty() && !itemDescription.getText().isEmpty()) {

                try {
                    float f = Float.parseFloat(itemCost.getText());
                    int q = Integer.parseInt(itemQuantity.getText());
                } catch (Exception e) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText("Please insert number in Cost and Quantity field.");
                    a.show();
                }
                String sql = "insert into ItemsTable(itemName,itemCost,itemQuantity,itemCategory,itemImage,itemdescription) "
                        + "VALUES('" + itemName.getText() + "','" + itemCost.getText() + "','"
                        + itemQuantity.getText() + "','" + itemCategory.getText() + "','" + itemImageUrl.getText() + "','" + itemDescription.getText() + "')";

                System.out.println("Insert" + sql);
                stmt.executeUpdate(sql);
                addItemsAnchor.getScene().getWindow().hide();

                System.out.println("Inserted");
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Alert!");
                a.setHeaderText("Successfull");
                a.setContentText("Data inserted Successfully.");
                a.showAndWait();
                //a.show();
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Please fillup all fields.");
                a.show();
            }
        } catch (Exception ex) {
            System.out.println("Error while updating " + ex.getMessage());
        }
    }

    /*
    @FXML
    private void backAction(ActionEvent event) throws IOException {
        Parent Menu = FXMLLoader.load(getClass().getResource("/MenuItemsAdmin/OperateMenu.fxml"));
                Scene scr = new Scene(Menu);
                Stage menuPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                menuPage.setScene(scr);
                menuPage.setTitle("User Log");
                menuPage.show();
    }
    */
    
 
    @FXML
    private void uploadItemImageAction(ActionEvent event) throws FileNotFoundException, IOException {
      
        chooser.setTitle("My file chooser");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().clear();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        File file=chooser.showOpenDialog(null);
        
        if(file!=null)
        {
            
            path=file.getAbsolutePath();   
            file = new File(path);
            itemImageUrl.setText(path);
            Image image = new Image(file.toURI().toString());
            itemImage.setImage(image);
            
        }
        else
        {
             Alert a = new Alert(Alert.AlertType.WARNING);
             a.setContentText("Choose an image. Only .JPG, .PNG, .GIf File type expected.");
             a.show();
             System.out.println("Invalid");
        }
                
    }

    @FXML
    private void imageChoose(MouseEvent event) {
    }

    @FXML
    private void backAction(ActionEvent event) {
    }


}
   
               
               