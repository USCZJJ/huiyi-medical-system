package com.huiyi.medical.dto;

public class PolicyQuery extends PageQuery {
    private String policyName;
    private Long companyId;
    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
}

