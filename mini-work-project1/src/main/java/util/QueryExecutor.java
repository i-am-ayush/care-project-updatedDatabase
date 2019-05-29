package util;

import dao.DatabaseConnector;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecutor {
    private final static Logger logger = Logger.getLogger(QueryExecutor.class);
    public static ResultSet queryExecute(PreparedStatement preparedStatement){
    try {
        ResultSet res=preparedStatement.executeQuery();
        return res;
    } catch (SQLException e) {
        logger.error(e.getMessage(),e);
        return null;
    }
}
}
