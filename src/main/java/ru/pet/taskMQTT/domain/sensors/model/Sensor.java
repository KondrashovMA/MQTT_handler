package ru.pet.taskMQTT.domain.sensors.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sensors")
@NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entry_id;

    @Column
    private String path;

    @Column
    private String value;

    @Column
    private String date;

    public Sensor(String path, String value, String timestamp) {
        this.path = path;
        this.value = value;
        this.date = timestamp;
    }
}