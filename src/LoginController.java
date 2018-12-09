
import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginController {
    ProfileController profileControllerInstance = new ProfileController();
    static String userName = "";
    static int currentUserID;
    String password = "";
    
    @FXML
    private TextField login_username;
    
    @FXML
    private PasswordField login_password;
    
    @FXML
    private Button login_button;
    
    @FXML
    private Button sign_up_button;

    @FXML
    private Label login_error;
    
    @FXML
    private void loadSignUpPage(ActionEvent event) throws IOException{
        Parent signUpPageParent = FXMLLoader.load(getClass().getResource("sign_up_page.fxml"));
        Scene signUpPageScene = new Scene(signUpPageParent);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - 1000) / 2);
        stage.setY((screenBounds.getHeight() - 1000) / 2);

        stage.setResizable(false);
        stage.setScene(signUpPageScene);
        stage.show();
    }
    
    @FXML
    private void checkCredentials(ActionEvent event) throws IOException{
        //System.out.println("Login!");
        // TODO Check password, call encryption method here
        // If password correct log in
        try {
            System.out.println(login_error);
            Class.forName("com.mysql.jdbc.Driver");
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            java.sql.Statement myStmt = myConn.createStatement();
            System.out.println("Successfully connected");
            userName = login_username.getText();
            password = login_password.getText();
            int check = CheckUser(userName);
            if(check == 1){
                String sql = "Select * from Authorization where UserID = (Select UserID from User where Username = ?);";
                PreparedStatement st = myConn.prepareStatement(sql);
                st.setString(1, userName);
                ResultSet rs = st.executeQuery();
                rs.next();
                if(rs.getString("Password").equals(password)){
                    System.out.println("Successfully Logged in");
                    System.out.println("Current User:" + userName);
                    currentUserID = rs.getInt("UserID");
                    //String currentUserName = userName;
                    loadUserDashBoard(event);
                }else{
                    System.out.println("Password is not correct");
                    login_error.setVisible(true);
                }
            }else if(check == 0){
                System.out.println("The Username does not exist");
                currentUserID = 0;
                userName = "";
                password = "";
                login_error.setVisible(true);
            }
        }catch(Exception e){
                System.out.println("Connection Failed Check Credential.");
                System.out.println(e);
        }
    }

    private void loadUserDashBoard(ActionEvent event) throws IOException{
        Parent signUpPageParent = FXMLLoader.load(getClass().getResource("profile_page.fxml"));
        Scene signUpPageScene = new Scene(signUpPageParent);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

//        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
////        stage.setX((screenBounds.getWidth() - 1920) / 2);
////        stage.setY((screenBounds.getHeight() - 1080) / 2);

        stage.setResizable(false);
        stage.setScene(signUpPageScene);
        stage.show();
        profileControllerInstance.showInfo(userName);

    }

    private String getUsername(){
        return login_username.getText();
    }

    private String getPassword(){
        return login_password.getText();
    }

    public static int CheckUser(String name){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");

            //Check whether the username exists
            String sql = "Select * from User where Username = ?;";
            PreparedStatement st = myConn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if(rs.first()){
                return 1;    // If the user exist, return 1
            }else {
                return 0;   // If the user does not exist, return 0
            }
        }catch(Exception e){
            System.out.println("Connection failed CheckUser");
            System.out.println(e);
            return -1;
        }
    }
}
