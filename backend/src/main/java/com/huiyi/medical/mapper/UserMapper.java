package com.huiyi.medical.mapper;

import com.huiyi.medical.dto.UserQuery;
import com.huiyi.medical.entity.Permission;
import com.huiyi.medical.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User findById(@Param("id") Long id);
    List<User> findPage(@Param("query") UserQuery query, @Param("offset") int offset, @Param("pageSize") int pageSize);
    long count(@Param("query") UserQuery query);
    int insert(User user);
    int update(User user);
    int updatePassword(@Param("id") Long id, @Param("passwordHash") String passwordHash);
    int logicalDelete(@Param("id") Long id);
    List<String> findPermissionCodes(@Param("userId") Long userId);
    List<Permission> findAllPermissions();
    int deleteUserPermissions(@Param("userId") Long userId);
    int insertUserPermission(@Param("userId") Long userId, @Param("permissionCode") String permissionCode);
}

