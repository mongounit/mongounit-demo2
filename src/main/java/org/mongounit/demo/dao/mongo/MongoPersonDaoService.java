package org.mongounit.demo.dao.mongo;

import java.util.Date;
import java.util.List;
import org.mongounit.demo.dao.PersonDaoService;
import org.mongounit.demo.dao.exception.PersonNotFoundException;
import org.mongounit.demo.dao.model.CreatePersonRequest;
import org.mongounit.demo.dao.model.Person;
import org.mongounit.demo.dao.model.Position;
import org.mongounit.demo.dao.model.UpdatePersonRequest;
import org.mongounit.demo.dao.mongo.repo.PersonRepo;
import org.mongounit.demo.dao.mongo.repo.PositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DAO service for {@link Person} entity.
 */
@Service
public class MongoPersonDaoService implements PersonDaoService {

  private final PersonRepo personRepo;

  private final PositionRepo positionRepo;

  @Autowired
  MongoPersonDaoService(final PersonRepo personRepo, final PositionRepo positionRepo) {
    this.personRepo = personRepo;
    this.positionRepo = positionRepo;
  }

  @SuppressWarnings("DuplicatedCode")
  @Override
  public Person createPerson(CreatePersonRequest createPersonRequest) {

    // Find position with the same name
    Position existingPosition =
        positionRepo.findByPositionName(createPersonRequest.getPositionName());

    Date now = new Date();

    // Create new position if doesn't exist already
    if (existingPosition == null) {

      Position newPosition = Position.builder()
          .positionName(createPersonRequest.getPositionName())
          .created(now)
          .updated(now)
          .build();
      existingPosition = positionRepo.save(newPosition);
    }

    // Create Person
    Person person = Person.builder()
        .name(createPersonRequest.getName())
        .positionId(existingPosition.getId())
        .favColors(createPersonRequest.getFavColors())
        .address(createPersonRequest.getAddress())
        .created(now)
        .updated(now)
        .build();

    return personRepo.save(person);
  }

  @Override
  public Person getPerson(String id) throws PersonNotFoundException {
    return personRepo.findById(id).orElseThrow(PersonNotFoundException::new);
  }

  @SuppressWarnings("DuplicatedCode")
  @Override
  public Person updatePerson(UpdatePersonRequest updatePersonRequest) {

    // Find position with the same name
    Position existingPosition =
        positionRepo.findByPositionName(updatePersonRequest.getPositionName());

    Date now = new Date();

    // Create new position if doesn't exist already
    if (existingPosition == null) {

      Position newPosition = Position.builder()
          .positionName(updatePersonRequest.getPositionName())
          .created(now)
          .updated(now)
          .build();
      existingPosition = positionRepo.save(newPosition);
    }

    // Get existing person
    Person existingPerson = getPerson(updatePersonRequest.getId());
    String oldPositionNameId = existingPerson.getPositionId();

    // Update existing person
    existingPerson.setName(updatePersonRequest.getName());
    existingPerson.setPositionId(existingPosition.getId());
    existingPerson.setFavColors(updatePersonRequest.getFavColors());
    existingPerson.setAddress(updatePersonRequest.getAddress());
    existingPerson.setUpdated(now); // FORGOT THAT BEFORE!!

    Person updatedPerson = personRepo.save(existingPerson);

    // Delete position if it's no longer being used
    List<Person> personsWithOldPosition = personRepo.findByPositionId(oldPositionNameId);
    if (personsWithOldPosition.size() == 0) {
      positionRepo.deleteById(oldPositionNameId);
    }

    return updatedPerson;
  }

  @Override
  public void deletePerson(String id) {

    // Find person to delete
    Person personToDelete = getPerson(id);
    String oldPositionNameId = personToDelete.getPositionId();

    // Delete person
    personRepo.deleteById(id);

    // Delete position if it's no longer being used
    List<Person> personsWithOldPosition = personRepo.findByPositionId(oldPositionNameId);
    if (personsWithOldPosition.size() == 0) {
      positionRepo.deleteById(oldPositionNameId);
    }
  }

  @Override
  public void renamePerson(String newName, String id) {

    // Get existing person
    Person existingPerson = getPerson(id);

    // Update name
    existingPerson.setName(newName);
    personRepo.save(existingPerson);
  }
}
