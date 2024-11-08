package com.example.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 기본 엔트포인트 제공, restful 처리
 */
@RequestMapping("/api/test")
@RestController
public class HomeConroller {
    @GetMapping("/home")
    public String home() {
        return "{ \"status\":\"ok\" }";
    }
}
