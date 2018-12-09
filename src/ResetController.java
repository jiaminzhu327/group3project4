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
    private Label notFound_label;

    @FXML
    private Label successed_label;

    @FXML
    private void updatePassword(){
        //connect to DB
        //check username, if exists then update the password.
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //Change information into yours
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            System.out.println("Successfully connected");
            int check = LoginController.CheckUser(userName.getText());
            if(check != 1){
                System.out.println("Please input correct username.");
                notFound_label.setVisible(true);
                return;
            }
            //Set and use SQL
            String sql = "Update Authorization set Password = ? where UserID = (Select UserID from User where Username = ?);";
            PreparedStatement st = myConn.prepareStatement(sql);
            st.setString(1, newPassword.getText());
            st.setString(2, userName.getText());
            st.executeUpdate();
            System.out.println("Successfully Updated");
            successed_label.setVisible(true);

        }catch(Exception e){
            System.out.println("Connection failed");
            System.out.println(e);
        }
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
}
