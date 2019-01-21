package com.qf.listener;

import com.qf.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener(queues = "goods_queue")
public class MyRabbitListenner {
    @Autowired
    private Configuration configuration;

    @RabbitHandler
    public void hangdlerMsg(Goods goods){
        System.out.println("收到的"+goods);

        Map<String,Goods> map = new HashMap<>();
        map.put("goods",goods);

        String path = this.getClass().getResource("/static/page/").getPath() + goods.getId() + ".html";
        System.out.println("静态页面的位置"+path);

        try(
                Writer out = new FileWriter(path);
        ) {
            //准备商品详情的模板
            Template template = configuration.getTemplate("goods.ftl");
            //生成静态页
            template.process(map, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
