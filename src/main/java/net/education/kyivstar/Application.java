package net.education.kyivstar;

import com.github.javafaker.Faker;
import net.education.kyivstar.courseParticipants.Student;

import java.util.Random;

import static net.education.kyivstar.Service.UserType.STUDENT;
import static net.education.kyivstar.Service.UserType.TEACHER;

public class Application {

    public static void main(String[] args) {
        var faker = new Faker();
        var random = new Random();

        Service service = new Service(faker, random);

        service.populateStorage(2);

        service.createUserAndStore(STUDENT, "Alex", "Burmistrenko", 30);
        service.createUserAndStore(TEACHER, "Maxim", "Dzhezhelo", 30);

        System.out.println("Get by name method: " + service.getByName("Alex"));

        System.out.println("Get by surname method: " + service.getBySurname("Dzhezhelo"));
        System.out.println("List "+ service.printStorage());
        System.out.println("Collect by age method: " + service.collectByAge(30));
       service.remove("Dzhezhelo");
        service.printStorage();

        service.update("Burmistrenko", new Student("Burito", "Mohito", 29));
        service.listsOfNames(service.collectByAge(30));

        service.printStorage();
    }
}
