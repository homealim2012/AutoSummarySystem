package edu.as.sys.model;

import edu.as.sys.config.DBUitil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by dell on 2016/5/6.
 */
public class Product {

    private String product_id;
    private String product_name = "";
    private int product_rate = 0;
    private int platform;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_rate() {
        return product_rate;
    }

    public void setProduct_rate(int product_rate) {
        this.product_rate = product_rate;
    }

    //    private static Product dao = new Product();
//
//    public String Product_id;
//    public String Product_name;
//    public int Product_type;
//    public int product_status;
//
//
//    public Product() {
//        Config.start();
//    }
//
//
//    private void Model2Data() {
////        this.update_unique_key();
//        this.set("Product_id", this.Product_id);
//        this.set("Product_name", this.Product_name);
//        this.set("Product_type", this.Product_type);
//        this.set("product_status", this.product_status);
//    }
//
//
//    public void save_model() {
//        this.Model2Data();
//        if (dao.findById(this.Product_id) == null) {
//            this.save();
//        }
//    }
    public void save_model() {
//        this.Model2Data();
//        if(dao.findById(this.comment_id)==null) {
//            this.save();
//        }
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "insert IGNORE into Product(product_id,product_name,platform_id,product_rate) values(?,?,?,?)";
            con = DBUitil.openConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, this.product_id);
            pstmt.setString(2, this.product_name);
            pstmt.setInt(3, this.platform);
            pstmt.setInt(4, this.product_rate);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
}
