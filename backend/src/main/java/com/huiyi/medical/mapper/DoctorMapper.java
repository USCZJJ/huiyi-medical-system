package com.huiyi.medical.mapper;

import com.huiyi.medical.dto.DoctorQuery;
import com.huiyi.medical.entity.Doctor;
import com.huiyi.medical.vo.DistributionVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DoctorMapper {
    List<Doctor> findPage(@Param("query") DoctorQuery query, @Param("offset") int offset, @Param("pageSize") int pageSize);
    long count(@Param("query") DoctorQuery query);
    Doctor findById(@Param("id") Long id);
    int insert(Doctor entity);
    int update(Doctor entity);
    int logicalDelete(@Param("id") Long id);
    long activeCount();
    List<DistributionVO> countByTitle();
    List<DistributionVO> countByDepartment();
}

