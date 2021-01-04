/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojecthardwarestore;

import finalprojecthardwarestore.controller.EmployeeController;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author DARK DEMON
 */
public class FXMLDocumentController implements Initializable {

//    String user = "maneth";
//    String pass = "2001";

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;


    @FXML
    void handleButtonAction(ActionEvent event) throws ClassNotFoundException, SQLException {

        String name = username.getText();
        String pass = password.getText();
        if (name.isEmpty() || pass.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter required fields");
            alert.showAndWait();
        } else {
            if (EmployeeController.checkLoginData(name, pass)) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuPage.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (Exception e) {
                    System.out.println("Cant load new window");
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The details you entered is incorrect");
                alert.showAndWait();
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
