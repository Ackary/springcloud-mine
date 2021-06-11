package com.aking.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * EsInsertDoc
 *
 * @author ak
 * @version 1.0
 * @date 2021/6/10
 */
public class EsInsertDoc {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 插入数据
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("1001");

        Users users = new Users();
        users.setAge(20);
        users.setId(1);
        users.setName("jeff");
        
        // 插入数据必须为JSON格式
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(users);

        // 关闭ES客户端
        esClient.close();
    }
}
