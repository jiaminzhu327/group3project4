import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PostsDAOimpl {

    private StringProperty UserName;
    private StringProperty PostText;
    private StringProperty PostTime;

    public PostsDAOimpl() {

        this.UserName = new SimpleStringProperty();
        this.PostText = new SimpleStringProperty();
        this.PostTime = new SimpleStringProperty();
    }


    // this is UserName
    public String getUserName() {
        return UserName.get();
    }
    // set the UserName
    public void setUserName(String newUserName) {
        UserName.set(newUserName);
    }
    // UserName column
    public StringProperty UserNameProperty() {
        return UserName;
    }


    // this is PostText
    public String getPostText() {
        return PostText.get();
    }
    // set
    public void setPostText(String newPostText) {
        PostText.set(newPostText);
    }
    // column
    public StringProperty PostTextProperty() {
        return PostText;
    }

    /////////// This is PostTime
    // this is PostText
    public String getPostTime() {
        return PostTime.get();
    }
    // set the UserName
    public void setPostTime(String newPostTime) {
        PostTime.set(newPostTime);
    }
    // UserName column
    public StringProperty PostTimeProperty() {
        return PostTime;
    }




}