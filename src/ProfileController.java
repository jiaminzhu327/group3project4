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


public class ProfileController {

    @FXML
    private Button udb_logoutButton;

    @FXML
    private Button udb_settingsButton;

    @FXML
    private Label udb_firstNameLabel;

    @FXML
    private Label udb_lastNameLabel;

    @FXML
    private Label udb_AgeLabel;

    @FXML
    private Label udb_EmailLabel;

    @FXML
    private TextArea udb_statusArea;

    @FXML
    private Button udb_editStatusButton;

    @FXML
    private ListView udb_FriendsListView;

    @FXML
    private ListView udb_PostsListView;

    @FXML
    private Button udb_NewPostButton;

    @FXML
    private Button image_button;

    @FXML
    private ImageView profile_image;

    @FXML
    private Label status_label;

    @FXML
    private TextField status_textfield;

    @FXML
    private Button removeFriend_button;

    @FXML
    private Button removePost_button;

    @FXML
    private CheckBox hideAge_button;

    @FXML
    private CheckBox hideStatus_button;

    @FXML
    private CheckBox hidePost_button;

    @FXML
    private CheckBox hideFriend_button;

    @FXML
    private ScrollPane udb_FLScrollPane;


    public String userName="";
    private boolean firstShow=true;

    public void initialize(){

        showInfo(LoginController.userName);
    }


    @FXML
    private void logoutOfApplication(ActionEvent event) throws IOException {
        //System.out.println("You have logged out.");
        LoginController.userName = "";
        LoginController.currentUserID = 0;
        Parent signUpPageParent = FXMLLoader.load(getClass().getResource("login_page.fxml"));
        Scene signUpPageScene = new Scene(signUpPageParent);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

//        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX((screenBounds.getWidth() - 720) / 2);
//        stage.setY((screenBounds.getHeight() - 720) / 2);

        stage.setResizable(false);
        stage.setScene(signUpPageScene);
        stage.show();
    }

    @FXML
    private void openSettings() throws IOException{
        Parent signUpPageParent = FXMLLoader.load(getClass().getResource("settings_page.fxml"));
        Scene signUpPageScene = new Scene(signUpPageParent);
        Stage stage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - 600) / 2);
        stage.setY((screenBounds.getHeight() - 400) / 2);

        stage.setResizable(false);
        stage.setScene(signUpPageScene);
        stage.show();
    }

    @FXML
    private void openAddFriend() throws IOException{
        Parent signUpPageParent = FXMLLoader.load(getClass().getResource("addfriend_page.fxml"));
        Scene signUpPageScene = new Scene(signUpPageParent);
        Stage stage = new Stage();

        stage.setResizable(false);
        stage.setScene(signUpPageScene);
        stage.show();
    }

    @FXML
    private void editProfileImage() throws IOException{
        Stage stage = null;
        final File[] selectedFile = {null};
//        final Image[] image = {null};

        image_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Choose Image");
                selectedFile[0] = chooser.showOpenDialog(stage);
                System.out.println(selectedFile[0]);
//                image[0] = new Image(selectedFile[0].toString());
//                profile_image.setImage(image[0]);
                try {
                    URL url = selectedFile[0].toURI().toURL();
                    FileInputStream fis = new FileInputStream(selectedFile[0]);
                    /*
                    Connect to DB and post this url into DB.
                     */
                    Class.forName("com.mysql.jdbc.Driver");
                    //Change information into yours
                    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
                    System.out.println("Successfully connected");
                    String sql = "Update UserInfo set Picture = ? where UserID = ?;";
                    PreparedStatement st = myConn.prepareStatement(sql);
                    st.setBinaryStream(1, fis, (int) selectedFile[0].length());
                    st.setInt(2, LoginController.currentUserID);
                    st.executeUpdate();
                    profile_image.setImage(new Image(url.toExternalForm()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch(Exception e){
                    System.out.println(e);
                }

            }
        });

    }

    @FXML
    private void addStatus(){
        udb_editStatusButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                status_label.textProperty().bind(Bindings.format("%s",status_textfield.getText()));
            }
        });
    }

    @FXML
    private void addPost(){
        if(udb_statusArea.getText().length() < 1){
            return;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        udb_PostsListView.getItems().add(0, String.format("%1$-10s:", udb_statusArea.getText() + "\t" + timestamp));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Change information into yours
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            System.out.println("Successfully connected");
            String sql = "Insert into Posts (UserID, Post, Date_Posted) values (?, ?, ?);";
            PreparedStatement st = myConn.prepareStatement(sql);
            st.setInt(1, LoginController.currentUserID);
            st.setString(2, udb_statusArea.getText());
            st.setTimestamp(3, timestamp);
            st.executeUpdate();
        }catch(Exception e){
            System.out.println("Connection Failed");
            System.out.println(e);
        }
    }

    @FXML
    private void removePost(){
        final int selectedIdx = udb_PostsListView.getSelectionModel().getSelectedIndex();
        udb_PostsListView.getItems().remove(selectedIdx);
    }

    @FXML
    private void removeFriend(){
        final int selectedIdx = udb_FriendsListView.getSelectionModel().getSelectedIndex();
        udb_FriendsListView.getItems().remove(selectedIdx);
    }

    public void showInfo(String userName){
        try {
            System.out.println(firstShow);
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
            System.out.println(firstName);
            System.out.println(udb_firstNameLabel);
            udb_firstNameLabel.textProperty().bind(Bindings.format("%s", firstName));

            String lastName = rs.getString("LastName");
            System.out.println(lastName);
            udb_lastNameLabel.textProperty().bind(Bindings.format("%s", lastName));
            String age = rs.getString("Age");
            System.out.println(age);
            udb_AgeLabel.textProperty().bind(Bindings.format("%s", age));

            hideAge_button.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    udb_AgeLabel.setVisible(!newValue);
                }
            });

            hideStatus_button.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    status_label.setVisible(!newValue);
                }
            });

            hidePost_button.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    udb_PostsListView.setVisible(!newValue);
                    //System.out.println("reach here...asdjhaskjdhakjshd");
                }
            });

            hideFriend_button.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    udb_FriendsListView.setVisible(!newValue);
                }
            });




            String email = rs.getString("Email");
            System.out.println(email);
            udb_EmailLabel.textProperty().bind(Bindings.format("%s", email));


            Blob blob = rs.getBlob("Picture");
            byte[] b = blob.getBytes(1, (int)blob.length());
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(b));
            Image imgFX = SwingFXUtils.toFXImage(img, null);
            profile_image.setImage(imgFX);

            if(firstShow==true)
            {
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
                    udb_PostsListView.getItems().add(0, String.format("%1$-10s:", pastPosts.get(i)+" "+postsDate.get(i)));
                    //    udb_PostsListView.getItems().add(0, String.format("%1$-10s:", pastPosts.get(i)));
                }
                firstShow=false;
            }

            hideFriend_button.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    udb_FriendsListView.setVisible(!newValue);
                }
            });

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


}