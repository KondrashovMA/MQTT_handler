import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pet.taskMQTT.TaskMQTTapp;
import ru.pet.taskMQTT.domain.sensors.model.Sensor;
import ru.pet.taskMQTT.domain.sensors.service.SensorsService;

import java.sql.Timestamp;

@SpringBootTest(classes = TaskMQTTapp.class)
public class DataBaseTest {
    @Autowired
    SensorsService sensorsService;

    @Test
    public void saveTest(){
//        Sensor sensor = new Sensor("1", "2", new Timestamp(System.currentTimeMillis()));
        Sensor sensor = new Sensor("1", "2", new Timestamp(System.currentTimeMillis()).toString());
        sensorsService.save(sensor);
     }

     @Test
     public void countTest(){
        int amount = sensorsService.countSensorByPathAndValue("sensors/light", "500");
         System.out.println("amount = " + amount);
     }
}