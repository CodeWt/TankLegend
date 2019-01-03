package history.JDBC;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Created by  qiao
 * @date 18-2-27 上午11:46
 */

/**
 * c3p0的连接池.
 */
public class ConnectionPool {
    private DataSource ds;
    private static ConnectionPool pool;
    private ConnectionPool(){
        ds = new ComboPooledDataSource();
    }
    public static final ConnectionPool getInstance(){
        if(pool==null){
            try{
                pool = new ConnectionPool();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("========================ConnectionPool getInstance");
        return pool;
    }
    public synchronized final Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("========================ConnectionPool getConnection");
        return null;
    }
}
