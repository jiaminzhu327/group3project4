
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MTR
 */
public class AddFriendDAOimpl {

    public void addFriend(String UserID)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Change information into yours
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            System.out.println("Successfully connected");
            int check = LoginController.CheckUser(UserID);
            if(check == 1) {
                String sql = "Select UserID from User where Username = ?;";
                PreparedStatement st = myConn.prepareStatement(sql);
                st.setString(1, UserID);
                ResultSet rs = st.executeQuery();
                rs.next();
                int friendID = rs.getInt("UserID");
                int checkFriend = CheckFriend(LoginController.currentUserID, friendID);
                //found_label.setVisible(true);
                if(checkFriend == 0) {
                    sql = "Insert into Friends (UserID, FriendID) values (?, ?);";
                    st = myConn.prepareStatement(sql);
                    st.setInt(1, LoginController.currentUserID);
                    st.setInt(2, friendID);
                    st.executeUpdate();
                    System.out.println("Successfully added.");
                    //found_label.setVisible(true);
                }else{
                    System.out.println("This user is already your friend.");
                    return;
                }
            }else {
                //notFound_label.setVisible(true);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void checkUser(String UserID)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Change information into yours
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/COMP585?autoReconnect=true&useSSL=false", "root", "root");
            System.out.println("Successfully connected");
            String sql = "Select * from Friends where UserID = ? and FriendID = ?;";
            PreparedStatement st = myConn.prepareStatement(sql);
            st.setInt(1, Integer.valueOf(UserID));
            st.setInt(2,Integer.valueOf(UserID));
            ResultSet rs = st.executeQuery();
            if(rs.first()){

            }else{
            }
        }catch(Exception e){
            System.out.println(e);
            //return -1;
        }
    }

    private int CheckFriend(int currentUserID, int friendID) {
        return 0;
    }

}