package com.moke.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/18
 */
@RestController
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() {
        return "testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "testB";
    }
}
