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


public class AddFriendController {

    @FXML
    private TextField userName;

    @FXML
    private Button addfriend_button;

    @FXML
    private Button close_button;

    @FXML
    private Label notFound_label;

    @FXML
    private Label found_label;

    @FXML
    private void addFriend(){
        //connect to DB
        //if username exists,found_label.setVisible(true) and add to friend list(could be hard), if not set notFound_label.setVisible(true);
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
}
