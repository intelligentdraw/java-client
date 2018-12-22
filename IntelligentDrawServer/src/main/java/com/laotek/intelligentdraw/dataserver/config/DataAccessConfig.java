package com.laotek.intelligentdraw.dataserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;

@Configuration
public class DataAccessConfig extends AbstractMongoConfiguration {

    @Override
    public MongoClient mongoClient() {
	return new MongoClient("127.0.0.1", 27017);
    }

    @Override
    protected String getDatabaseName() {
	return "intelligent_draw";
    }

    @Bean
    public CascadeSaveMongoEventListener cascadeSaveMongoEventListener() {
	return new CascadeSaveMongoEventListener();
    }
}
