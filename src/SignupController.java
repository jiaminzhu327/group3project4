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
    private String userName;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    private Boolean checkPass;
    private static SignupDAOImpl sign;

    public SignupController()
    {
        String userName;
        String firstName;
        String lastName;
        int age;
        String email;
        String password;
        Boolean checkPass;
    }

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
        //Create New User
        userName = sign_up_username.getText();
        //Check whether the Username is already used or not
        int check = LoginController.CheckUser(userName);
        if(check != 0){
            System.out.println("This Username exists.");
            return;
        }
        //Register personal info
        firstName = sign_up_firstName.getText();
        lastName =sign_up_lastName.getText();
        age = Integer.parseInt(sign_up_age.getText());
        email = sign_up_email.getText();
        //Register authorization info
        password = sign_up_password.getText();
        checkPass = matchPasswords();
        sign=new SignupDAOImpl();
        sign.addUsersInfo(this);
        if(checkPass)
        {
            Parent signUpPageParent = FXMLLoader.load(getClass().getResource("login_page.fxml"));
            Scene signUpPageScene = new Scene(signUpPageParent);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            //Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            //stage.setX((screenBounds.getWidth() - 720) / 2);
            //stage.setY((screenBounds.getHeight() - 720) / 2);
            stage.setResizable(false);
            stage.setScene(signUpPageScene);
            stage.show();
        }

    }
    public String getUserName()
    {
        return userName;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public int getAge()
    {
        return age;
    }
    public String getEmail()
    {
        return email;
    }
    public String getPassword()
    {
        return password;
    }
    public boolean getCheckPass()
    {
        return checkPass;
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