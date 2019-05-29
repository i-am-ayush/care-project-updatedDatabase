package dao;

import bean.Job;
import org.apache.log4j.Logger;
import util.QueryExecutor;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class JobDao {
    private final static Logger logger = Logger.getLogger(DatabaseConnector.class);
    static Connection conn = DatabaseConnector.getConnection();

    public static boolean save(Job job) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into job(title,postedBy,startDateTime,endDateTime,payPerHour) values(?,?,?,?,?)");
            stmt.setString(1, job.getTitle());
            stmt.setInt(2, job.getPostedBy());
            java.sql.Date sqlDate1 = new java.sql.Date(job.getStartDateTime().getTime());
            java.sql.Date sqlDate2 = new java.sql.Date(job.getEndDateTime().getTime());
            stmt.setDate(3, sqlDate1);
            stmt.setDate(4, sqlDate2);
            stmt.setDouble(5, job.getPayPerHour());
            stmt.execute();
            stmt.close();
            return true;

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

            return false;
        }
    }
    public static void update(Job job){
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE job "
                    + "SET title=?, startDateTime=?, endDateTime=?, payPerHour=? "
                    + "WHERE id=?");
            stmt.setString(1,job.getTitle());
            java.sql.Date sqlDate1 = new java.sql.Date(job.getStartDateTime().getTime());
            java.sql.Date sqlDate2 = new java.sql.Date(job.getEndDateTime().getTime());
            stmt.setDate(2, sqlDate1);
            stmt.setDate(3, sqlDate2);
            stmt.setDouble(4,job.getPayPerHour());
            stmt.setInt(5,job.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Job getById(int jobId){
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM JOB  where id=?");
            stmt.setInt(1,jobId);
            ResultSet res = QueryExecutor.queryExecute(stmt);
            Job job=new Job();
            while (res.next()) {
                job.setId(res.getInt(1));
                job.setTitle(res.getString(2));
                job.setPostedBy(res.getInt(3));
                job.setStartDateTime(res.getDate(4));
                job.setEndDateTime(res.getDate(5));
                job.setPayPerHour(res.getDouble(6));
            }
            res.close();
            stmt.close();
            return job;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean delete(Job job){
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE job "
                    + "SET status='INACTIVE' "
                    + "WHERE id=?");
            stmt.setInt(1,job.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<Job> getJobsPostedBy(int id) {
        List<Job> resultList = new LinkedList<>();


        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM job WHERE postedBy=?")) {
            stmt.setInt(1, id);
            try (ResultSet res = stmt.executeQuery()) {
                while (res.next()) {
                    Job job=new Job();
                    job.setId(res.getInt("id"));
                    job.setTitle(res.getString("title"));
                    job.setStartDateTime(res.getDate("startDateTime"));
                    job.setEndDateTime(res.getDate("endDateTime"));
                    job.setPayPerHour(res.getDouble("payPerHour"));
                    resultList.add(job);
                }
            }
        } catch (Exception e) {
        }
        return resultList;
    }

    //public static void main(String[] args) {
//        Job job=new Job();
//        job.setId(1);
//        JobDao.delete(job);
//        Job job=null;
//        job=JobDao.getById(1);
//        System.out.println(job.getTitle());
//        List<Job> l=new ArrayList<>();
//        l=JobDao.getJobsPostedBy(1);
//        for(int i=0;i<l.size();i++){
//            System.out.println(l.get(i).getTitle());
//        }
//
//
//     Job job=new Job();
//        job.setPayPerHour(800.0);
//        Date date=new Date();
//        job.setPostedBy(4);
//        job.setStartDateTime(date);
//        job.setEndDateTime(date);
//        job.setTitle("baby care8");
//        JobDao.save(job);
//
//    }
}

