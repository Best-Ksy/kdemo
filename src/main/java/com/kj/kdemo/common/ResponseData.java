package com.kj.kdemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: ResponseData
 * Description: response返回结果
 * date: 2020/10/10 9:41
 *
 * @author Ksy
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = -1991096682171397080L;

    private int code;
    private String message;
    private T data;


}
