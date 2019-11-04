package org.mongounit.demo.dao.mongo;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mongounit.AssertMatchesDataset;
import org.mongounit.MongoUnitTest;
import org.mongounit.SeedWithDataset;
import org.mongounit.demo.dao.model.Address;
import org.mongounit.demo.dao.model.CreatePersonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MongoUnitTest(name = "personDaoService")
@SeedWithDataset
@DisplayName("MongoPersonDaoService with MongoUnit testing framework")
public class MongoPersonDaoServiceWithClassIT {

  @Autowired
  private MongoPersonDaoService mongoPersonDaoService;

  @Test
  @DisplayName("Create person on a non-empty database")
  @AssertMatchesDataset
  void createPersonWithExistingData() {

    CreatePersonRequest request = CreatePersonRequest.builder()
        .name("Robert")
        .address(Address.builder().street("13 Builder St.").zipcode(12345).build())
        .favColors(Arrays.asList("blue", "white"))
        .positionName("Builder")
        .build();

    mongoPersonDaoService.createPerson(request);
  }
}
