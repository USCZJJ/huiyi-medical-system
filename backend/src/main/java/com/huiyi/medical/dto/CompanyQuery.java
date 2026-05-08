package com.huiyi.medical.dto;

public class CompanyQuery extends PageQuery {
    private String companyName;
    private String city;
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}

