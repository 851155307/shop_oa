package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${fdfs.imgpath}")
    private String imgPath;
    @RequestMapping("/list")
    public String goodsManage(Model model){
        List<Goods> goods = goodsService.getAllGoods();
        System.out.println("商品："+goods);
        model.addAttribute("goods",goods);
        model.addAttribute("imgpath",imgPath);
        return "goodslist";
    }


    @RequestMapping("/uploadimg")
    @ResponseBody
    public String uploadimg(MultipartFile file) throws IOException {
        System.out.println("上传的文件名称："+file.getOriginalFilename());
        System.out.println("上传的文件名称大小："+file.getSize());

        StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                file.getInputStream(),
                file.getSize(),
                "JPG",
                null);
        System.out.println("上传到的full："+storePath.getFullPath());
        return "{\"imgpath\":\""+storePath.getFullPath()+"\"}";
    }

    @RequestMapping("/insert")
    public String insertGoods(Goods goods){
        goodsService.insertGoods(goods);
        return "redirect:/goods/list";
    }

}
