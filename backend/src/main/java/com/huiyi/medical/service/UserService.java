package com.huiyi.medical.service;

import com.huiyi.medical.common.BusinessException;
import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.dto.*;
import com.huiyi.medical.entity.Permission;
import com.huiyi.medical.entity.User;
import com.huiyi.medical.mapper.UserMapper;
import com.huiyi.medical.security.JwtTokenProvider;
import com.huiyi.medical.vo.LoginVO;
import com.huiyi.medical.vo.UserVO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginVO login(LoginRequest request) {
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null || user.getStatus() == null || user.getStatus() != 1 || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        return new LoginVO(jwtTokenProvider.createToken(user), toVO(user));
    }

    public UserVO currentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }
        return toVO(user);
    }

    public PageResult<UserVO> page(UserQuery query) {
        List<UserVO> list = userMapper.findPage(query, query.offset(), query.safePageSize()).stream().map(this::toVO).toList();
        return new PageResult<>(list, userMapper.count(query), query.safePage(), query.safePageSize());
    }

    @Transactional
    public Long create(UserRequest request) {
        if (userMapper.findByUsername(request.getUsername()) != null) {
            throw new BusinessException(409, "用户名已存在");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BusinessException(400, "密码不能为空");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setStatus(1);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userMapper.insert(user);
        savePermissions(user.getId(), request.getPermissions());
        return user.getId();
    }

    @Transactional
    public void update(Long id, UserRequest request) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        User sameUsername = userMapper.findByUsername(request.getUsername());
        if (sameUsername != null && !sameUsername.getId().equals(id)) {
            throw new BusinessException(409, "用户名已存在");
        }
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setRole(request.getRole());
        userMapper.update(user);
        savePermissions(id, request.getPermissions());
    }

    public void delete(Long id) {
        userMapper.logicalDelete(id);
    }

    public List<Permission> permissions() {
        return userMapper.findAllPermissions();
    }

    public List<String> userPermissions(Long userId) {
        return userMapper.findPermissionCodes(userId);
    }

    @Transactional
    public void updatePermissions(Long userId, PermissionUpdateRequest request) {
        savePermissions(userId, request.getPermissions());
    }

    public void changePassword(PasswordChangeRequest request) {
        UserVO current = currentUser();
        User user = userMapper.findById(current.getId());
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
            throw new BusinessException(400, "原密码错误");
        }
        userMapper.updatePassword(user.getId(), passwordEncoder.encode(request.getNewPassword()));
    }

    public void resetPassword(Long id, PasswordResetRequest request) {
        if (userMapper.findById(id) == null) {
            throw new BusinessException(404, "用户不存在");
        }
        userMapper.updatePassword(id, passwordEncoder.encode(request.getNewPassword()));
    }

    private void savePermissions(Long userId, List<String> permissions) {
        userMapper.deleteUserPermissions(userId);
        if (permissions != null) {
            permissions.forEach(code -> userMapper.insertUserPermission(userId, code));
        }
    }

    private UserVO toVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setName(user.getName());
        vo.setRole(user.getRole());
        vo.setCreatedAt(user.getCreatedAt());
        vo.setPermissions(userMapper.findPermissionCodes(user.getId()));
        return vo;
    }
}

