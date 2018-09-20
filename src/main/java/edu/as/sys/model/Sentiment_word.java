package edu.as.sys.model;

import edu.as.sys.config.DBUitil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by dell on 2017/2/20.
 */
public class Sentiment_word {
    public static HashSet<String> POSITIVE;
    public static HashSet<String> NEGATIVE;

    static {
        POSITIVE = getWordList(1);
        NEGATIVE = getWordList(-1);
    }
    public static HashSet<String> getWordList(int sentiment_type) {
        HashSet<String> result = new HashSet<>();
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBUitil.openConnection();
            String sql = "select Sentiment_word.word from Sentiment_word where Sentiment_word.sentiment_type=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, sentiment_type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("word"));
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
}
