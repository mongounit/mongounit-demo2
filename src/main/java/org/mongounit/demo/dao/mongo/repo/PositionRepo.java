package org.mongounit.demo.dao.mongo.repo;

import org.mongounit.demo.dao.model.Position;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PositionRepo extends MongoRepository<Position, String> {

  Position findByPositionName(String positionName);

}
