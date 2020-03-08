package nl.bennic.rest.domotica.repository;

import nl.bennic.rest.domotica.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {

}
