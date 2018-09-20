package edu.as.sys.model;

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
public class DBOperation {

    public static List<Info> getInfoList() {
        Connection con = null;
        PreparedStatement pstmt = null;
        List<Info> infoList = new ArrayList<Info>();
        try {
            con = DBUitil.openConnection();
            String sql = "select id, time_stamp, ori_sentence, jlmlmr, jlmlmr_abs, singlemr, random_choose, filter_sentence, topic_words, search_query, urls from info ";
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Info info = new Info();
                info.id = rs.getInt(1);
                info.time_stamp = rs.getString(2);
                info.ori_sentence = rs.getString(3);
                info.jlmlmr = rs.getString(4);
                info.jlmlmr_abs = rs.getString(5);
                info.singlemr = rs.getString(6);
                info.random_choose = rs.getString(7);
                info.filter_sentence = rs.getString(8);
                info.topic_words = rs.getString(9);
                info.search_query = rs.getString(10);
                info.urls = rs.getString(11);
                infoList.add(info);
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
        return infoList;
    }
    public static Info getInfoByTimeStamp(String time_stamp) {
        Connection con = null;
        PreparedStatement pstmt = null;
        Info info = new Info();
        try {
            con = DBUitil.openConnection();
            String sql = "select id, time_stamp, ori_sentence, jlmlmr, jlmlmr_abs, singlemr, random_choose, filter_sentence, topic_words, search_query, urls from info "
                    + "where time_stamp='" + time_stamp+"'";
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                info.id = rs.getInt(1);
                info.time_stamp = rs.getString(2);
                info.ori_sentence = rs.getString(3);
                info.jlmlmr = rs.getString(4);
                info.jlmlmr_abs = rs.getString(5);
                info.singlemr = rs.getString(6);
                info.random_choose = rs.getString(7);
                info.filter_sentence = rs.getString(8);
                info.topic_words = rs.getString(9);
                info.search_query = rs.getString(10);
                info.urls = rs.getString(11);
                break;
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
        return info;
    }

    public static void insertInfo(Info info) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBUitil.openConnection();
            String sql = "insert into info (time_stamp, ori_sentence, jlmlmr, jlmlmr_abs, singlemr, random_choose, filter_sentence, topic_words, search_query, urls) "
                    + "values (" +
                    "'" + info.time_stamp + "', " +
                    "'" + info.ori_sentence + "', " +
                    "'" + info.jlmlmr + "', " +
                    "'" + info.jlmlmr_abs + "', " +
                    "'" + info.singlemr + "', " +
                    "'" + info.random_choose + "', " +
                    "'" + info.filter_sentence + "', " +
                    "'" + info.topic_words + "', " +
                    "'" + info.search_query + "', " +
                    "'" + info.urls + "')";
            pstmt = con.prepareStatement(sql);
            int count = pstmt.executeUpdate();
            if (count != 1) {
                System.err.println("insertInfo error!");
            }
        } catch (SQLException ex) {
            System.err.println("SQL error, "+ex);
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
