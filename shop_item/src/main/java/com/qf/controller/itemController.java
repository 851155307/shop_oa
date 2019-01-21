package com.qf.controller;

import com.qf.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/item")
public class itemController {

    @Autowired
    private Configuration configuration;

    @RequestMapping("/createHtml")
    @ResponseBody
    public String createHtml(@RequestBody Goods goods){
        System.out.println("实打实的"+goods);
        //数据
        Map<String,Goods> map = new HashMap();
        map.put("goods",goods);

        String path = this.getClass().getResource("/static/page/").getPath()+goods.getId()+".html";
        //组合生成静态页
        System.out.println("静态页面的路径"+path);

        try(
                Writer writer = new FileWriter(path);
                ) {
            //生成模板
            Template template = configuration.getTemplate("goods.ftl");
            template.process(map,writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
