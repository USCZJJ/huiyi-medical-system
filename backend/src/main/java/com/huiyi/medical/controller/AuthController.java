package com.huiyi.medical.controller;

import com.huiyi.medical.common.Result;
import com.huiyi.medical.dto.LoginRequest;
import com.huiyi.medical.service.UserService;
import com.huiyi.medical.vo.LoginVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        return Result.success(userService.login(request));
    }
}

