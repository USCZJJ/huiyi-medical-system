package com.huiyi.medical.entity;

import java.math.BigDecimal;

public class Medicine extends BaseEntity {
    private String medicineName;
    private String type;
    private String specification;
    private BigDecimal price;
    private String manufacturer;

    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSpecification() { return specification; }
    public void setSpecification(String specification) { this.specification = specification; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
}

