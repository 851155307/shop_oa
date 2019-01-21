package com.qf;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private String title;
    private String ginfo;
    private int gcount;
    private int tid;
    private double allprice;
    private double price;
    private String gimage;
}
