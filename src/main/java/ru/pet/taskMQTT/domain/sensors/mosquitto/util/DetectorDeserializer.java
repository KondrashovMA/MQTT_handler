package ru.pet.taskMQTT.domain.sensors.mosquitto.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.pet.taskMQTT.domain.sensors.model.DetectorDto;

@AllArgsConstructor
@Component
@Slf4j
public class DetectorDeserializer {
    private final ObjectMapper objectMapper;

    public DetectorDto deserialize(String data){
        if (data == null){
            return null;
        }
        try {
            return objectMapper.readValue(data, DetectorDto.class);
        } catch (JsonMappingException e){
            log.error("Unable while parsing Json cause by mapping exception: {}", e.getMessage());
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            log.error("Unable while parsing Json cause by processing exception: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
