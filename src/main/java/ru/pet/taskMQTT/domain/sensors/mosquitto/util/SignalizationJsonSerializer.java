package ru.pet.taskMQTT.domain.sensors.mosquitto.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.pet.taskMQTT.domain.sensors.model.SignalizationDto;

@AllArgsConstructor
@Slf4j
@Component
public class SignalizationJsonSerializer {
    ObjectMapper objectMapper;

    public String serializeToJson(SignalizationDto signalizationDto){
        if(signalizationDto != null){
            try {
                return objectMapper.writeValueAsString(signalizationDto);
            } catch (JsonProcessingException e) {
                log.error("Unable to create Json cause: {}", e.getMessage());
                return null;
            }
        }
        return null;
    }
}
