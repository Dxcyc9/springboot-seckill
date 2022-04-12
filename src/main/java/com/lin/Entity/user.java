package com.lin.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class user implements Serializable {

    private String username;
    private String tel;
    private String idnumber;
    private String password;
    private String bankcard;

}
