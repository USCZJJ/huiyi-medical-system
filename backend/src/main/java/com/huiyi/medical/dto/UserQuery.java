package com.huiyi.medical.dto;

public class UserQuery extends PageQuery {
    private String username;
    private String role;
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

