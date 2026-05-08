package com.huiyi.medical.service;

import com.huiyi.medical.common.BusinessException;
import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.dto.CityQuery;
import com.huiyi.medical.entity.City;
import com.huiyi.medical.mapper.CityMapper;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private final CityMapper mapper;
    public CityService(CityMapper mapper) { this.mapper = mapper; }
    public PageResult<City> page(CityQuery q) { return new PageResult<>(mapper.findPage(q, q.offset(), q.safePageSize()), mapper.count(q), q.safePage(), q.safePageSize()); }
    public City detail(Long id) { City e = mapper.findById(id); if (e == null) throw new BusinessException(404, "城市不存在"); return e; }
    public Long create(City e) { validate(e); if (mapper.findByCode(e.getCode()) != null) throw new BusinessException(409, "城市代码已存在"); mapper.insert(e); return e.getId(); }
    public void update(Long id, City e) { e.setId(id); validate(e); City same = mapper.findByCode(e.getCode()); if (same != null && !same.getId().equals(id)) throw new BusinessException(409, "城市代码已存在"); mapper.update(e); }
    public void delete(Long id) { mapper.logicalDelete(id); }
    private void validate(City e) {
        if (e.getCityName() == null || e.getCityName().isBlank()) throw new BusinessException(400, "城市名称不能为空");
        if (e.getProvince() == null || e.getProvince().isBlank()) throw new BusinessException(400, "省份不能为空");
        if (e.getCode() == null || e.getCode().isBlank()) throw new BusinessException(400, "城市代码不能为空");
    }
}

