/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojecthardwarestore;

import finalprojecthardwarestore.controller.SupplierController;
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

/**
 * FXML Controller class
 *
 * @author DARK DEMON
 */
public class SupplierRecordsController implements Initializable {

    @FXML
    private TableView<Supplier> table;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField tel;
    @FXML
    private TextField address;
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
            Logger.getLogger(SupplierRecordsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void addOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        Supplier supplier = new Supplier(
                id.getText(),
                name.getText(),
                Integer.valueOf(tel.getText()),
                address.getText()
        );
        boolean isAdded = SupplierController.insertSupplier(supplier);
        if(isAdded){
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
        TableColumn<Supplier, String> id = new TableColumn<>("Id");
        id.setMinWidth(150);
        id.setStyle("-fx-alignment: CENTER");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Supplier, String> name = new TableColumn<>("Name");
        name.setMinWidth(150);
        name.setStyle("-fx-alignment: CENTER");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Supplier, String> tel = new TableColumn<>("Telephone");
        tel.setMinWidth(150);
        tel.setStyle("-fx-alignment: CENTER");
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        TableColumn<Supplier, String> address = new TableColumn<>("Address");
        address.setMinWidth(150);
        address.setStyle("-fx-alignment: CENTER");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        table.setItems(getSuppliers());
        boolean addAll = table.getColumns().addAll(id, name, tel, address);
    }
    
    private ObservableList<Supplier> getSuppliers() throws ClassNotFoundException, SQLException {
        List<Supplier> suppliers = SupplierController.getAllSupplier();
        ObservableList<Supplier> supplier = FXCollections.observableArrayList();
        for (Supplier temp : suppliers) {
            supplier.add(temp);
        }
        return supplier;
    }

    @FXML
    private void deleteOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        boolean isDeleted = SupplierController.deleteSupplier(id.getText());
        if(isDeleted){
            System.out.println("Deleted");
            loadTable();
        }else{
            System.out.println("Not Deleted");
        }
    }

    @FXML
    private void tableOnMouseClicked(MouseEvent event) {
        Supplier supplier = table.getSelectionModel().getSelectedItem();
        id.setText(supplier.getId());
        name.setText(supplier.getName());
        tel.setText(String.valueOf(supplier.getTel()));
        address.setText(supplier.getAddress());
    }
    
//    private void ShandleClose(ActionEvent event){
//        System.exit(0);
//    }
}


