package com.kj.kdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: Message
 * Description:
 * date: 2020/10/10 11:57
 *
 * @author Ksy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = -5693850238562156994L;

    private String text;
//    private String muserid;
//    private String ouserid;
    private String mysocketid;
    private String otsocketid;


}
