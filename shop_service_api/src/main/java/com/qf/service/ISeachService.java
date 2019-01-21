package com.qf.service;

import com.qf.Goods;

import java.util.List;

public interface ISeachService {
    List<Goods> queryGoodsByIndex(String keyword);

    int addIndex(Goods goods);
}
