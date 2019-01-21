package com.qf.service;

import com.qf.Goods;

import java.util.List;

public interface IGoodsService {
    List<Goods> getAllGoods();
    Goods insertGoods(Goods goods);
}
