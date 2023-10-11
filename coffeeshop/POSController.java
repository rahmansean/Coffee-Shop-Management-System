/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop;

import EmployeesTable.EmployeesTableModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class POSController implements Initializable {
    PreparedStatement ps=null;
    Connection connection=null;
    ResultSet rs=null;
    int qty;
    float discountPrice=0;
     private ObservableList<orderTable> datalist;
    @FXML
    private JFXButton btnsave;
    @FXML
    private JFXTextField customername;
    @FXML
    private JFXTextField customerphone;
    @FXML
    private Button btnback;
    @FXML
    private TableView<orderTable> orderTableview;
    private TableColumn<orderTable, String> itemnameView;
    private TableColumn<orderTable, String> priceView;
    private TableColumn<orderTable, String> qtyView;
    private TableColumn<orderTable, String> totalcostView;
    @FXML
    private JFXButton searchitem;
    @FXML
    private TextField itemid;
    @FXML
    private JFXTextField itemname;
    @FXML
    private JFXTextField itemcost;
    @FXML
    private JFXButton addCart;
   
    @FXML
    private Label orderdate;
    private Label orderTime;
    @FXML
    private Label delivertime;
    int orderNo;
    String payment;
    @FXML
    private JFXTextField total;
    @FXML
    private Spinner qtySpinner;

    @FXML
    private JFXTextField totalitemprice;
    @FXML
    private ComboBox paymentMethodID;
    private Label receivetime;
    @FXML
    private TableColumn<?, ?> itemsname;
    @FXML
    private TableColumn<?, ?> itemprice;
    @FXML
    private TableColumn<?, ?> itemquantity;
    @FXML
    private TableColumn<?, ?> totalcost;
   
   
    @FXML
    private TextField ordid;
    int orderNum;
    @FXML
    private JFXTextField discountp;
    @FXML
    private JFXTextField subtotalCost;
    
    @FXML
    private JFXButton categorynum1;
    @FXML
    private JFXComboBox combobox;
    @FXML
    private JFXButton deleteitem;
    @FXML
    private JFXButton categorynum2;
    @FXML
    private JFXButton categorynum3;
    @FXML
    private JFXButton categorynum4;
    @FXML
    private JFXButton categorynum5;
    @FXML
    private JFXButton categorynum6;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        paymentMethodID.getItems().add("CASH");
        paymentMethodID.getItems().add("BKASH");
        datalist = FXCollections.observableArrayList();
       
        try {
            // TODO

            orderTable();
            
        } catch (SQLException ex) {
            Logger.getLogger(POSController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(POSController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void SaveAction(ActionEvent event) throws SQLException {
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
    
    
    public void orderTable() throws SQLException, ClassNotFoundException {
        DBConnect();
        

        this.orderdate.setText(LocalDate.now().toString());
        String ordate=LocalDate.now().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        String ordtime=LocalDateTime.now().format(formatter);
        //orderTime.setText(ordtime);
        
        //receivetime.setText(ordtime);
        
      
        formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        this.delivertime.setText(LocalDateTime.now().format(formatter));
        
        
       
        
        
        SpinnerValueFactory<Integer> gradesvaluefactory= new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1);
        this.qtySpinner.setValueFactory(gradesvaluefactory);
        qtySpinner.setEditable(true);
        
        
        qtySpinner.valueProperty().addListener(new ChangeListener<Integer>(){
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
               
                  qty= (int) qtySpinner.getValue();
                 
                 
                 float price = Integer.valueOf(itemcost.getText());
        
                 float totalprice=price*qty;
                 totalitemprice.setText(String.valueOf(totalprice));
                 //totalitemprice.setText(Integer.toString(totalprice));
            }
   
         });
        
       
        int rand = ThreadLocalRandom.current().nextInt(100, 999 + 1);
        String randomNum = Integer.toString(rand);

        SimpleDateFormat formatt = new SimpleDateFormat("yyM");
        java.util.Date currDat = Calendar.getInstance().getTime();
        String dat = formatt.format(currDat);

        String number = dat + randomNum;
        orderNum = Integer.parseInt(number);
         
         
        ordid.setText(String.valueOf(orderNum));
       
    }
    
     
    
    
    public void Clear()
    {
        
        customername.clear();
        customerphone.clear();
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

    @FXML
    private void searchItemAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        
        
        String itemNAME=(String) combobox.getValue();
        
         DBConnect();
         String sql="select itemId,itemName,itemCost from ItemsTable where itemName=?";
        
        ps=connection.prepareStatement(sql);
        ps.setString(1, itemNAME);
        rs=ps.executeQuery();
        while(rs.next()){
            itemid.setText(rs.getString("itemId"));
            itemname.setText(rs.getString("itemName"));
            itemcost.setText(rs.getString("itemCost"));
            
        }
        
        
        
        
    }

    @FXML
    private void addcartAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        
        if(itemid.getText()==null || itemname.getText()==null || qtySpinner.getValue()==null || itemcost.getText()==null ||
                totalitemprice.getText()==null || paymentMethodID.getValue()==null){
            
            JOptionPane.showMessageDialog(null,"Please filled the all fields!");
        }else{
            
            int rand = ThreadLocalRandom.current().nextInt(100, 999 + 1);
        String randomNum = Integer.toString(rand);

        SimpleDateFormat formatt = new SimpleDateFormat("yyMdd");
        java.util.Date currDate = Calendar.getInstance().getTime();
        String date = formatt.format(currDate);

        String num = date + randomNum;
        int order_num = Integer.parseInt(num);
        
         String query="Insert into OrderTable(orderId,orderDate,discount,quantity,recieveTime,deliverTime,totalCost,paymentMethod,itemID)"
                + "values(" + order_num + ",'" + LocalDate.now().toString() + "'," + discountPrice + "," + qty + ",'" + LocalDate.now().toString()
                + "','" + LocalDate.now().toString() + "'," + totalitemprice.getText() + ",'" + paymentMethodID.getValue()+ "'," + itemid.getText()+ ")";
        
       Statement stm = connection.createStatement();

        int i=stm.executeUpdate(query);
                
        if(i==1){
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Completed");
            a.show();
       
        }
        

        loadfromDatabase();
        

      String qu="select sum(totalCost) as totalPrice from OrderTable";
      ps=connection.prepareStatement(qu);
       rs=ps.executeQuery();
      if(rs.next()){
          total.setText(rs.getString("totalPrice"));
      }
        
        }
            
            
        clear();
        
    }


    public void loadfromDatabase() throws ClassNotFoundException, SQLException{
         datalist.clear();
         

        DBConnect();
        orderTableview.getItems().removeAll();
        String sql="select ItemsTable.itemName,ItemsTable.itemCost,OrderTable.quantity,OrderTable.totalCost from OrderTable INNER JOIN ItemsTable ON OrderTable.itemID=ItemsTable.itemID ";
        ps=connection.prepareStatement(sql);
        rs=ps.executeQuery();
        
       while (rs.next()) {
            datalist.add(new orderTable(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getFloat(4)));

        }
       
       
        //orderid.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        itemsname.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        itemprice.setCellValueFactory(new PropertyValueFactory<>("itemCost"));
        itemquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalcost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
       
      orderTableview.setItems(null);  
      orderTableview.setItems(datalist);
      
      
      
      
        
    }

    private void onaction(ActionEvent event) {
        
         if(combobox.getSelectionModel().getSelectedItem()!=null)
        {
             System.out.println("Selected"+combobox.getSelectionModel().getSelectedItem());
             //label.setText(combobox.getSelectionModel().getSelectedItem().toString());
         
        }
       
    }

    @FXML
    private void contentOne(ActionEvent event) {
        
  
        try {
            DBConnect();

                String query = categorynum1.getText();
            System.out.println(query);
            ResultSet rs = connection.createStatement().executeQuery("SELECT itemName FROM ItemsTable WHERE itemCategory ='" + query + "'");
            //String sql = "SELECT itemImage FROM ItemsTable WHERE itemID ='" + query + "'";
            //System.out.println(sql);

            ObservableList options = FXCollections.observableArrayList();
         
            while (rs.next()) {

          
                options.add(rs.getString("itemName"));

            }
            combobox.setItems(options);
            
        } catch (Exception e) {
            System.out.println("Error" +e);
        }
        
         
    }


public void clear(){
    itemid.setText("");
    itemname.setText("");
    itemcost.setText("");
    //qtySpinner.setValueFactory(null);
    totalitemprice.setText("");
    paymentMethodID.setValue(null);
    
}

    @FXML
    private void deleteItemAction(ActionEvent event) throws ClassNotFoundException {
        
        if (orderTableview.getSelectionModel().getSelectedItem() != null) {
             orderTable data = orderTableview.getSelectionModel().getSelectedItem();
             float cost = data.totalCost;
            try {
                 DBConnect();
                String sql = "delete from orderTable where totalCost='"+cost+"'";
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(sql);
   
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Removed data from Database.");
                a.show();
                
                orderTableview.getItems().remove(data);
                
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
    private void contentTwo(ActionEvent event) {
         try {
            DBConnect();

                String query = categorynum2.getText();
            System.out.println(query);
            ResultSet rs = connection.createStatement().executeQuery("SELECT itemName FROM ItemsTable WHERE itemCategory ='" + query + "'");
            //String sql = "SELECT itemImage FROM ItemsTable WHERE itemID ='" + query + "'";
            //System.out.println(sql);

            ObservableList options = FXCollections.observableArrayList();
         
            while (rs.next()) {

          
                options.add(rs.getString("itemName"));

            }
            combobox.setItems(options);
            
        } catch (Exception e) {
            System.out.println("Error" +e);
        }
        
    }

    @FXML
    private void contentThree(ActionEvent event) {
         try {
            DBConnect();

                String query = categorynum3.getText();
            System.out.println(query);
            ResultSet rs = connection.createStatement().executeQuery("SELECT itemName FROM ItemsTable WHERE itemCategory ='" + query + "'");
            //String sql = "SELECT itemImage FROM ItemsTable WHERE itemID ='" + query + "'";
            //System.out.println(sql);

            ObservableList options = FXCollections.observableArrayList();
         
            while (rs.next()) {

          
                options.add(rs.getString("itemName"));

            }
            combobox.setItems(options);
            
        } catch (Exception e) {
            System.out.println("Error" +e);
        }
        
    }
    

    @FXML
    private void contentFour(ActionEvent event) {
         try {
            DBConnect();

                String query = categorynum4.getText();
            System.out.println(query);
            ResultSet rs = connection.createStatement().executeQuery("SELECT itemName FROM ItemsTable WHERE itemCategory ='" + query + "'");
            //String sql = "SELECT itemImage FROM ItemsTable WHERE itemID ='" + query + "'";
            //System.out.println(sql);

            ObservableList options = FXCollections.observableArrayList();
         
            while (rs.next()) {

          
                options.add(rs.getString("itemName"));

            }
            combobox.setItems(options);
            
        } catch (Exception e) {
            System.out.println("Error" +e);
        }
        
    }

    @FXML
    private void contentFive(ActionEvent event) {
        try {
            DBConnect();

                String query = categorynum5.getText();
            System.out.println(query);
            ResultSet rs = connection.createStatement().executeQuery("SELECT itemName FROM ItemsTable WHERE itemCategory ='" + query + "'");
            //String sql = "SELECT itemImage FROM ItemsTable WHERE itemID ='" + query + "'";
            //System.out.println(sql);

            ObservableList options = FXCollections.observableArrayList();
         
            while (rs.next()) {

          
                options.add(rs.getString("itemName"));

            }
            combobox.setItems(options);
            
        } catch (Exception e) {
            System.out.println("Error" +e);
        }
        
    }
     

    @FXML
    private void contentSix(ActionEvent event) {
         try {
            DBConnect();

                String query = categorynum6.getText();
            System.out.println(query);
            ResultSet rs = connection.createStatement().executeQuery("SELECT itemName FROM ItemsTable WHERE itemCategory ='" + query + "'");
            //String sql = "SELECT itemImage FROM ItemsTable WHERE itemID ='" + query + "'";
            //System.out.println(sql);

            ObservableList options = FXCollections.observableArrayList();
         
            while (rs.next()) {

          
                options.add(rs.getString("itemName"));

            }
            combobox.setItems(options);
            
        } catch (Exception e) {
            System.out.println("Error" +e);
        }
        
    }
   
    
}
