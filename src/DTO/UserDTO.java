package DTO;

import java.util.Date;

public class UserDTO {
    
    private int id;
    private String name;
    private String email;
    private String password;
    private String fullName;
    private int isAdmin;
    private boolean isDeleted;
    private String otp;
    private Date expired_time;
   
    public UserDTO() {
    }
    public UserDTO(int id, String name, String email, String password, String fullName, int isAdmin , Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
        this.isDeleted = isDeleted;
    }
    public UserDTO(String name, String email, String password, String fullName,int isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }
    

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }


    public UserDTO setId(int id) {
        this.id = id;
        return this;
    }
    
    public UserDTO setName(String name) {
        this.name = name;
        return this;
    }
    
    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }
    
    public UserDTO setPassword(String password) {
        this.password = password;
        return this;
    }
    
    public UserDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
    
    public UserDTO setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }
    
    public UserDTO setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }
    
    public UserDTO setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public UserDTO setExpiredTime(Date expired_time) {
        this.expired_time = expired_time;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public Date getExpiredTime() {
        return expired_time;
    }

    public static UserDTO builder(){
        return new UserDTO();
    }

    public UserDTO build(){
        return this;
    }
    
}
