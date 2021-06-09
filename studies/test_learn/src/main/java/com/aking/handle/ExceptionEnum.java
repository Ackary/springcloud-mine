package com.aking.handle;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author ak
 */
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(-1, "未知错误"),

    /**
     * 空指针异常：NullPointerException
     */
    NULL_EXCEPTION(-2, "空指针异常：NullPointerException"),

    /**
     * "无效的数据访问资源使用异常：InvalidDataAccessResourceUsageException
     */
    INVALID_EXCEPTION(1146, "无效的数据访问资源使用异常：InvalidDataAccessResourceUsageException");

    public Integer code;
    public String msg;

}
