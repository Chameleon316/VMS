package org.lw.vms.controller;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import io.jsonwebtoken.Claims;
import org.lw.vms.entity.Mechanic;
import org.lw.vms.entity.RepairOrder;
import org.lw.vms.service.MechanicService;
import org.lw.vms.utils.JwtUtil;
import org.lw.vms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest; // 用于获取请求头

/**
 * 维修人员相关的 RESTful API 控制器。
 */
@RestController
@RequestMapping("/api/mechanic")
public class MechanicController {

    @Autowired
    private MechanicService mechanicService;

    @Autowired
    private JwtUtil jwtUtil; // 注入 JwtUtil

    /**
     * 根据用户ID获取维修人员的详细信息 (含工种、时薪)。
     * 只有维修人员本人或管理员可以查询。
     *
     * @param userId 用户 ID (从 JWT 中获取或路径参数)
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 维修人员详细信息
     */
    @GetMapping("/details/{userId}")
    public Result<Mechanic> getMechanicDetails(@PathVariable Integer userId, @RequestHeader("Authorization") String token) {
        // 从 JWT 中解析出当前用户的 ID 和角色
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer currentUserId = claims.get("userId", Integer.class);
        String currentUserRole = claims.get("role", String.class);

        // 权限校验：只有维修人员本人或管理员可以查询
        if (!userId.equals(currentUserId) && !"admin".equals(currentUserRole)) {
            return Result.fail(403, "无权限查询其他维修人员信息");
        }

        Mechanic mechanic = mechanicService.getMechanicDetailsByUserId(userId);
        if (mechanic != null) {
            return Result.success(mechanic, "查询维修人员详情成功");
        }
        return Result.fail("维修人员不存在");
    }

    /**
     * 根据维修人员ID查询其当前和历史维修工单。
     * 只有维修人员本人或管理员可以查询。
     *
     * @param mechanicId 维修人员 ID
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 维修工单列表
     */
    @GetMapping("/{mechanicId}/orders")
    public Result<List<RepairOrder>> getMechanicOrders(@PathVariable Integer mechanicId, @RequestHeader("Authorization") String token) {
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer currentUserId = claims.get("userId", Integer.class);
        String currentUserRole = claims.get("role", String.class);

        // 获取该 mechanicId 对应的 user_id，以便进行权限校验
        Mechanic mechanic = mechanicService.getMechanicDetailsByUserId(currentUserId); // 注意这里用 currentUserId 获取当前登录的维修人员信息
        if (mechanic == null || (!mechanicId.equals(mechanic.getMechanicId()) && !"admin".equals(currentUserRole))) {
            return Result.fail(403, "无权限查询此维修人员的工单");
        }

        List<RepairOrder> orders = mechanicService.getMechanicRepairOrders(mechanicId);
        return Result.success(orders, "查询维修工单成功");
    }

    /**
     * 根据维修人员ID查询其累计工时费收入。
     * 只有维修人员本人或管理员可以查询。
     *
     * @param mechanicId 维修人员 ID
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 累计工时费收入
     */
    @GetMapping("/{mechanicId}/income")
    public Result<BigDecimal> getMechanicIncome(@PathVariable Integer mechanicId, @RequestHeader("Authorization") String token) {
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer currentUserId = claims.get("userId", Integer.class);
        String currentUserRole = claims.get("role", String.class);

        Mechanic mechanic = mechanicService.getMechanicDetailsByUserId(currentUserId);
        if (mechanic == null || (!mechanicId.equals(mechanic.getMechanicId()) && !"admin".equals(currentUserRole))) {
            return Result.fail(403, "无权限查询此维修人员的收入");
        }

        BigDecimal totalIncome = mechanicService.getMechanicTotalLaborCostIncome(mechanicId);
        return Result.success(totalIncome, "查询累计工时费收入成功");
    }

    /**
     * 更新维修人员的工种和时薪。
     * 只有维修人员本人或管理员可以操作。
     * @param mechanicId 维修人员 ID
     * @param specialty 工种
     * @param hourlyRate 时薪
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 更新后的维修人员信息
     */
    @PutMapping("/{mechanicId}")
    public Result<Mechanic> updateMechanicInfo(
            @PathVariable Integer mechanicId,
            @RequestParam String specialty,
            @RequestParam BigDecimal hourlyRate,            @RequestHeader("Authorization") String token
    ) {
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        System.out.println(mechanicId);
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
//        Integer currentUserId = claims.get("userId", Integer.class);
        String currentUserRole = claims.get("role", String.class);
        Integer currentUserId = mechanicId;

        Mechanic mechanic = mechanicService.getMechanicDetailsById(currentUserId);
        System.out.println(mechanic);
        if (mechanic == null || (!mechanicId.equals(mechanic.getMechanicId()) && !"admin".equals(currentUserRole))) {
            return Result.fail(403, "无权限更新此维修人员的信息");
        }
        if (!"mechanic".equals(currentUserRole) && !"admin".equals(currentUserRole)) {
            return Result.fail(403, "只有维修人员或管理员可以更新维修人员信息");
        }

        Mechanic updatedMechanic = mechanicService.updateMechanicInfo(mechanicId, specialty, hourlyRate);
        if (updatedMechanic != null) {
            return Result.success(updatedMechanic, "维修人员信息更新成功");
        }
        return Result.fail("维修人员信息更新失败");
    }
}

