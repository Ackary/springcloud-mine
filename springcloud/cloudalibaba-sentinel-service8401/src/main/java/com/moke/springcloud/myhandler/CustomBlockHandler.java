package com.moke.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.moke.springcloud.entities.CommonResult;

/**
 * 官方规定外部类做异常处理需要static
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/22
 */
public class CustomBlockHandler {
    public static CommonResult<?> handlerException(BlockException exception) {
        return new CommonResult<>(4444, "按自定义，global handlerException----1");
    }

    public static CommonResult<?> handlerException2(BlockException exception) {
        return new CommonResult<>(4444, "按自定义，global handlerException----2");
    }
}
