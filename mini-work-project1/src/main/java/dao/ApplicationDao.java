package dao;

import bean.Application;
import org.apache.log4j.Logger;
import util.QueryExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ApplicationDao {
    private final static Logger logger = Logger.getLogger(DatabaseConnector.class);
    static Connection conn = DatabaseConnector.getConnection();

    public static boolean save(Application application) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into application(jobId,memberId,expectedPay) values(?,?,?)");
            stmt.setInt(1, application.getJobId());
            stmt.setInt(2, application.getMemberId());
            stmt.setDouble(3, application.getExpectedPay());
            boolean result = stmt.execute();
            stmt.close();
            return result;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    public static void update(Application application) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE application "
                    + "SET expectedPay=? "
                    + "WHERE id=?");
            stmt.setInt(2, application.getApplicationId());
            stmt.setDouble(1, application.getExpectedPay());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

        }
    }

    public static boolean delete(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE job_application "
                    + "SET status='INACTIVE' "
                    + "WHERE id=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    public static Application getById(int applicationId) {
        Application application = new Application();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM application where id=?");
            stmt.setInt(1, applicationId);
            ResultSet res = QueryExecutor.queryExecute(stmt);
            while (res.next()) {
                application.setApplicationId(res.getInt(1));
                application.setJobId(res.getInt(2));
                application.setMemberId(res.getInt(3));
                application.setExpectedPay(res.getDouble(4));
                Application.Status status = Application.Status.valueOf(res.getString("status"));
                application.setStatus(status);
                res.close();
                stmt.close();
            }
            return application;

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean updateByExpectedPay(int jobAppId, double expectedPay) {
        boolean isUpdated = false;

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE application "
                + "SET expectePay=? "
                + "WHERE id=?")) {
            stmt.setDouble(1, expectedPay);
            stmt.setInt(2, jobAppId);
            int affectedRows = stmt.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return isUpdated;
    }

    public static List<Application> get(int id) {
        List<Application> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM application where memberId=?");
            stmt.setInt(1, id);
            ResultSet res = QueryExecutor.queryExecute(stmt);
            while (res.next()) {
                Application application = new Application();
                application.setApplicationId(res.getInt(1));
                application.setJobId(res.getInt(2));
                //application.setMemberId(res.getInt(3));
                application.setExpectedPay(res.getDouble(4));
                Application.Status status = Application.Status.valueOf(res.getString("status"));
                application.setStatus(status);
                list.add(application);
            }
            return list;

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static List<Map<String, Object>> getSitterNotAppliedJobsList(int userId) {
        List<Map<String, Object>> resultList = new LinkedList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT id, title, startDateTime, payPerHour FROM job WHERE id NOT IN " +
                "(SELECT jobId FROM Application WHERE memberId = ?) AND status='ACTIVE'")) {
            stmt.setInt(1, userId);
            try (ResultSet res = stmt.executeQuery()) {
                while (res.next()) {
                    Map<String, Object> tempMap = new HashMap<>();
                    tempMap.put("jobId", res.getInt("id"));
                    tempMap.put("title", res.getString("title"));
                    tempMap.put("startDate", res.getDate("startDateTime"));
                    tempMap.put("payPerHour", res.getDouble("payPerHour"));
                    resultList.add(tempMap);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultList;
    }

    public static List<Application> get(int id, Application.Status status) {
        List<Application> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM application where memberId=? and status=?");
            stmt.setInt(1, id);
            stmt.setString(2, String.valueOf(status));

            ResultSet res = QueryExecutor.queryExecute(stmt);
            while (res.next()) {
                Application application = new Application();
                application.setApplicationId(res.getInt(1));
                application.setJobId(res.getInt(2));
                //application.setMemberId(res.getInt(3));
                application.setExpectedPay(res.getDouble(4));
                //Application.Status status = Application.Status.valueOf(res.getString("status"));
                application.setStatus(status);
                list.add(application);
            }
            return list;

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static List<Map<String, Object>> getSitterJobListint (int userId) {
        List<Map<String, Object>> resultList = new LinkedList<>();
        try (PreparedStatement stmt = conn.prepareStatement("select title,app.status, startDateTime, expectedPay from application app, job jb" +
                " where app.jobId = jb.id and app.memberId=?")) {
            stmt.setInt(1, userId);
            try (ResultSet res = stmt.executeQuery()) {
                while (res.next()) {
                    Map<String, Object> tempMap = new HashMap<>();
                    //tempMap.put("id", res.getInt("id"));
                    tempMap.put("title", res.getString("title"));
                    tempMap.put("startDate", res.getString("startDateTime"));
                    tempMap.put("status", Application.Status.valueOf(res.getString("status")));
                    tempMap.put("expectedPay", res.getDouble("expectedPay"));
                    resultList.add(tempMap);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultList;
    }
    public static List<Map<String, Object>> getAllJobBySitter (int jobId) {
        List<Map<String, Object>> resultList = new LinkedList<>();
        try (PreparedStatement stmt = conn.prepareStatement("Select a.id, firstName, a.status, expectedPay from member m, Application a where a.memberId = m.id and jobId = ?")){
            stmt.setInt(1, jobId);
            try (ResultSet res = stmt.executeQuery()) {
                while (res.next()) {
                    Map<String, Object> tempMap = new HashMap<>();
                    //tempMap.put("id", res.getInt("id"));
                    tempMap.put("id", res.getInt("id"));
                    tempMap.put("firstName", res.getString("firstName"));
                    tempMap.put("status", Application.Status.valueOf(res.getString("status")));
                    tempMap.put("expectedPay", res.getDouble("expectedPay"));
                    resultList.add(tempMap);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultList;
    }

    public boolean deleteByJobId(int jobId) {

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE Application "
                + "SET status='INACTIVE' "
                + "WHERE jobId=?"
        )) {
            stmt.setInt(1, jobId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean deleteByuserId(int userId) {

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE Application "
                + "SET status='INACTIVE' "
                + "WHERE memberId=?"
        )) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public static void main(String[] args) {
//        Application ap=new Application();
//        ap.setMemberId(8);
//        ap.setJobId(8);
//        ap.setExpectedPay(500);
//        save(ap);
        List<Map<String, Object>> l = new ArrayList<>();

        l = ApplicationDao.getSitterJobListint(5);
        for (int i = 0; i < l.size(); i++) {
            System.out.println("------------neww job-----------");
            for (Map.Entry<String, Object> entry : l.get(i).entrySet()) {
                System.out.println("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());
            }
        }

    }
}
