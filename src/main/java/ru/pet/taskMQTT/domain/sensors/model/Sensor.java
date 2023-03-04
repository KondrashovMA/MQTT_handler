package ru.pet.taskMQTT.domain.sensors.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sensors")
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entry_id;

    @Column
    private String path;

    @Column
    private Double value;

    @Column
    private String date;
}
