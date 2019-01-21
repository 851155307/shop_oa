package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Goods;
import com.qf.service.ISeachService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/seach")
public class SeachController {

    @Reference
    private ISeachService seachService;

    @RequestMapping("/query")
    public String seach(String  keyword, Model model){
        List<Goods> goodsList = seachService.queryGoodsByIndex(keyword);
        System.out.println("查询结果："+goodsList);
        model.addAttribute("goodsList",goodsList);
        return "seachlist";
    }
}
