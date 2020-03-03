package nl.bennic.rest.domotica.repository;

import nl.bennic.rest.domotica.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceRepository extends MongoRepository<Device, String> {

    Device findByName(String name);
    List<Device> findAllByName(String name);
}
