package nl.bennic.rest.domotica.repository;

import nl.bennic.rest.domotica.model.Scene;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SceneRepository extends MongoRepository<Scene, String> {

}
