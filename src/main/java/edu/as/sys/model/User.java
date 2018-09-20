package edu.as.sys.model;

import edu.as.sys.config.DBUitil;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dell on 2016/5/5.
 */
public class User implements Serializable {

    //    private final static User dao = new User();
//
    public int user_id;
    public String user_name;
    public String user_password;

    public static User get_user_by_name_password(String name, String password) throws SQLException {
        User results = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBUitil.openConnection();
            String sql = "select * from User where user_name = ? and user_password = ? limit 1";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                results = new User();
                results.user_id = rs.getInt("user_id");
                results.user_name = rs.getString("user_name");
                results.user_password = rs.getString("user_password");
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public boolean verify() {
        boolean result = false;
        try {
            if (get_user_by_name_password(this.user_name, this.user_password) != null) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean save_model() {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            String sql = "insert IGNORE into User(user_name,user_password) values(?,?)";
            con = DBUitil.openConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, this.user_name);
            pstmt.setString(2, this.user_password);
            int count = pstmt.executeUpdate();
            if (count == 1) {
                result = true;
            }
        } catch (Exception ex) {
            result = false;
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

//    public String user_city;
//    public String user_province;
//    public String user_location;
//    public String user_description;
//    public String user_image_url;
//    public int user_gender;
//    public int user_friends_count;
//    public int user_followers_count;
//    public Date user_creation_time;
//    public int user_verified;
//    public int user_statuses_count;
//    public String user_image_position;
//
//    public User(){
//        Config.start();
//    }
//
//    private void Model2Data(){
//        this.set("user_id",    this.user_id);
//        this.set("user_name",    this.user_name);
//        this.set("user_city",    this.user_city);
//        this.set("user_province",    this.user_province);
//        this.set("user_location",    this.user_location);
//        this.set("user_description",    this.user_description);
//        this.set("user_image_url",    this.user_image_url);
//        this.set("user_gender",    this.user_gender);
//        this.set("user_friends_count",    this.user_friends_count);
//        this.set("user_followers_count",    this.user_followers_count);
//        this.set("user_creation_time",    this.user_creation_time);
//        this.set("user_verified",    this.user_verified);
//        this.set("user_statuses_count",    this.user_statuses_count);
//        this.set("user_image_position",    this.user_image_position);
//    }
//
//    public void save_model(){
//        this.Model2Data();
//        this.save();
//    }
//
//    public static boolean exist(long user_id){
//        return dao.findById(user_id)!=null;
//    }
}
