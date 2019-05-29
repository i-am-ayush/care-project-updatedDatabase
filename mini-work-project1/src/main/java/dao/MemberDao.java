package dao;

import bean.Member;
import org.apache.log4j.Logger;
import util.QueryExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {
    private final static Logger logger = Logger.getLogger(DatabaseConnector.class);

    static Connection conn = DatabaseConnector.getConnection();

    public static boolean save(Member member) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into member(firstName,lastName,phoneNumber,email,type,address,password) values(?,?,?,?,?,?,?)");
            stmt.setString(1, member.getFirstName());
            stmt.setString(2, member.getLastName());
            stmt.setInt(3, member.getPhoneNumber());
            stmt.setString(4, member.getEmail());
            stmt.setString(5, String.valueOf(member.getType()));
            stmt.setString(6, member.getAddress());
            stmt.setString(7, member.getPassword());
            stmt.execute();
            stmt.close();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
    public static void update(Member member){
        try{
        PreparedStatement stmt = conn.prepareStatement("UPDATE member "
                + "SET firstName=?, lastName=?, phoneNumber=?, email=?, address=?"
                + "WHERE id=?");
        stmt.setString(1, member.getFirstName());
        stmt.setString(2, member.getLastName());
        stmt.setInt(3, member.getPhoneNumber());
        stmt.setString(4, member.getEmail());
        stmt.setString(5, member.getAddress());
        stmt.setInt(6,member.getId());
        stmt.executeUpdate();
        stmt.close();
    } catch (Exception e) {
        logger.error(e.getMessage(), e);
    }
    }
    public static boolean delete(int memberId){

        try {
            PreparedStatement stmt = conn.prepareStatement( "UPDATE member "
                    + "SET status='INACTIVE' "
                    + "WHERE id=?");
            stmt.setInt(1,memberId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
//    public static Member getById(int memberId) {
//        try {
//            Member member = new Member();
//            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MEMBER where id=?");
//            stmt.setInt(1, memberId);
//
//            ResultSet res = QueryExecutor.queryExecute(stmt);
//            while (res.next()) {
//                member.setId(res.getInt());
//                member.setFirstName(res.getString(2));
//                member.setLastName(res.getString(3));
//                member.setPhoneNumber(res.getInt(4));
//                member.
//                member.setEmail(res.getString(5));
//                member.setType(Member.MemberType.stringToEnum(res.getString(6)));
//                member.setAddress(res.getString(7));
//                member.setPassword(res.getString(9));
//            }
//            res.close();
//            stmt.close();
//            return member;
//        } catch (SQLException e) {
//            logger.error(e.getMessage(), e);
//            return null;
//        }
//
//    }

    public static Member getById(int memberId) {
        try {
           // List<Member> listOfMember = new ArrayList<>();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MEMBER where id=?");
            stmt.setInt(1,memberId);
            ResultSet res = QueryExecutor.queryExecute(stmt);

            Member member = new Member();
            while (res.next()) {

                member.setId(res.getInt("id" ));
                member.setFirstName(res.getString("firstName"));
                member.setLastName(res.getString("lastName"));
                member.setPhoneNumber(res.getInt("phoneNumber"));
                member.setEmail(res.getString("email"));
                member.setType(Member.MemberType.stringToEnum(res.getString("type")));
                member.setAddress(res.getString("address"));
                member.setStatus(Member.Status.valueOf(res.getString("status")));
            }
            res.close();
            stmt.close();
            return member;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }
public static boolean getByEmailid(String emailId,String password){

    try {
        PreparedStatement stmt = conn.prepareStatement("select id from member where email=? and password=?");
        stmt.setString(1,emailId);
        stmt.setString(2,password);
        ResultSet res = QueryExecutor.queryExecute(stmt);
        if(res.next()){
            return true;
        }
        else{
            return false;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    public static int getByEmailid(String emailId){
        int id=0;
        try {
            PreparedStatement stmt = conn.prepareStatement("select id from member where email=?");
            stmt.setString(1,emailId);
            ResultSet res = QueryExecutor.queryExecute(stmt);
            while(res.next()){
                id= res.getInt("id");
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return id;
    }
    public static void main(String[] args) {
        Member m=new Member();
        m.setAddress("niki");
        m.setEmail("nikiemail");
        m.setPhoneNumber(909009099);
        m.setFirstName("nik");
        m.setLastName("kinj");
        m.setType(Member.MemberType.SEEKER);
        m.setPassword("password");

        System.out.println("ok");
        save(m);
      Member m1=  getById(1);
        System.out.println(m1.getLastName());
}
}


