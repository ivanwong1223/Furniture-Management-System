/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.util.ArrayList;

/**
 *
 * @author johnp
 */
abstract public class User {
    private String id;
    private String name;
    private String password;
    private String role;
    private String contactNo;
    private String email;
    private String status;

    public User(String id, String name, String password, String role, String contactNo, String email, String status) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.contactNo = contactNo;
        this.email = email;
        this.status = status;
    }
    
    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     *
     * @param userId
     * @return
     */
    public abstract ArrayList<?> importUsers(String userId);
    
    public abstract boolean updateDetails(String id, String name, String password, String role, String contactNo, String email);
    
    
}
