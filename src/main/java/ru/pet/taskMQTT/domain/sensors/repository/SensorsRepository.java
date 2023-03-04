package ru.pet.taskMQTT.domain.sensors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pet.taskMQTT.domain.sensors.model.Sensor;

public interface SensorsRepository extends JpaRepository<Sensor, Long> {
}
