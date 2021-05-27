package com.aking.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/5/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class User implements Serializable {

    private String name;

    private int age;
}
