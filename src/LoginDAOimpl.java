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


public class LoginDAOimpl  {

    public void checkUsers(String userID)throws SQLException
    {
        boolean login_error = false;
        //System.out.println(login_error);
        //Class.forName("com.mysql.jdbc.Driver");
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
        java.sql.Statement myStmt = myConn.createStatement();
        System.out.println("Successfully connected");
        //userName = login_username.getText();
        //password = login_password.getText();
        String sql = "Select * from Authorization where UserID = (Select UserID from User where Username = ?);";
        PreparedStatement st = myConn.prepareStatement(sql);
        //st.setString(1, userName);
        ResultSet rs = st.executeQuery();
        rs.next();
        if(rs.getString("Password").equals(123)){
            System.out.println("Successfully Logged in");
            System.out.println("Current User:" );
            int currentUserID = rs.getInt("UserID");
            String userName = "gasaf";
            String currentUserName = userName;
            //loadUserDashBoard();
        }else{
            System.out.println("Password is not correct");
            //login_error.setVisible(true);
        }
    }

}