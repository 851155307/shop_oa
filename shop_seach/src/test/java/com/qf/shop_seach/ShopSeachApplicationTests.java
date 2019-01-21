package com.qf.shop_seach;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSeachApplicationTests {

    @Autowired
    private SolrClient solrClient;


    //添加索引
    @Test
    public void contextLoads(){
//        for(int i = 0; i < 10; i++) {
//            //创建一个document对象 -> 一个商品
//            SolrInputDocument document = new SolrInputDocument();
//            //设置字段
//            document.setField("id", i + 1);
//
//            if(i == 5){
//                document.setField("gtitle", "小霸王学习机霸王霸王霸王霸王霸王霸王霸王霸王" + i);
//            } else {
//                document.setField("gtitle", "小霸王学习机" + i);
//            }
//            document.setField("ginfo", "学电脑更容易");
//            document.setField("gprice", 199.9 + i);
//            document.setField("gimage", "http://www.baidu.com");
//            document.setField("gcount", 100 + i);
//
//            //将该商品放入索引库
//            try {
//                solrClient.add(document);
//            } catch (SolrServerException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }

        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", 100);
        document.setField("gtitle", "小米笔记本");
        document.setField("ginfo", "学电脑更容易");
        document.setField("gprice", 199.9 );
        document.setField("gimage", "http://www.baidu.com");
        document.setField("gcount", 100);

        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除索引
    @Test
    public void delete() throws IOException, SolrServerException {
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }


    @Test
    public void update() throws IOException, SolrServerException {
        System.out.println("222");
    }
}

