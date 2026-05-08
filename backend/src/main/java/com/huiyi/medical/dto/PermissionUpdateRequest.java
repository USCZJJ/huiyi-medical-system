package com.huiyi.medical.dto;

import java.util.List;

public class PermissionUpdateRequest {
    private List<String> permissions;
    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
}

