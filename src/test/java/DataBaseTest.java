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
    public void test(){
         var a = sensorsService.getAll();
        System.out.println(a.size());
        a.forEach(System.out::println);

        Sensor sensor1 = new Sensor("1", "2", new Timestamp(System.currentTimeMillis()));

//        Sensor sensor = new Sensor();
//        sensor.setPath("Path2");
//        sensor.setDate(new Timestamp(System.currentTimeMillis()));
//        sensor.setValue("value");
//        sensorsService.save(sensor);

        sensorsService.save(sensor1);
     }

     @Test
     public void count(){
        int amount = sensorsService.countSensorByPathAndValue("sensors/light", "500");
         System.out.println("amount = " + amount);
     }


}
