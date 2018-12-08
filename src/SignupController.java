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

public class SignupController {
    
    @FXML
    private TextField sign_up_firstName;
    
    @FXML
    private TextField sign_up_lastName;
    
    @FXML
    private TextField sign_up_age;
    
    @FXML
    private TextField sign_up_email;
    
    @FXML
    private TextField sign_up_username;
    
    @FXML
    private PasswordField sign_up_password;
    
    @FXML
    private PasswordField sign_up_confirmPassword;
    
    @FXML
    private Button sign_up_registerButton;
    
    @FXML
    private Button sign_up_cancelButton;

    @FXML
    private Label username_avail;

    @FXML
    private Label passwordMatches;

    @FXML
    private Label passwordDoesntMatch;

    
    @FXML
    private void registerUser (ActionEvent event) throws Exception{
        //TODO Using DAO objects pass data to DB
        //TODO Let user know if account creation was successful

        try{
            Class.forName("com.mysql.jdbc.Driver");
            //Change information into yours
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            System.out.println("Successfully connected");

            //Create New User
            String userName = sign_up_username.getText();
            Date sqlNow = new Date(System.currentTimeMillis());
            Random rnd = new Random();

            //Check whether the Username is already used or not
            int check = LoginController.CheckUser(userName);
            if(check != 0){
                System.out.println("This Username is already used.");
                return;
            }

            //Set random number as UserID
            int uID = rnd.nextInt();
            if(uID < 0){
                uID = Math.abs(uID);
            }
            String sql = "Insert into User (UserID, Username, Date_Created) values (?, ?, ?);";
            PreparedStatement st = myConn.prepareStatement(sql);
            st.setInt(1, uID);
            st.setString(2, userName);
            st.setDate(3, sqlNow);
            st.executeUpdate();

            //Register personal info
            String firstName = sign_up_firstName.getText();
            String lastName =sign_up_lastName.getText();
            int age = Integer.parseInt(sign_up_age.getText());
            String email = sign_up_email.getText();
            sql = "Insert into UserInfo (UserID, FirstName, LastName, Age, Email) values (?, ?, ?, ?, ?);";
            st = myConn.prepareStatement(sql);
            st.setInt(1, uID);
            st.setString(2, firstName);
            st.setString(3, lastName);
            st.setInt(4, age);
            st.setString(5, email);
            st.executeUpdate();

            //Register authorization info
            String password = sign_up_password.getText();
            Boolean checkPass = matchPasswords();
            if(checkPass) {
                sql = "Insert into Authorization (UserID, Password, Date_Created) values (?, ?, ?);";
                st = myConn.prepareStatement(sql);
                st.setInt(1, uID);
                st.setString(2, password);
                st.setDate(3, sqlNow);
                st.executeUpdate();
                System.out.println("Successfully Registered");
                //Returns back to login after account creation
                Parent signUpPageParent = FXMLLoader.load(getClass().getResource("login_page.fxml"));
                Scene signUpPageScene = new Scene(signUpPageParent);
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                //Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                //stage.setX((screenBounds.getWidth() - 720) / 2);
                //stage.setY((screenBounds.getHeight() - 720) / 2);
                stage.setResizable(false);
                stage.setScene(signUpPageScene);
                stage.show();
            }
        }catch(Exception e){
            System.out.println("Connection failed");
            System.out.println(e);
        }

    }
    
    @FXML
    private void cancelRegistration (ActionEvent event) throws Exception{

        //TODO Prompt user if they are sure they want to cancel registration

        System.out.println("Cancel button pressed");
        Parent signUpPageParent = FXMLLoader.load(getClass().getResource("login_page.fxml"));
        Scene signUpPageScene = new Scene(signUpPageParent);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - 720) / 2);
        stage.setY((screenBounds.getHeight() - 720) / 2);

        stage.setResizable(false);
        stage.setScene(signUpPageScene);
        stage.show();
    }

    @FXML
    private boolean matchPasswords(){
        if(sign_up_password.getText().equals("") || sign_up_confirmPassword.getText().equals("")){
            passwordMatches.setVisible(false);
            passwordDoesntMatch.setVisible(false);
            sign_up_password.setStyle("-fx-border-color: #DCDCDC;");
            sign_up_confirmPassword.setStyle("-fx-border-color: #DCDCDC;");
            return false;
        }else if(sign_up_password.getText().equals(sign_up_confirmPassword.getText())) {
            passwordDoesntMatch.setVisible(false);
            passwordMatches.setVisible(true);
            sign_up_password.setStyle("-fx-border-color: #00FF3C;");
            sign_up_confirmPassword.setStyle("-fx-border-color: #00FF3C;");
            return true;
        }else{
            passwordMatches.setVisible(false);
            passwordDoesntMatch.setVisible(true);
            sign_up_password.setStyle("-fx-border-color: red;");
            sign_up_confirmPassword.setStyle("-fx-border-color: red;");
            return false;
        }
    }

    @FXML
    private void checkUsername(){
        // TODO Check DB if username already exits if so warn user.
//        if(!sign_up_username.getText().equals("username")){ // Username is taken
//            sign_up_username.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");
//            //username_avail.setVisible(true);
//
//        }else if(sign_up_username.getText().equals("")){
//            sign_up_username.setStyle("-fx-border-color: #DCDCDC; -fx-text-inner-color: black;");
//            //username_avail.setVisible(false);
//        }else{
//            sign_up_username.setStyle("-fx-border-color: #00FF3C; -fx-text-inner-color: #00FF3C;");
//           // username_avail.setVisible(false);
//        }
    }

}
