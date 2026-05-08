package com.huiyi.medical.dto;

import jakarta.validation.constraints.NotBlank;

public class PasswordResetRequest {
    @NotBlank
    private String newPassword;
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}

