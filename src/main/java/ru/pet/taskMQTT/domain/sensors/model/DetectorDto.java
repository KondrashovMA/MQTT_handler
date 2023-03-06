package ru.pet.taskMQTT.domain.sensors.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetectorDto {
    private String detecorName;
    private Map<String, String> sensorsValues; // sensor name + sensor value
}
