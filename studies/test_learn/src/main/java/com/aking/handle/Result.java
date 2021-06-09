package com.aking.handle;

import lombok.Data;

/**
 * @author ak
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

}
