package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import handler.HttpHeadHandler;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Created by  qiao
 * @date 18-2-27 上午10:46
 */

public class C3P0Utils {
    private final static String filename = "/home/qiao/IdeaProjects/tanklegendnetty/src/main/resources/c3p0.properties";
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, String> mapSmall = new ConcurrentHashMap<>();
    private static Logger logger = Logger.getLogger(HttpHeadHandler.class.getName());

    public static DataSource getDataSource() {
        return dataSource;
    }

    static {
        map = C3P0Utils.readConfig();
        Class clazz = null;
        try {
            clazz = Class.forName("com.mchange.v2.c3p0.ComboPooledDataSource");
            dataSource = (ComboPooledDataSource) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Method[] methods = clazz.getDeclaredMethods(  );
        for (String str1 : map.keySet()) {
            String value = map.get(str1);
            for (Method method : methods) {
                String methodname = "set" + str1.substring(0, 1).toUpperCase() + str1.substring(1, str1.length());
                if (method.getName().equals(methodname)) {
                    Class<?>[] clas = method.getParameterTypes();
                    if (clas[0] == String.class) {
                        try {
                            method.invoke(dataSource, value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            method.invoke(dataSource, Integer.parseInt(value));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 将c3p0.properties文件中的信息收集到Map中.
     * @return
     */
    private static ConcurrentHashMap<String, String> readConfig(){
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filename));

            String str = "";
            while ((str = br.readLine()) != null) {
                String[] strings = str.split("=");
                mapSmall.put(strings[0], strings[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mapSmall;
    }

    /**
     * 建立连接
     * @return
     */
    public static Connection getConnection() {
        try {
            logger.info("===================================jdbc success =================");
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行更新操作.
     * @param sql
     * @param args
     * @return
     * @throws Exception
     */
    public static int updata(String sql, Object... args) throws Exception {
        PreparedStatement preparedStatement = null;
        int number = 0;
        preparedStatement = C3P0Utils.getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i+1 , args[i]);
        }
        number = preparedStatement.executeUpdate();
        return number;
    }
}