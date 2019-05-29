package dao;

import bean.Member;
import bean.Seeker;
import bean.Sitter;
import org.apache.log4j.Logger;
import util.QueryExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SitterDao {
    private final static Logger logger = Logger.getLogger(DatabaseConnector.class);
    static Connection conn = DatabaseConnector.getConnection();
    public static boolean save(Sitter sitter){
        MemberDao.save(sitter);
        int id=MemberDao.getByEmailid(sitter.getEmail());
        try {

            PreparedStatement stmt = conn.prepareStatement("insert into sitter(memberId,yearsOfExp,expectedPay) values(?,?,?)");
            stmt.setInt(1,id);
            stmt.setInt(2,sitter.getYearsOfExp());
            stmt.setDouble(3,sitter.getExpectedPay());
            boolean result=stmt.execute();
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
            return false;
        }
    }
    public static void update(Sitter sitter){
        MemberDao.update(sitter);
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE sitter "
                    + "SET yearsOfExp=?, expectedPay=? "
                    + "WHERE memberId=?");
            stmt.setInt(1, sitter.getYearsOfExp());
            stmt.setDouble(2, sitter.getExpectedPay());
            stmt.setInt(3, sitter.getMemberId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static boolean delete(int sitterId){
        return MemberDao.delete(sitterId);
    }
    public static Sitter getById(int sitterId){
        Sitter sitter=new Sitter();
        Member member=MemberDao.getById(sitterId);
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM sitter where memberId=?");
            stmt.setInt(1, sitterId);
            ResultSet res = QueryExecutor.queryExecute(stmt);
            while (res.next()) {
                sitter.setMemberId(res.getInt(1));
                sitter.setYearsOfExp(res.getInt(2));
                sitter.setExpectedPay(res.getDouble(3));
            }
            sitter.setId(member.getId());
            sitter.setAddress(member.getAddress());
            sitter.setEmail(member.getEmail());
            sitter.setFirstName(member.getFirstName());
            sitter.setLastName(member.getLastName());
            sitter.setPhoneNumber(member.getPhoneNumber());
            sitter.setType(member.getType());
            res.close();
            stmt.close();
            return sitter;

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
//
//    public static void main(String[] args) {
//        Sitter s=new Sitter();
//        s.setAddress("kota");
//        s.setEmail("abhishek@gmail.com");
//        s.setFirstName("abhishek");
//        s.setLastName("kumar");
//        s.setPassword("abhishek123");
//        s.setPhoneNumber(990173218);
//        s.setExpectedPay(800.0);
//        s.setYearsOfExp(4);
//        s.setType(Seeker.MemberType.SITTER);
//        SitterDao.save(s);
//    }
}

