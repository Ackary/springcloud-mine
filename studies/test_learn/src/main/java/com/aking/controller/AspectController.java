package com.aking.controller;

import com.aking.annotation.HandleResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AspectController
 *
 * @author ak
 * @version 1.0
 * @date 2021/6/8
 */
@RequestMapping
@RestController
@Slf4j
public class AspectController {

    @GetMapping("/aspect")
    @HandleResult(desc = "AOP TEST")
    public void testRespect() {
        log.info("---AspectController---");
    }

}
