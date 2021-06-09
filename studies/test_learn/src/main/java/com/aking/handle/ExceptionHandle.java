package com.aking.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Component;

/**
 * ExceptionHandle
 *
 * @author ak
 * @version 1.0
 * @date 2021/6/8
 */
@Slf4j
@Component
public class ExceptionHandle {

    public Result exceptionGet(Throwable t) {
        log.error("异常信息：", t);
        if (t instanceof InvalidDataAccessResourceUsageException) {
            return ResultUtil.error(ExceptionEnum.INVALID_EXCEPTION);
        } else if (t instanceof NullPointerException) {
            return ResultUtil.error(ExceptionEnum.NULL_EXCEPTION);
        }
        return ResultUtil.error(ExceptionEnum.UNKNOWN_ERROR);
    }

}
