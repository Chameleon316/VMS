package org.lw.vms.controller;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import io.jsonwebtoken.Claims;
import org.lw.vms.DTOs.MaterialConsumptionRequest;
import org.lw.vms.entity.Material;
import org.lw.vms.entity.MaterialConsumption;
import org.lw.vms.service.MaterialConsumptionService;
import org.lw.vms.service.MaterialService;
import org.lw.vms.service.RepairOrderService;
import org.lw.vms.utils.JwtUtil;
import org.lw.vms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 材料管理和材料消耗相关的 RESTful API 控制器。
 */
@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialConsumptionService materialConsumptionService;

    @Autowired
    private RepairOrderService repairOrderService; // 用于更新工单的总材料费和总工时费

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 管理员新增材料。
     * 权限：admin
     * @param material 材料信息
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @PostMapping
    public Result<Material> addMaterial(@RequestBody Material material, @RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only admins can add materials");
        }

        Material newMaterial = materialService.addMaterial(material);
        if (newMaterial != null) {
            return Result.success(newMaterial, "Material added successfully");
        }
        return Result.fail("Failed to add material");
    }

    /**
     * 管理员更新材料信息。
     * 权限：admin
     * @param material 材料信息
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @PutMapping
    public Result<Material> updateMaterial(@RequestBody Material material, @RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only admins can update materials");
        }

        Material updatedMaterial = materialService.updateMaterial(material);
        if (updatedMaterial != null) {
            return Result.success(updatedMaterial, "Material updated successfully");
        }
        return Result.fail("Failed to update material or material not found");
    }

    /**
     * 管理员删除材料。
     * 权限：admin
     * @param materialId 材料ID
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @DeleteMapping("/{materialId}")
    public Result<String> deleteMaterial(@PathVariable Integer materialId, @RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only admins can delete materials");
        }

        boolean deleted = materialService.deleteMaterial(materialId);
        if (deleted) {
            return Result.success("Material deleted successfully");
        }
        return Result.fail("Failed to delete material or material not found");
    }

    /**
     * 维修人员记录材料消耗。
     * 权限：mechanic, admin
     * @param request 材料消耗请求 DTO
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @PostMapping("/consume")
    public Result<MaterialConsumption> recordMaterialConsumption(@RequestBody MaterialConsumptionRequest request, @RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"mechanic".equals(role) && !"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only mechanics or admins can record material consumption");
        }

        MaterialConsumption newConsumption = materialConsumptionService.recordMaterialConsumption(request);
        if (newConsumption != null) {
            // TODO: 在实际场景中，这里还需要根据消耗的材料和数量，更新对应工单的 total_material_cost
            // 还需要更新 material 表中的 stock_quantity (库存数量)
            return Result.success(newConsumption, "Material consumption recorded successfully");
        }
        return Result.fail("Failed to record material consumption");
    }

    /**
     * 根据工单ID查询材料消耗记录。
     * 权限：mechanic, admin
     * @param orderId 工单ID
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @GetMapping("/consume/byOrder/{orderId}")
    public Result<List<MaterialConsumption>> getMaterialConsumptionsByOrderId(@PathVariable Integer orderId, @RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"mechanic".equals(role) && !"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only mechanics or admins can view material consumption");
        }

        List<MaterialConsumption> consumptions = materialConsumptionService.getMaterialConsumptionsByOrderId(orderId);
        return Result.success(consumptions, "Material consumptions fetched successfully");
    }

    /**
     * 查询所有材料（用户和维修人员可查看库存，管理员可管理）。
     * 权限：user, mechanic, admin
     * @return 统一响应结果
     */
    @GetMapping("/all")
    public Result<List<Material>> getAllMaterials(@RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"user".equals(role) && !"mechanic".equals(role) && !"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Unauthorized access");
        }

        List<Material> materials = materialService.getAllMaterials();
        return Result.success(materials, "All materials fetched successfully");
    }

    /**
     * 根据材料ID查询材料信息（用户和维修人员可查看，管理员可管理）。
     * 权限：user, mechanic, admin
     * @param materialId 材料ID
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @GetMapping("/{materialId}")
    public Result<Material> getMaterialById(@PathVariable Integer materialId, @RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"user".equals(role) && !"mechanic".equals(role) && !"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Unauthorized access");
        }

        Material material = materialService.getMaterialById(materialId);
        if (material != null) {
            return Result.success(material, "Material fetched successfully");
        }
        return Result.fail("Material not found");
    }
}