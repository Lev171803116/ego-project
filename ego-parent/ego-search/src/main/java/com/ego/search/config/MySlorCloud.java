package com.ego.search.config;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MySlorCloud {
    @Autowired
    private SolrClient solrClient;
    public CloudSolrClient ClientTest(){
        CloudSolrClient cloudSolrClient =(CloudSolrClient)solrClient;
        cloudSolrClient.setDefaultCollection("ego_core");
        return cloudSolrClient;
    }
}
