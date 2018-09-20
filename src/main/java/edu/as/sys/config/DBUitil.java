
package edu.as.sys.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class DBUitil {
    private static DataSource ds = null;

    static {
        try {
            InputStream in = DBUitil.class.getClassLoader()
                    .getResourceAsStream("ds.properties");
            Properties props = new Properties();
            props.load(in);
            ds = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection openConnection() throws SQLException {
        return ds.getConnection();
    }


//    private static boolean status = true;
//    private static boolean init() {
//        PropKit.use("app.properties");
//        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
//        /**配置druid监控**/
//        dp.addFilter(new StatFilter());
//        WallFilter wall = new WallFilter();
//        wall.setDbType("mysql");
//        dp.addFilter(wall);
//        dp.start();
//        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
//        arp.addMapping("User","User_id", User.class);
//        arp.addMapping("Weibo","weibo_id", Weibo.class);
//        arp.addMapping("Comment","comment_id", Comment.class);
//        arp.addMapping("Product","product_id", Product.class);
//        arp.start();
//        return true;
//    }
//
//    public static void start(){
//        if(status){
//            init();
//            status = false;
//        }
//    }

}