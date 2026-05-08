package com.huiyi.medical.dto;

public class MaterialQuery extends PageQuery {
    private String materialName;
    private String type;
    public String getMaterialName() { return materialName; }
    public void setMaterialName(String materialName) { this.materialName = materialName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}

