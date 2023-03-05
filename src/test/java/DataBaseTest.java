import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pet.taskMQTT.TaskMQTTapp;
import ru.pet.taskMQTT.domain.sensors.model.Sensor;
import ru.pet.taskMQTT.domain.sensors.service.SensorsService;

@SpringBootTest(classes = TaskMQTTapp.class)
public class DataBaseTest {
    @Autowired
    SensorsService sensorsService;

    @Test
    public void test(){
         var a = sensorsService.getAll();
        System.out.println(a.size());
        a.forEach(System.out::println);

//        Sensor sensor = new Sensor();
//        sensor.setPath("Path2");
//        sensor.setDate("SomeDate");
//        sensor.setValue(5.1);
//        sensorsService.save(sensor);
     }


}
