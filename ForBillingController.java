/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojecthardwarestore;

import finalprojecthardwarestore.controller.BillController;
import finalprojecthardwarestore.controller.CustomerController;
import finalprojecthardwarestore.controller.ItemController;
import finalprojecthardwarestore.controller.ItemPurchaseController;
import finalprojecthardwarestore.controller.SupplierController;
import finalprojecthardwarestore.model.Bill;
import finalprojecthardwarestore.model.ItemPurchase;
import finalprojecthardwarestore.model.Supplier;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author DARK DEMON
 */
public class ForBillingController implements Initializable {

    @FXML
    private TextField qty;
    @FXML
    private TextField date;
    @FXML
    private TextField price;
    @FXML
    private TableView<Bill> table;
    @FXML
    private Button cancel;
    @FXML
    private TextField billId;
    @FXML
    private ComboBox<String> itemId;
    @FXML
    private ComboBox<String> custId;
    @FXML
    private TextField discount;
    //private TextField total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        date.setText(String.valueOf(formatter.format(todayDate)));
        try {
            loadTable();
            loadId();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PurchasingRecordsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Date todayDate = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    private void tableOnMouseClicked(MouseEvent event) {
        Bill bill = table.getSelectionModel().getSelectedItem();
        billId.setText(bill.getBill());

    }

    @FXML
    private void billOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        Bill bill = new Bill(
                billId.getText(),
                itemId.getValue().substring(0, 2),
                custId.getValue().substring(0, 2),
                Integer.parseInt(qty.getText()),
                date.getText(),
                Double.parseDouble(discount.getText()),
                Double.parseDouble(price.getText())
                //Double.parseDouble(total.getText())
        );
        boolean insertBill = BillController.insertBill(bill);
        if (insertBill) {
            System.out.println("Bill Added");
            loadTable();
        } else {
            System.out.println("Bill not deleted");
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        boolean deleteBill = BillController.deleteBill(billId.getText());
        if (deleteBill) {
            System.out.println("Bill Deleted");
            loadTable();
        } else {
            System.out.println("Bill not deleted");
        }
    }

    private void loadId() throws ClassNotFoundException, SQLException {
        List<String> allItemId = ItemController.getAllItemId();
        for (String string : allItemId) {
            string += " - " + ItemController.getItemName(string);
            itemId.getItems().add(string);
        }
        List<String> allCustomerId = CustomerController.getAllCustomerId();
        for (String string : allCustomerId) {
            string += " - " + CustomerController.getCustomerName(string);
            custId.getItems().add(string);
        }
    }

    private void loadTable() throws ClassNotFoundException, SQLException {
        table.setStyle("-fx-alignment: CENTER");
        int size = table.getItems().size();
        table.getItems().remove(0, size);
        int size1 = table.getColumns().size();
        table.getColumns().remove(0, size1);
        table.setStyle("-fx-cell-size: 25px");

        TableColumn<Bill, String> id = new TableColumn<>("Id");
        id.setMinWidth(150);
        id.setStyle("-fx-alignment: CENTER");
        id.setCellValueFactory(new PropertyValueFactory<>("bill"));

        TableColumn<Bill, String> Customer = new TableColumn<>("Customer");
        Customer.setMinWidth(150);
        Customer.setStyle("-fx-alignment: CENTER");
        Customer.setCellValueFactory(new PropertyValueFactory<>("cutomer"));

        TableColumn<Bill, String> item = new TableColumn<>("Item");
        item.setMinWidth(150);
        item.setStyle("-fx-alignment: CENTER");
        item.setCellValueFactory(new PropertyValueFactory<>("item"));

        TableColumn<Bill, Integer> qty = new TableColumn<>("Qty");
        qty.setMinWidth(150);
        qty.setStyle("-fx-alignment: CENTER");
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<Bill, Double> price = new TableColumn<>("Price");
        price.setMinWidth(150);
        price.setStyle("-fx-alignment: CENTER");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Bill, Double> discount = new TableColumn<>("Discount");
        discount.setMinWidth(150);
        discount.setStyle("-fx-alignment: CENTER");
        discount.setCellValueFactory(new PropertyValueFactory<>("discount"));

//        TableColumn<Bill, Double> total = new TableColumn<>("Total");
//        total.setMinWidth(150);
//        total.setStyle("-fx-alignment: CENTER");
//        total.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<Bill, String> date = new TableColumn<>("Date");
        date.setMinWidth(150);
        date.setStyle("-fx-alignment: CENTER");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.setItems(getBillPurchase());
        boolean addAll = table.getColumns().addAll(id, Customer, item, price, qty, discount,  date);
    }

    private ObservableList<Bill> getBillPurchase() throws ClassNotFoundException, SQLException {
        List<Bill> itemPurchases = BillController.getAllBills();
        ObservableList<Bill> itemPurchase = FXCollections.observableArrayList();
        for (Bill temp : itemPurchases) {
            itemPurchase.add(temp);
        }
        for (Bill itemPurchase1 : itemPurchase) {
            itemPurchase1.setCustomer(CustomerController.getCustomerName(itemPurchase1.getCustomer()));
            itemPurchase1.setItem(ItemController.getItemName(itemPurchase1.getItem()));
        }
        return itemPurchase;
    }
}
