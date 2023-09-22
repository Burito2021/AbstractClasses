package net.education.kyivstar;

import com.github.javafaker.Faker;
import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import net.education.kyivstar.courseParticipants.Student;
import net.education.kyivstar.courseParticipants.Teacher;

import java.util.Random;

import static net.education.kyivstar.Service.UserType.*;

public class Service {

    Faker faker = new Faker();
    Storage storage = new Storage();
    Random random = new Random();

    private String randomName() {
        return faker.name().firstName();
    }

    private String randomSurname() {
        return faker.name().lastName();
    }

    private int randomAge() {
        return faker.number().numberBetween(18, 65);
    }

    private int randomAgeTeachersAndRevisers() {
        return faker.number().numberBetween(25, 65);
    }

    public void populateStorage(int numberOfPeople) {
        for (int x = 0; x < numberOfPeople; x++) {
            int randomNumber = random.nextInt(values().length);
            UserType randomHuman = UserType.values()[randomNumber];

            switch (randomHuman) {
                case TEACHER:
                    storage.add(new Teacher(randomName(), randomSurname(), randomAgeTeachersAndRevisers()));
                    break;
                case REVISER:
                    storage.add(new Reviser(randomName(), randomSurname(), randomAgeTeachersAndRevisers()));
                    break;
                case STUDENT:
                    storage.add(new Student(randomName(), randomSurname(), randomAge()));
                    break;
            }
        }
    }

    public void createUserAndStore(UserType userType,String name, String surname, int age) {

        switch (userType) {
            case TEACHER:
                storage.add(new Teacher(name, surname, age));
                break;
            case REVISER:
                storage.add(new Reviser(name, surname, age));
                break;
            case STUDENT:
                storage.add(new Student(name, surname, age));
                break;
        }
    }

    public enum UserType {
        STUDENT,
        TEACHER,
        REVISER
    }
}
