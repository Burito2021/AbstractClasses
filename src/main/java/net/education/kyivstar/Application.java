package net.education.kyivstar;

import com.github.javafaker.Faker;
import net.education.kyivstar.courseParticipants.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static net.education.kyivstar.Service.UserType.STUDENT;
import static net.education.kyivstar.Service.UserType.TEACHER;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        Service service = new Service(new Faker(), new Random() ,new Repository(new Storage()));

        service.populateStorage(2);

        service.createUserAndStore(STUDENT, "Alex", "Burmistrenko", 30);
        service.createUserAndStore(TEACHER, "Maxim", "Dzhezhelo", 30);

        logger.info("Get by name method: " + service.getByName("Alex"));

        logger.info("Get by surname method: " + service.getBySurname("Dzhezhelo"));
        logger.info("List " + service.printStorage());
        logger.info("Collect by age method: " + service.collectByAge(30));
        service.remove("Dzhezhelo");
        service.printStorage();

        logger.info("Burmistrenko", new Student("Burito", "Mohito", 29));
        service.listsOfNames(service.collectByAge(30));

        service.printStorage();
    }
}
