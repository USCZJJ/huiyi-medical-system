package com.huiyi.medical.service;

import com.huiyi.medical.common.BusinessException;
import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.dto.PolicyQuery;
import com.huiyi.medical.entity.Policy;
import com.huiyi.medical.mapper.PolicyMapper;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    private final PolicyMapper mapper;
    public PolicyService(PolicyMapper mapper) { this.mapper = mapper; }
    public PageResult<Policy> page(PolicyQuery q) { return new PageResult<>(mapper.findPage(q, q.offset(), q.safePageSize()), mapper.count(q), q.safePage(), q.safePageSize()); }
    public Policy detail(Long id) { Policy e = mapper.findById(id); if (e == null) throw new BusinessException(404, "政策不存在"); return e; }
    public Long create(Policy e) { validate(e); mapper.insert(e); return e.getId(); }
    public void update(Long id, Policy e) { e.setId(id); validate(e); mapper.update(e); }
    public void delete(Long id) { mapper.logicalDelete(id); }
    private void validate(Policy e) {
        if (e.getPolicyName() == null || e.getPolicyName().isBlank()) throw new BusinessException(400, "政策名称不能为空");
        if (e.getCompanyId() == null) throw new BusinessException(400, "适用公司不能为空");
        if (e.getEffectiveDate() == null) throw new BusinessException(400, "生效日期不能为空");
        if (e.getExpiryDate() == null) throw new BusinessException(400, "到期日期不能为空");
        if (e.getExpiryDate().isBefore(e.getEffectiveDate())) throw new BusinessException(400, "到期日期不能早于生效日期");
    }
}

