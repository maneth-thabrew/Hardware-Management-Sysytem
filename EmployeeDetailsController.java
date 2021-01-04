/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojecthardwarestore;

import finalprojecthardwarestore.controller.CustomerController;
import finalprojecthardwarestore.controller.EmployeeController;
import finalprojecthardwarestore.model.Customer;
import finalprojecthardwarestore.model.Employee;
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
public class EmployeeDetailsController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField tel;
    @FXML
    private TextField address;
    @FXML
    private TextField name;
    @FXML
    private TextField status;
    @FXML
    private TableView<Employee> table;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private TextField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            loadTable();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeeDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

//    @FXML
//    private void tableOnMouseClicked(MouseEvent event) {
//    }

   @FXML
    private void addOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        Employee employee = new Employee(
                id.getText(),
                name.getText(),
                status.getText(),
                Integer.valueOf(tel.getText()),
                address.getText(),
                password.getText()
        );
        boolean isAdded = EmployeeController.insertEmployee(employee);
        if (isAdded) {
            loadTable();
            System.out.println("Added");
        } else {
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
        TableColumn<Employee, String> id = new TableColumn<>("Id");
        id.setMinWidth(150);
        id.setStyle("-fx-alignment: CENTER");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Employee, String> name = new TableColumn<>("Name");
        name.setMinWidth(150);
        name.setStyle("-fx-alignment: CENTER");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Employee, String> status = new TableColumn<>("Status");
        status.setMinWidth(150);
        status.setStyle("-fx-alignment: CENTER");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<Employee, String> tel = new TableColumn<>("Telephone");
        tel.setMinWidth(150);
        tel.setStyle("-fx-alignment: CENTER");
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        TableColumn<Employee, String> address = new TableColumn<>("Address");
        address.setMinWidth(150);
        address.setStyle("-fx-alignment: CENTER");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Employee, String> password = new TableColumn<>("Password");
        password.setMinWidth(150);
        password.setStyle("-fx-alignment: CENTER");
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        table.setItems(getEmployee());
        boolean addAll = table.getColumns().addAll(id, name,status, tel, address, password);
    }

    private ObservableList<Employee> getEmployee() throws ClassNotFoundException, SQLException {
        List<Employee> employees = EmployeeController.getAllEmployee();
        ObservableList<Employee> employee = FXCollections.observableArrayList();
        for (Employee temp : employees) {
            employee.add(temp);
        }
        return employee;
    }


    @FXML
    private void deleteOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        boolean isDeleted = EmployeeController.deleteEmployee(id.getText());
        if (isDeleted) {
            System.out.println("Deleted");
            loadTable();
        } else {
            System.out.println("Not Deleted");
        }
    }
    
     @FXML
    private void tableOnMouseClicked(MouseEvent event) {
        Employee employee = (Employee) table.getSelectionModel().getSelectedItem();
        id.setText(employee.getId());
        name.setText(employee.getName());
        status.setText(employee.getStatus());
        tel.setText(String.valueOf(employee.getTel()));
        address.setText(employee.getAddress());
        password.setText(employee.getPassword());
    }

//    private ObservableList<Employee> getEmployee() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    

}
