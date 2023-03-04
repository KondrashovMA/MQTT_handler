package ru.pet.taskMQTT.domain.sensors.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pet.taskMQTT.domain.sensors.model.Sensor;
import ru.pet.taskMQTT.domain.sensors.repository.SensorsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SensorsService {
    private SensorsRepository sensorsRepository;

    public void save(Sensor sensor){
        sensorsRepository.save(sensor);
    }

    public List<Sensor> getAll(){
        return sensorsRepository.findAll();
    }
}