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
    public static String selectedUserName = "";

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
    private ImageView friend_image;

    @FXML
    private TextArea post_textarea;

    @FXML
    private Button close_button;

    public void initialize(){
        showInfo();
    }
/*
    private void showInfo(){
        //.......
    }

*/


    @FXML
    public void showInfo(){
        try {
            String userName = selectedUserName;
            Class.forName("com.mysql.jdbc.Driver");
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            java.sql.Statement myStmt = myConn.createStatement();
            System.out.println("Successfully connected");

            //Get personal data of input UserName
            String sql = "Select * from UserInfo where UserID = (Select UserID from User where UserName = ?);";
            PreparedStatement st = myConn.prepareStatement(sql);
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            rs.next();
            String firstName = rs.getString("FirstName");
            firstname_label.textProperty().bind(Bindings.format("%s", firstName));

            String lastName = rs.getString("LastName");
            System.out.println(lastName);
            lastname_label.textProperty().bind(Bindings.format("%s", lastName));
            String age = rs.getString("Age");
            System.out.println(age);
            age_label.textProperty().bind(Bindings.format("%s", "Age:" + age));

            if(rs.getString("status")!=null) {
                String status=rs.getString("status");
                System.out.println("current status "+status);
                status_label.textProperty().bind(Bindings.format("%s","Status:" + status));
            }


            String email = rs.getString("Email");
            System.out.println(email);
            email_label.textProperty().bind(Bindings.format("%s", "Email:" + email));


            Blob blob = rs.getBlob("Picture");
            if(blob != null) {
                byte[] b = blob.getBytes(1, (int) blob.length());
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(b));
                Image imgFX = SwingFXUtils.toFXImage(img, null);
                friend_image.setImage(imgFX);
            }
            /*
            //if(firstShow==true)
            //{
                post_textarea.getItems().clear();
                ArrayList<String> pastPosts=new ArrayList<String>();
                ArrayList<Timestamp> postsDate=new ArrayList<Timestamp>();
                sql="Select * from posts where UserID=(Select UserID from User where UserName = ?);";
                st=myConn.prepareStatement(sql);
                st.setString(1,userName);
                ResultSet re=st.executeQuery();
                while(re.next())
                {
                    pastPosts.add(re.getString("Post"));
                    postsDate.add(re.getTimestamp("Date_Posted"));
                    System.out.println(re.getString("Post"));
                    System.out.println(re.getDate("Date_Posted"));
                }
                for(int i=0;i<pastPosts.size();i++)
                {
                    post_textarea.getItems().add(0, String.format("%1$-10s:", pastPosts.get(i)+" "+postsDate.get(i)));
                    //    udb_PostsListView.getItems().add(0, String.format("%1$-10s:", pastPosts.get(i)));
                }
                //firstShow=false;
            //}
            */

            myConn.close();

        }catch(SQLException e){
            System.out.println("Connection Failed");
            System.out.println(e);
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found");
            System.out.println(e);
        }catch(NullPointerException e){
            System.out.println("Null Pointer");
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }
    }


    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }



}
