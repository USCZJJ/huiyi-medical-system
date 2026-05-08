package com.huiyi.medical.service;

import com.huiyi.medical.common.BusinessException;
import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.dto.CompanyQuery;
import com.huiyi.medical.entity.Company;
import com.huiyi.medical.mapper.CompanyMapper;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyMapper mapper;
    public CompanyService(CompanyMapper mapper) { this.mapper = mapper; }
    public PageResult<Company> page(CompanyQuery q) { return new PageResult<>(mapper.findPage(q, q.offset(), q.safePageSize()), mapper.count(q), q.safePage(), q.safePageSize()); }
    public Company detail(Long id) { Company e = mapper.findById(id); if (e == null) throw new BusinessException(404, "公司不存在"); return e; }
    public Long create(Company e) { validate(e); mapper.insert(e); return e.getId(); }
    public void update(Long id, Company e) { e.setId(id); validate(e); mapper.update(e); }
    public void delete(Long id) { mapper.logicalDelete(id); }
    private void validate(Company e) {
        if (e.getCompanyName() == null || e.getCompanyName().isBlank()) throw new BusinessException(400, "公司名称不能为空");
        if (e.getContact() == null || e.getContact().isBlank()) throw new BusinessException(400, "联系人不能为空");
        if (e.getPhone() == null || e.getPhone().isBlank()) throw new BusinessException(400, "联系电话不能为空");
        if (e.getCity() == null || e.getCity().isBlank()) throw new BusinessException(400, "城市不能为空");
        if (e.getAddress() == null || e.getAddress().isBlank()) throw new BusinessException(400, "地址不能为空");
    }
}

