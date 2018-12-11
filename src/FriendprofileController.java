import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.embed.swing.SwingFXUtils;

import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class FriendprofileController {

    @FXML
    private Label firstname_label;

    @FXML
    private Label lastname_label;

    @FXML
    private Label age_label;

    @FXML
    private Label email_label;

    @FXML
    private Label status_label;

    @FXML
    private TextArea post_textarea;

    @FXML
    private Button close_button;

    private void showInfo(){
        //.......
    }




    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }



}
