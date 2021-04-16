package com.moke.springcloud.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.json.JSONObject;
import com.moke.springcloud.entities.CommonResult;
import com.moke.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/7
 */
@RestController
@Slf4j
public class OrderController {
    //public static final String PAYMENT_URL = "http://localhost:8001";cloud-payment-service
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    private ShearCaptcha captcha;

    @GetMapping("/consumer/payment/create")
    //@SuppressWarnings("rawtypes")
    public CommonResult<?> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    @SuppressWarnings("rawtypes")
    public CommonResult getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    //ribbon不用引入，因为在eureka中已经有了
    @GetMapping("/consumer/payment/getForEntity/{id}")
    @SuppressWarnings("rawtypes")
    public CommonResult<?> getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            log.info(entity.getStatusCode() + "\t" + entity.getHeaders());
            return entity.getBody();
        } else {
            return new CommonResult<>(400, "failed");
        }
    }

    @GetMapping(value = "/consumer/verifyCode")
    public void getFileCode() {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("e:/line.png");
        //输出code，验证图形验证码的有效性，返回boolean值
        log.info("code：" + lineCaptcha.getCode() + "，是否有效：" + lineCaptcha.verify("1234"));

        //重新生成验证码
        lineCaptcha.createCode();
        lineCaptcha.write("e:/line.png");
        //新的验证码，验证图形验证码的有效性，返回boolean值
        log.info("code：" + lineCaptcha.getCode() + "，是否有效：" + lineCaptcha.verify("1234"));
    }

    @GetMapping("/getCode")
    public void getStreamCode(HttpServletResponse response) {
        // 随机生成 4 位验证码
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        // 定义图片的显示大小
        captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        try {
            // 调用父类的 setGenerator() 方法，设置验证码的类型
            //captcha.setGenerator(randomGenerator);
            captcha.setGenerator(new MathGenerator());
            // 重新生成code
            captcha.createCode();
            log.info("验证：" + captcha.verify("verify"));
            // 输出到页面
            captcha.write(response.getOutputStream());
            // 打印日志
            log.info("生成的验证码:{}", captcha.getCode());
            // 关闭流
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/verify/{verify}")
    @ResponseBody
    public JSONObject verify(@PathVariable("verify") String verify) {
        JSONObject json = new JSONObject();
        if (captcha.verify(verify)) {
            json.put("Verify", "Correct");
        } else {
            json.put("Verify", "Wrong");
        }
        return json;
    }

    @GetMapping("/consumer/zipkin/")
    public String consumerZipkin() {
        return restTemplate.getForObject("http://localhost:8001" + "/payment/zipkin/", String.class);
    }
}
