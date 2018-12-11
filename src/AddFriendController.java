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
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Change information into yours
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            System.out.println("Successfully connected");
            int check = LoginController.CheckUser(userName.getText());
            if(check == 1) {
                String sql = "Select UserID from User where Username = ?;";
                PreparedStatement st = myConn.prepareStatement(sql);
                st.setString(1, userName.getText());
                ResultSet rs = st.executeQuery();
                rs.next();
                int friendID = rs.getInt("UserID");
                int checkFriend = CheckFriend(LoginController.currentUserID, friendID);
                found_label.setVisible(true);
                if(checkFriend == 0) {
                    sql = "Insert into Friends (UserID, FriendID) values (?, ?);";
                    st = myConn.prepareStatement(sql);
                    st.setInt(1, LoginController.currentUserID);
                    st.setInt(2, friendID);
                    st.executeUpdate();
                    System.out.println("Successfully added.");
                    found_label.setVisible(true);
                }else{
                    System.out.println("This user is already your friend.");
                    return;
                }
            }else {
                notFound_label.setVisible(true);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private int CheckFriend(int uID, int fID){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Change information into yours
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            System.out.println("Successfully connected");
            String sql = "Select * from Friends where UserID = ? and FriendID = ?;";
            PreparedStatement st = myConn.prepareStatement(sql);
            st.setInt(1, uID);
            st.setInt(2, fID);
            ResultSet rs = st.executeQuery();
            if(rs.first()){
                return 1;
            }else{
                return 0;
            }
        }catch(Exception e){
            System.out.println(e);
            return -1;
        }
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
}