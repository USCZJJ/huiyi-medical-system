package com.huiyi.medical.controller;

import com.huiyi.medical.common.Result;
import com.huiyi.medical.service.DashboardService;
import com.huiyi.medical.vo.DashboardVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;
    public DashboardController(DashboardService service) { this.service = service; }
    @GetMapping public Result<DashboardVO> dashboard() { return Result.success(service.dashboard()); }
}

