package com.aking.moke.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/27
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @GetMapping("doLogin")
    public String doLogin(String username, String password) {
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.setLoginId(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    @GetMapping("isLogin")
    public String isLogin(String username, String password) {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

}
