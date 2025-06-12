package org.lw.vms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
@RequestMapping("/api/test")
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
