package com.aking.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * EsClient
 *
 * @author ak
 * @version 1.0
 * @date 2021/6/9
 */
public class EsClient {

    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 关闭ES客户端
        esClient.close();
    }

}
