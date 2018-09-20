package edu.as.sys.model;

//import com.jfinal.plugin.activerecord.Model;
//import edu.cn.nlsde.tmfst.config.Config;

import edu.as.sys.config.DBUitil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2016/5/6.
 */
public class Comment {

//    private static Comment dao = new Comment();

    public String comment_id;
    @NotNull
    public String comment_content;
    @Past
    public Date comment_time;
    public Date comment_crawler_time;
    public String product_id;

    public List<String> getPhotoList() {
        Connection con = null;
        PreparedStatement pstmt = null;
        List<String> result = new ArrayList<>();
        try {
            con = DBUitil.openConnection();
            String sql = "select photo.photo_url from photo inner join Comment on photo.comment_id=Comment.comment_id where " +
                    "Comment.product_id=? limit 100";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, product_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("photo_url"));
            }
        } catch (SQLException ex) {

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

    public static List<Comment> get_comment(String product_id) {
        List<Comment> results = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBUitil.openConnection();
            String sql = "select * from Comment where product_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, product_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comment oneItem = new Comment();
                oneItem.comment_id = rs.getString("comment_id");
                oneItem.comment_content = rs.getString("comment_content");
                oneItem.comment_time = rs.getTimestamp("comment_time");
                oneItem.product_id = rs.getString("product_id");
                results.add(oneItem);
            }
        } catch (SQLException ex) {

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

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public Date getComment_time() {
        return comment_time;
    }

    public void setComment_time(Date comment_time) {
        this.comment_time = comment_time;
    }

    public Date getComment_crawler_time() {
        return comment_crawler_time;
    }

    public void setComment_crawler_time(Date comment_crawler_time) {
        this.comment_crawler_time = comment_crawler_time;
    }

    public String getProduct_id() {
        return product_id;
    }
//    public Comment(){
//        Config.start();
//    }


//    private void Model2Data(){
////        this.update_unique_key();
//        this.set("comment_id", this.comment_id);
//        this.set("comment_time", this.comment_time);
//        this.set("comment_content", this.comment_content);
//        this.set("product_id", this.product_id);
//    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void save_model() throws SQLException {
//        this.Model2Data();
//        if(dao.findById(this.comment_id)==null) {
//            this.save();
//        }
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "insert IGNORE into Comment(comment_id,comment_content,comment_time,product_id,hash) values(?,?,?,?,?)";
            con = DBUitil.openConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, this.comment_id);
            pstmt.setString(2, this.comment_content);
            pstmt.setTimestamp(3, new java.sql.Timestamp(this.comment_time.getTime()));
            pstmt.setString(4, this.product_id);
            pstmt.setString(5, Photo.Calculate_identity(this.comment_content));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
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
}
