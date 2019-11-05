package org.mongounit.demo.dao.mongo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mongounit.AssertMatchesDataset;
import org.mongounit.LocationType;
import org.mongounit.MongoUnit;
import org.mongounit.MongoUnitTest;
import org.mongounit.SeedWithDataset;
import org.mongounit.demo.dao.model.Address;
import org.mongounit.demo.dao.model.CreatePersonRequest;
import org.mongounit.demo.dao.model.Person;
import org.mongounit.demo.dao.model.UpdatePersonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MongoUnitTest(name = "personDaoService")
@DisplayName("MongoPersonDaoService with MongoUnit testing framework")
public class MongoPersonDaoServiceIT {

  @Autowired
  private MongoPersonDaoService mongoPersonDaoService;

  @Test
  @DisplayName("Create person on an empty database")
  @AssertMatchesDataset
  void createPerson() {

    CreatePersonRequest request = CreatePersonRequest.builder()
        .name("Bob The Builder")
        .address(Address.builder().street("12 Builder St.").zipcode(12345).build())
        .favColors(Arrays.asList("red", "green"))
        .positionName("Builder")
        .build();

    mongoPersonDaoService.createPerson(request);
  }

  @Test
  @DisplayName("Create person on a non-empty database")
  @SeedWithDataset
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

  @Test
  @DisplayName("Create person on a non-empty database with explicit seed")
  @SeedWithDataset("onePersonToStart.json")
  @AssertMatchesDataset("expected2People.json")
  void createPersonWithExistingDataExplicitSeed() {

    CreatePersonRequest request = CreatePersonRequest.builder()
        .name("Robert")
        .address(Address.builder().street("13 Builder St.").zipcode(12345).build())
        .favColors(Arrays.asList("blue", "white"))
        .positionName("Builder")
        .build();

    mongoPersonDaoService.createPerson(request);
  }

  @Test
  @DisplayName("Create person on a non-empty database with classpath root datasets")
  @SeedWithDataset(
      value = "common/createPersonWithExistingData-seed.json",
      locationType = LocationType.CLASSPATH_ROOT)
  @AssertMatchesDataset(
      value = "common/createPersonWithExistingData-expected.json",
      locationType = LocationType.CLASSPATH_ROOT
  )
  void createPersonWithExistingDataWithPackageRelative() {

    CreatePersonRequest request = CreatePersonRequest.builder()
        .name("Robert")
        .address(Address.builder().street("13 Builder St.").zipcode(12345).build())
        .favColors(Arrays.asList("blue", "white"))
        .positionName("Builder")
        .build();

    mongoPersonDaoService.createPerson(request);
  }

  @Test
  @DisplayName("Update person")
  @SeedWithDataset("createPersonWithExistingData-seed.json")
  @AssertMatchesDataset
  void updatePerson() {

    UpdatePersonRequest updateRequest =
        new UpdatePersonRequest(
            "5db7545b7b615c739732c777",
            "Builder",
            "Robert",
            Arrays.asList("red", "green"),
            new Address("12 Builder St.", 12345));
    mongoPersonDaoService.updatePerson(updateRequest);

    // Any other API functions can be called here and asserted as usual
    Person updatedPerson = mongoPersonDaoService.getPerson("5db7545b7b615c739732c777");
    assertTrue(
        updatedPerson.getCreated().compareTo(updatedPerson.getUpdated()) < 0,
        "Updated data should be after created date");
  }

  @Test
  @DisplayName("Update person - Manual MongoUnit Testing")
  void updatePersonManualTest() {

    // Seed with data
    MongoUnit.seedWithDataset("createPersonWithExistingData-seed.json", this.getClass());

    // Perform API action
    UpdatePersonRequest updateRequest =
        new UpdatePersonRequest(
            "5db7545b7b615c739732c777",
            "Builder",
            "Robert",
            Arrays.asList("red", "green"),
            new Address("12 Builder St.", 12345));
    mongoPersonDaoService.updatePerson(updateRequest);

    // Any other API functions can be called here and asserted as usual
    Person updatedPerson = mongoPersonDaoService.getPerson("5db7545b7b615c739732c777");
    assertTrue(
        updatedPerson.getCreated().compareTo(updatedPerson.getUpdated()) < 0,
        "Updated data should be after created date");

    // Assert database is in the expected state
    MongoUnit.assertMatchesDataset("updatePerson-expected.json", this.getClass());
  }

  @Test
  @DisplayName("Delete person")
  @SeedWithDataset("createPersonWithExistingData-seed.json")
  @AssertMatchesDataset
  void deletePersonKeepCollections() {
    mongoPersonDaoService.deletePerson("5db7545b7b615c739732c777");
  }

  // Exercise: annotate this method to get the getPerson method
  //@Test
  @DisplayName("Get person")
  void testGetPerson() {

    // Step 1: Annotate method with seeded data
    // @SeedWithDataset("createPersonWithExistingData-seed.json")

    // Step 2: Retrieve the person by seeded ID, assert correct data
    // Person person = mongoPersonDaoService.getPerson("5db7545b7b615c739732c777");
    // assertEquals("Bob The Builder", person.getName());

    // Step 3: AssertThrows PersonNotFoundException.class when bad ID is supplied
    // assertThrows(PersonNotFoundException.class, () -> mongoPersonDaoService.getPerson("12345"));
  }
}



