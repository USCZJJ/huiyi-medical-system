package com.huiyi.medical.entity;

import java.time.LocalDate;

public class Policy extends BaseEntity {
    private String policyName;
    private Long companyId;
    private String companyName;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private String content;

    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public LocalDate getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(LocalDate effectiveDate) { this.effectiveDate = effectiveDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}

