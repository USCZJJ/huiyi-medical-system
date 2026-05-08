package com.huiyi.medical.service;

import com.huiyi.medical.common.BusinessException;
import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.dto.LocationQuery;
import com.huiyi.medical.entity.Location;
import com.huiyi.medical.mapper.LocationMapper;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private final LocationMapper mapper;
    public LocationService(LocationMapper mapper) { this.mapper = mapper; }
    public PageResult<Location> page(LocationQuery q) { return new PageResult<>(mapper.findPage(q, q.offset(), q.safePageSize()), mapper.count(q), q.safePage(), q.safePageSize()); }
    public Location detail(Long id) { Location e = mapper.findById(id); if (e == null) throw new BusinessException(404, "地点不存在"); return e; }
    public Long create(Location e) { validate(e); mapper.insert(e); return e.getId(); }
    public void update(Long id, Location e) { e.setId(id); validate(e); mapper.update(e); }
    public void delete(Long id) { mapper.logicalDelete(id); }
    private void validate(Location e) {
        if (e.getLocationName() == null || e.getLocationName().isBlank()) throw new BusinessException(400, "地点名称不能为空");
        if (e.getCity() == null || e.getCity().isBlank()) throw new BusinessException(400, "城市不能为空");
        if (e.getAddress() == null || e.getAddress().isBlank()) throw new BusinessException(400, "地址不能为空");
        if (e.getContact() == null || e.getContact().isBlank()) throw new BusinessException(400, "联系人不能为空");
        if (e.getPhone() == null || e.getPhone().isBlank()) throw new BusinessException(400, "联系电话不能为空");
    }
}

