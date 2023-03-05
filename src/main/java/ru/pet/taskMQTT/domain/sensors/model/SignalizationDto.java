package ru.pet.taskMQTT.domain.sensors.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignalizationDto {
    private String device;
    private String sensor;
    private Object value;
    private String untis;
    private int repeated;
}