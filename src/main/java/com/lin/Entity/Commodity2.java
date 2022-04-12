package com.lin.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Commodity2 {

    private Integer id;
    private String name;
    private String description;
    private String interest;
    private String lastingtime;
    private String starttime;
    private Integer num;
}
