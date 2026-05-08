package com.huiyi.medical.dto;

public class MedicineQuery extends PageQuery {
    private String medicineName;
    private String type;
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}

