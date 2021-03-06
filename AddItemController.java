/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojecthardwarestore;

import finalprojecthardwarestore.controller.CustomerController;
import finalprojecthardwarestore.controller.ItemController;
import finalprojecthardwarestore.model.Customer;
import finalprojecthardwarestore.model.Item;
import java.net.URL;
import java.sql.SQLException;
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
public class AddItemController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField price;
    @FXML
    private TextField name;
    @FXML
    private TextField qty;
    @FXML
    private TableView<Item> table;
    @FXML
    private Button additem;
    @FXML
    private Button deleteitem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadTable();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CustomerDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    @FXML
    private void tableOnMoseClicked(MouseEvent event) {
        Item selectedItem = table.getSelectionModel().getSelectedItem();
        id.setText(selectedItem.getId());
    }

    @FXML
    private void additemOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        Item item = new Item(
                id.getText(),
                name.getText(),
                Double.parseDouble(price.getText()),
                Integer.parseInt(qty.getText())
        );
        boolean isAdded = ItemController.insertItem(item);
        if (isAdded) {
            loadTable();
            System.out.println("Added");
        } else {
            System.out.println("Not Added");
        }
    }

    @FXML
    private void deleteitemOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        boolean isDeleted = CustomerController.deleteCustomer(id.getText());
        if (isDeleted) {
            System.out.println("Deleted");
            loadTable();
        } else {
            System.out.println("Not Deleted");
        }
    }
    
    private void loadTable() throws ClassNotFoundException, SQLException {
        table.setStyle("-fx-alignment: CENTER");
        int size = table.getItems().size();
        table.getItems().remove(0, size);
        int size1 = table.getColumns().size();
        table.getColumns().remove(0, size1);
        table.setStyle("-fx-cell-size: 25px");
        TableColumn<Item, String> id = new TableColumn<>("Id");
        id.setMinWidth(150);
        id.setStyle("-fx-alignment: CENTER");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Item, String> name = new TableColumn<>("Name");
        name.setMinWidth(150);
        name.setStyle("-fx-alignment: CENTER");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, Double> price = new TableColumn<>("Price");
        price.setMinWidth(150);
        price.setStyle("-fx-alignment: CENTER");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Item, Integer> qty = new TableColumn<>("QTY");
        qty.setMinWidth(150);
        qty.setStyle("-fx-alignment: CENTER");
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        table.setItems(getItem());
        boolean addAll = table.getColumns().addAll(id, name, price, qty);
    }

    private ObservableList<Item> getItem() throws ClassNotFoundException, SQLException {
        List<Item> items = ItemController.getAllItems();
        ObservableList<Item> item = FXCollections.observableArrayList();
        for (Item temp : items) {
            item.add(temp);
        }
        return item;
    }
    
    
}
