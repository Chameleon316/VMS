package org.lw.vms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        return request.getHeader("Authorization") != null ?
            "Hello, authenticated user!" :
            "Hello, guest! Please log in." +
            "\nNote: This endpoint is for testing purposes only." +
            "\nPlease use the /api/user/register and /api/user/login endpoints for user registration and login." +
            "\nThe Authorization header is required for authenticated requests." +
            "\nExample: Authorization: Bearer <your_jwt_token>";
    }
}
