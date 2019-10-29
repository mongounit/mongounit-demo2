package org.mongounit.demo.dao;

import org.mongounit.demo.dao.exception.PersonNotFoundException;
import org.mongounit.demo.dao.model.CreatePersonRequest;
import org.mongounit.demo.dao.model.Person;
import org.mongounit.demo.dao.model.UpdatePersonRequest;

@SuppressWarnings("UnusedReturnValue")
public interface PersonDaoService {

  Person createPerson(CreatePersonRequest createPersonRequest);

  Person getPerson(String id) throws PersonNotFoundException;

  Person updatePerson(UpdatePersonRequest updatePersonRequest);

  void deletePerson(String id);

  void renamePerson(String newName, String id);
}
