package com.huiyi.medical.mapper;

import com.huiyi.medical.dto.CompanyQuery;
import com.huiyi.medical.entity.Company;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CompanyMapper {
    List<Company> findPage(@Param("query") CompanyQuery query, @Param("offset") int offset, @Param("pageSize") int pageSize);
    long count(@Param("query") CompanyQuery query);
    Company findById(@Param("id") Long id);
    int insert(Company entity);
    int update(Company entity);
    int logicalDelete(@Param("id") Long id);
    long activeCount();
}

