package com.huiyi.medical.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class UserRequest {
    @NotBlank
    private String username;
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String role;
    private List<String> permissions;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
}

