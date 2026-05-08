package com.huiyi.medical.entity;

public class Material extends BaseEntity {
    private String materialName;
    private String type;
    private String description;

    public String getMaterialName() { return materialName; }
    public void setMaterialName(String materialName) { this.materialName = materialName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

