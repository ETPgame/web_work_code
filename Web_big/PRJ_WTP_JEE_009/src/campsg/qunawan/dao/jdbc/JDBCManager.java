package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JDBCManager {

    private static DataSource dataSource;

    // 静态代码块，初始化数据源
    static {
        try {
            InitialContext context = new InitialContext();
            // 获取数据源对象
            dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/mydb");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据失败");
        }
    }

    // 获取数据库连接
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接失败");
        }
    }

    // 归还数据库连接
    public static void close(Object o) {
        if (o != null) {
            if (o instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) o).close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("关闭数据源失败");
                }
            } else if (o instanceof Connection) {
                // 如果是连接对象，归还连接
                try {
                    ((Connection) o).close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException("关闭数据连接失败");
                }
            }
        }
    }
}
