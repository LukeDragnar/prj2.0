/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiatd.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import nghiatd.utils.DBUtils;

/**
 *
 * @author ADMIN
 */
public class RegistrationDAO {  
    public boolean checkLogin(String username, String password)throws SQLException, NamingException, ClassNotFoundException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try{
        //1. connect DB
        con = DBUtils.getConnection();
        //2. create SQL statement String
        if(con!=null){           
            String sql = "Select userID " + "From tblUsers " + "Where userID = ? and password = ?"; 
            //3. Create statement to set sql
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            //4. excute query
            rs = stm.executeQuery();
            //5.process
            if(rs.next()){
                return true;
            }
          }//end if connection is exitsted
        }finally{
            if(rs != null){
                rs.close();
            }
            if(stm != null){
                stm.close(); 
            }
            if(con != null){
                con.close();
            }
        }
        return false;   
    }
    private List <RegistrationDTO> accountList;

    public List<RegistrationDTO> getAccountList() {
        return accountList;
    }
    
        public void searchLastname(String searchValue)
            throws SQLException, /*ClassNotFoundException*/ NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
      
        try {
            // 1. Make Connection
            con = DBUtils.getConnection(); // phải bắt lỗi ClassNotFoundException
            if (con != null) {
                // 2. Create SQL String
                String sql = "SELECT userID, password, fullName, roleID, status"
                        + " FROM tblUsers "
                        + " WHERE fullName LIKE ?";

                // 3. Create Statement object
                stm = con.prepareStatement(sql); // đón nhận câu lệnh sql 
                stm.setString(1, "%" + searchValue + "%");

                // 4. Excute Query
                rs = stm.executeQuery();

                // 5. Process // trả ra nhiều dòng dùng while 
                while (rs.next()) {
                    // 5.1 map
                    // 5.1.1 get data from Result Set
                    String userID = rs.getString("userID");
                    String password = rs.getString("password");
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    // 5.1.2 get data properties of DTO
                    RegistrationDTO dto = new RegistrationDTO(userID, password, fullName, roleID);
                    // 5.2 add to list 
                    if (this.accountList == null) {
                        this.accountList = new ArrayList<>();
                    } // end accountList has not existed
                    this.accountList.add(dto);
                } // end map data to store 
            } // end connection has existed        
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close(); // phải bắt lỗi SQLException
            }
        }
    }
        public boolean checkDuplicate(String userID) throws SQLException {
        boolean check = false;
        RegistrationDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement("SELECT userID FROM tblUsers WHERE userID=?");
                ptm.setString(1, userID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
        public boolean insertV2(RegistrationDTO user) throws SQLException, ClassNotFoundException{
        boolean checkInsert = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement("INSERT INTO tblUsers(userID, fullName, roleID, password)" + "VALUES(?,?,?,?)");
                ptm.setString(1, user.getUserID());
                ptm.setString(2, user.getFullName());
                ptm.setString(3, user.getRoleID());
                ptm.setString(4, user.getPassword());
                checkInsert = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception ex) {
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkInsert;
    }
        public boolean deleteAccount(String username)
                throws SQLException, /*ClassNotFoundException*/ NamingException,   ClassNotFoundException{
        Connection con = null;
        PreparedStatement stm = null; //dùng prepareStatement vì ko cần đọc nhiều lần, chỉ cần chạy 1 lần
        ResultSet rs = null;
        boolean result = false;
        try{
            // 1. Make Connection
            con = DBUtils.getConnection(); 
            if (con != null){
                // 2. Create SQL String
                String sql = "Delete From tblUsers "
                        + "Where userID = ?"; 

                // 3. Create Statement object
                stm = con.prepareStatement(sql); // đón nhận câu lệnh sql 
                stm.setString(1, username);

                // 4. Excute Query
                int effectRows = stm.executeUpdate();

                // 5. Process
                if (effectRows > 0){
                    result = true;
                }
            } // end connection has existed        
        }finally{
            if (rs != null){
                rs.close();
            }
            if (stm != null){
                stm.close();
            }
            if (con != null){
                con.close(); // phải bắt lỗi SQLException
            }
        }
        return result;
    }
    
    public boolean updateAccount(String username, String password, String fullname)
                throws SQLException, /*ClassNotFoundException*/ NamingException,   ClassNotFoundException{
        Connection con = null;
        PreparedStatement stm = null; //dùng prepareStatement vì ko cần đọc nhiều lần, chỉ cần chạy 1 lần
        ResultSet rs = null;
        boolean result = false;
        try{
            // 1. Make Connection
            con = DBUtils.getConnection(); // phải bắt lỗi ClassNotFoundException
            if (con != null){
                // 2. Create SQL String
                String sql = "UPDATE tblUsers SET password = ?, fullName = ? WHERE userID = ?";

                // 3. Create Statement object
                stm = con.prepareStatement(sql); // đón nhận câu lệnh sql 
                stm.setString(1, password);
                stm.setString(2, fullname);
                stm.setString(3, username);

                // 4. Excute Query
                int effectRows = stm.executeUpdate();

                // 5. Process
                if (effectRows > 0){
                    result = true;
                }
            } // end connection has existed        
        }finally{
            if (rs != null){
                rs.close();
            }
            if (stm != null){
                stm.close();
            }
            if (con != null){
                con.close(); // phải bắt lỗi SQLException
            }
        }
        return result;
    }
}