package com.qf.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.Goods;
import com.qf.service.ISeachService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class SeachServiceImpl implements ISeachService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Goods> queryGoodsByIndex(String keyword) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setStart(1);
        solrQuery.setRows(3);
        if(keyword==null){
            solrQuery.setQuery("*:*");
        }else{
            solrQuery.setQuery("gtitle:"+keyword+"|| ginfo:"+keyword);
        }
        List<Goods> goodsList = new ArrayList<>();

        try {
            QueryResponse query = solrClient.query(solrQuery);
            SolrDocumentList results = query.getResults();
            //总数
            long totalCount = results.getNumFound();
            for(SolrDocument sd:results){
                String id = (String) sd.get("id");
                int uid = Integer.parseInt(id);
                String gtitle = (String) sd.get("gtitle");
                String ginfo = (String) sd.get("ginfo");
                float gprice = (float) sd.get("gprice");
                int gcount = (int) sd.get("gcount");
                String gimage = (String) sd.get("gimage");
                Goods goods = new Goods(uid,gtitle,ginfo,gcount,0,0,gprice,gimage);
                System.out.println("商品："+goods);
                goodsList.add(goods);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return goodsList;
    }

    @Override
    public int addIndex(Goods goods) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",goods.getId());
        document.setField("gtitle",goods.getTitle());
        document.setField("gprice",goods.getPrice());
        document.setField("gimage",goods.getGimage());
        document.setField("gcount",goods.getGcount());
        document.setField("ginfo",goods.getGinfo());
        try {
            solrClient.add(document);
            solrClient.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
