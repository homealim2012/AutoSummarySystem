package edu.as.sys.model;

import edu.as.sys.config.DBUitil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Created by dell on 2017/2/19.
 */
public class Photo {
    public String photo_url;
    public String comment_id;

    public void save_model() {
        String sql = "insert IGNORE into photo(photo_url, comment_id, hash) values(?,?,?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBUitil.openConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, this.photo_url);
            pstmt.setString(2, this.comment_id);
            String hash = Calculate_identity(this.photo_url);
            pstmt.setString(3, hash);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    }

    public static String Calculate_identity(String itemString) {
        String result = null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            //result = bytesToHex(md.digest(itemString.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
