/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojecthardwarestore;

import finalprojecthardwarestore.controller.CustomerController;
import finalprojecthardwarestore.controller.SupplierController;
import finalprojecthardwarestore.model.Customer;
import finalprojecthardwarestore.model.Supplier;
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
import static sun.net.www.MimeTable.loadTable;

/**
 * FXML Controller class
 *
 * @author DARK DEMON
 */
public class CustomerDetailsController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField tel;
    @FXML
    private TextField address;
    @FXML
    private TextField name;
    @FXML
    private TextField discountrate;
    @FXML
    private TableView<Customer> table;
    @FXML
    private Button add;
    @FXML
    private Button delete;

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
    private void addOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        Customer customer = new Customer(
                id.getText(),
                name.getText(),
                Integer.valueOf(tel.getText()),
                address.getText(),
                discountrate.getText()
        );
        boolean isAdded = CustomerController.insertCustomer(customer);
        if (isAdded){
            loadTable();
            System.out.println("Added");
        }else{
            System.out.println("Not Added");
        }
    }

    private void loadTable() throws ClassNotFoundException, SQLException {
        table.setStyle("-fx-alignment: CENTER");
        int size = table.getItems().size();
        table.getItems().remove(0, size);
        int size1 = table.getColumns().size();
        table.getColumns().remove(0, size1);
        table.setStyle("-fx-cell-size: 25px");
        TableColumn<Customer, String> id = new TableColumn<>("Id");
        id.setMinWidth(150);
        id.setStyle("-fx-alignment: CENTER");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Customer, String> name = new TableColumn<>("Name");
        name.setMinWidth(150);
        name.setStyle("-fx-alignment: CENTER");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Customer, String> tel = new TableColumn<>("Telephone");
        tel.setMinWidth(150);
        tel.setStyle("-fx-alignment: CENTER");
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        TableColumn<Customer, String> address = new TableColumn<>("Address");
        address.setMinWidth(150);
        address.setStyle("-fx-alignment: CENTER");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Customer, String> discountrate = new TableColumn<>("DiscountRate");
        discountrate.setMinWidth(150);
        discountrate.setStyle("-fx-alignment: CENTER");
        discountrate.setCellValueFactory(new PropertyValueFactory<>("discountRate"));
        table.setItems(getCustomer());
        boolean addAll = table.getColumns().addAll(id, name, tel, address, discountrate);
    }

    private ObservableList<Customer> getCustomer() throws ClassNotFoundException, SQLException {
        List<Customer> customers = CustomerController.getAllCustomer();
        ObservableList<Customer> customer = FXCollections.observableArrayList();
        for (Customer temp : customers) {
            customer.add(temp);
        }
        return customer;
    }

    @FXML
    private void deleteOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        boolean isDeleted = CustomerController.deleteCustomer(id.getText());
        if (isDeleted) {
            System.out.println("Deleted");
            loadTable();
        } else {
            System.out.println("Not Deleted");
        }
    }

    @FXML
    private void tableOnMouseClicked(MouseEvent event) {
        Customer customer = (Customer) table.getSelectionModel().getSelectedItem();
        id.setText(customer.getId());
        name.setText(customer.getName());
        tel.setText(String.valueOf(customer.getTel()));
        address.setText(customer.getAddress());
        discountrate.setText(customer.getDiscountRate());
    }

}
