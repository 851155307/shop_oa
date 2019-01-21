package com.qf.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.Goods;
import com.qf.dao.IGoodsDao;
import com.qf.service.IGoodsService;
import com.qf.service.ISeachService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private IGoodsDao goodsDao;

    @Reference
    private ISeachService seachService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<Goods> getAllGoods() {
        return goodsDao.selectList(null);
    }

    @Override
    @Transactional
    public Goods insertGoods(Goods goods) {
        System.out.println("添加的"+goods);
        goodsDao.insert(goods);
        seachService.addIndex(goods);
        rabbitTemplate.convertAndSend("goods_exchange","",goods);
        return goods;
    }
}
