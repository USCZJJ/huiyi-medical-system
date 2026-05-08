package com.huiyi.medical.controller;

import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.common.Result;
import com.huiyi.medical.dto.*;
import com.huiyi.medical.entity.Permission;
import com.huiyi.medical.service.UserService;
import com.huiyi.medical.vo.UserVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/me")
    public Result<UserVO> currentUser() {
        return Result.success(userService.currentUser());
    }

    @GetMapping("/users")
    public Result<PageResult<UserVO>> users(UserQuery query) {
        return Result.success(userService.page(query));
    }

    @PostMapping("/users")
    public Result<Map<String, Long>> create(@Valid @RequestBody UserRequest request) {
        return Result.success(Map.of("id", userService.create(request)));
    }

    @PutMapping("/users/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        userService.update(id, request);
        return Result.success();
    }

    @DeleteMapping("/users/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }

    @GetMapping("/permissions")
    public Result<List<Permission>> permissions() {
        return Result.success(userService.permissions());
    }

    @GetMapping("/users/{id}/permissions")
    public Result<List<String>> userPermissions(@PathVariable Long id) {
        return Result.success(userService.userPermissions(id));
    }

    @PutMapping("/users/{id}/permissions")
    public Result<Void> updatePermissions(@PathVariable Long id, @RequestBody PermissionUpdateRequest request) {
        userService.updatePermissions(id, request);
        return Result.success();
    }

    @PutMapping("/users/me/password")
    public Result<Void> changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        userService.changePassword(request);
        return Result.success();
    }

    @PutMapping("/users/{id}/password/reset")
    public Result<Void> resetPassword(@PathVariable Long id, @Valid @RequestBody PasswordResetRequest request) {
        userService.resetPassword(id, request);
        return Result.success();
    }
}

