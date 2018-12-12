import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class AddFriendController implements Initializable {

    @FXML
    private TextField userName;

    @FXML
    private Button close_button;

    @FXML
    private Label notFound_label;

    @FXML
    private Label found_label;

    public static User foundUser = null;

    private ProfileController controller;
    public void setProfileController(ProfileController controller) {
        this.controller = controller;
    }

    @FXML
    private void addFriend() {
        notFound_label.setVisible(false);
        found_label.setVisible(false);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM UserInfo WHERE UserID = (Select UserID from User where UserName = ?)");
            ps.setString(1,userName.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                foundUser = new User(rs.getString("FirstName"), rs.getString("LastName"), rs.getInt("Age"));
                foundUser.setId(rs.getInt("UserID"));
                foundUser.setEmail(rs.getString("Email"));
            }
            found_label.setVisible(true);

        } catch (SQLException | ClassNotFoundException e) {
            found_label.setVisible(false);
            notFound_label.setVisible(true);
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        if(found_label.isVisible()) {
            controller.AddUserToFriendList(foundUser);
        }
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
