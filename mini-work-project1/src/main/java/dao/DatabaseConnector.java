package dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final static Logger logger = Logger.getLogger(DatabaseConnector.class);
    private static Connection CONNECTION_INSTANCE;
    private static String DATABASE_URL = "jdbc:mysql://localhost:3306/care";

    private static Connection createConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DATABASE_URL,"root","123456");
            logger.info("Connection to SQLite has been established.");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }

        return conn;
    }

    public static Connection getConnection() {
        if (CONNECTION_INSTANCE == null) {
            CONNECTION_INSTANCE = createConnection();
        }
        return CONNECTION_INSTANCE;
    }
}
