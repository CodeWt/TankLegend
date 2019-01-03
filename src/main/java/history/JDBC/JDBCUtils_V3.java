package history.JDBC;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 提供获取连接和释放资源的 方法
 * 
 * @author Never Say Never
 * @date 2016年7月29日
 * @version V1.0
 */
public class JDBCUtils_V3 {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	/**
	 * 静态代码块加载配置文件信息
	 */
	static {
		try {
			// 1.创建一个properties对象
			Properties props = new Properties();
			props.load(new FileInputStream("/home/qiao/桌面/qiaodemo/src/main/resources/db.properties"));
			// 2.获取相关参数的值
			driver = props.getProperty("driver");
			url = props.getProperty("url");
			username = props.getProperty("username");
			password = props.getProperty("password");
			System.out.println(driver+"  "+url+"  "+username+"  "+password);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取连接方法
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void release(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static Connection aaa(){
		System.out.println("调用成功.");
		return null;
	}
}
