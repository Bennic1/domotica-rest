package nl.bennic.rest.domotica.repository;

import nl.bennic.rest.domotica.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {

    List<Device> findAllByName(String name);
}
