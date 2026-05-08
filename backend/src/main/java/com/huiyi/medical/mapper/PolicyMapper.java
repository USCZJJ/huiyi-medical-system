package com.huiyi.medical.mapper;

import com.huiyi.medical.dto.PolicyQuery;
import com.huiyi.medical.entity.Policy;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PolicyMapper {
    List<Policy> findPage(@Param("query") PolicyQuery query, @Param("offset") int offset, @Param("pageSize") int pageSize);
    long count(@Param("query") PolicyQuery query);
    Policy findById(@Param("id") Long id);
    int insert(Policy entity);
    int update(Policy entity);
    int logicalDelete(@Param("id") Long id);
    List<Policy> findLatest(@Param("limit") int limit);
}

