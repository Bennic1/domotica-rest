package nl.bennic.rest.domotica.repository;

import nl.bennic.rest.domotica.model.Scene;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SceneRepository extends MongoRepository<Scene, String> {

}
