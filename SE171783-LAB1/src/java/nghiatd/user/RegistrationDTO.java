
package nghiatd.user;

import java.io.Serializable;

public class RegistrationDTO implements Serializable{
    // khai báo các props map với row trong table ko đc public 
    private String userID;
    private String password;
    private String fullName;
    private String roleID;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String userID, String password, String fullName, String roleID) {
        this.userID = userID;
        this.password = password;
        this.fullName = fullName;
        this.roleID = roleID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
    

    
}
