package org.mongounit.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.mongounit.demo.dao.mongo.repo")
public class MongoUnitDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(MongoUnitDemoApplication.class, args);
  }
}
