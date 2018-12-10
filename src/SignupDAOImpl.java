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

public class SignupDAOImpl implements SignupDAO{
    
    public void addUsersInfo(SignupController s)throws SQLException
    {
         try{
            Class.forName("com.mysql.jdbc.Driver");
            //Change information into yours
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            System.out.println("Successfully connected");

            //Create New User
            String userName = s.getUserName();
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
            String firstName = s.getFirstName();
            String lastName =s.getLastName();
            int age =s.getAge();
            String email = s.getEmail();
            sql = "Insert into UserInfo (UserID, FirstName, LastName, Age, Email) values (?, ?, ?, ?, ?);";
            st = myConn.prepareStatement(sql);
            st.setInt(1, uID);
            st.setString(2, firstName);
            st.setString(3, lastName);
            st.setInt(4, age);
            st.setString(5, email);
            st.executeUpdate();

            //Register authorization info
            String password = s.getPassword();
            Boolean checkPass = s.getCheckPass();
            if(checkPass) {
                sql = "Insert into Authorization (UserID, Password, Date_Created) values (?, ?, ?);";
                st = myConn.prepareStatement(sql);
                st.setInt(1, uID);
                st.setString(2, password);
                st.setDate(3, sqlNow);
                st.executeUpdate();
                System.out.println("Successfully Registered");
                //Returns back to login after account creation
                
            }
        }catch(Exception e){
            System.out.println("Connection failed");
            System.out.println(e);
        }
    }
    
}
