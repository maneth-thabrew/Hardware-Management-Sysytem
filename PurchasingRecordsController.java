/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojecthardwarestore;

import finalprojecthardwarestore.controller.ItemController;
import finalprojecthardwarestore.controller.ItemPurchaseController;
import finalprojecthardwarestore.controller.SupplierController;
import finalprojecthardwarestore.model.Item;
import finalprojecthardwarestore.model.ItemPurchase;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DARK DEMON
 */
public class PurchasingRecordsController implements Initializable {

    @FXML
    private TextField pId;
    @FXML
    private TextField price;
    @FXML
    private TableView<ItemPurchase> table;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button additem;
    @FXML
    private TextField qty;
    @FXML
    private TextField discount;
    @FXML
    private TextField total;
    @FXML
    private ComboBox<String> sId;
    @FXML
    private ComboBox<String> iId;
    @FXML
    private TextField date;

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
        ItemPurchase selectedItem = table.getSelectionModel().getSelectedItem();
        pId.setText(selectedItem.getId());
    }

    @FXML
    private void addOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        ItemPurchase itemPurchase = new ItemPurchase(
                pId.getText(),
                sId.getValue().substring(0, 2),
                iId.getValue().substring(0, 2),
                Double.parseDouble(price.getText()),
                Integer.parseInt(qty.getText()),
                Double.parseDouble(price.getText()),
                Double.parseDouble(price.getText()),
                date.getText()
        );
        boolean isInserted = ItemPurchaseController.insertItemPurchase(itemPurchase);
        if (isInserted) {
            System.out.println("Added");
            loadTable();
        } else {
            System.out.println("Not Added");
        }
    }

    @FXML
    private void deleteOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        boolean isDeleted = ItemPurchaseController.deleteItemPurchase(pId.getText());
        if (isDeleted) {
            System.out.println("Deleted");
            loadTable();
        } else {
            System.out.println("Not Deleted");
        }
    }
    

    

    private void loadId() throws ClassNotFoundException, SQLException {
        List<String> allItemId = ItemController.getAllItemId();
        for (String string : allItemId) {
            string += " - " + ItemController.getItemName(string);
            iId.getItems().add(string);
        }
        List<String> allSupplierId = SupplierController.getAllSupplierId();
        for (String string : allSupplierId) {
            string += " - " + SupplierController.getSupplierName(string);
            sId.getItems().add(string);
        }
    }

    private void loadTable() throws ClassNotFoundException, SQLException {
        table.setStyle("-fx-alignment: CENTER");
        int size = table.getItems().size();
        table.getItems().remove(0, size);
        int size1 = table.getColumns().size();
        table.getColumns().remove(0, size1);
        table.setStyle("-fx-cell-size: 25px");

        TableColumn<ItemPurchase, String> id = new TableColumn<>("Id");
        id.setMinWidth(150);
        id.setStyle("-fx-alignment: CENTER");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ItemPurchase, String> supplier = new TableColumn<>("Supplier");
        supplier.setMinWidth(150);
        supplier.setStyle("-fx-alignment: CENTER");
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<ItemPurchase, String> item = new TableColumn<>("Item");
        item.setMinWidth(150);
        item.setStyle("-fx-alignment: CENTER");
        item.setCellValueFactory(new PropertyValueFactory<>("item"));

        TableColumn<ItemPurchase, Integer> qty = new TableColumn<>("Qty");
        qty.setMinWidth(150);
        qty.setStyle("-fx-alignment: CENTER");
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<ItemPurchase, Double> price = new TableColumn<>("Price");
        price.setMinWidth(150);
        price.setStyle("-fx-alignment: CENTER");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<ItemPurchase, Double> discount = new TableColumn<>("Discount");
        discount.setMinWidth(150);
        discount.setStyle("-fx-alignment: CENTER");
        discount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        TableColumn<ItemPurchase, Double> total = new TableColumn<>("Total");
        total.setMinWidth(150);
        total.setStyle("-fx-alignment: CENTER");
        total.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<ItemPurchase, String> date = new TableColumn<>("Date");
        date.setMinWidth(150);
        date.setStyle("-fx-alignment: CENTER");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.setItems(getItemPurchase());
        boolean addAll = table.getColumns().addAll(id, supplier, item, price, qty, discount, total, date);
    }

    private ObservableList<ItemPurchase> getItemPurchase() throws ClassNotFoundException, SQLException {
        List<ItemPurchase> itemPurchases = ItemPurchaseController.getAllItemPurchases();
        ObservableList<ItemPurchase> itemPurchase = FXCollections.observableArrayList();
        for (ItemPurchase temp : itemPurchases) {
            itemPurchase.add(temp);
        }
        for (ItemPurchase itemPurchase1 : itemPurchase) {
            itemPurchase1.setSupplier(SupplierController.getSupplierName(itemPurchase1.getSupplier()));
            itemPurchase1.setItem(ItemController.getItemName(itemPurchase1.getItem()));
        }
        return itemPurchase;
    }

    @FXML
    private void addItemOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddItem.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
