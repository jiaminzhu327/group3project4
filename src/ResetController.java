import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Random;


public class ResetController {

    @FXML
    private TextField userName;

    @FXML
    private TextField newPassword;

    @FXML
    private Button resetpwd_button;

    @FXML
    private Button close_button;

    @FXML
    private void updatePassword(){

        //connect to DB
        //check username, if exists then update the password.
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
}
