package net.education.kyivstar;

import com.github.javafaker.Faker;
import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import net.education.kyivstar.courseParticipants.Student;
import net.education.kyivstar.courseParticipants.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.education.kyivstar.Service.UserType.values;

public class Service {
 private Logger logger = LoggerFactory.getLogger(Service.class);

    private final Faker faker;
    private final Random random;
    Repository repository = new Repository();

    public Service(Faker faker, Random random) {
        this.faker = faker;
        this.random = random;
    }

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
                    repository.add(new Teacher(randomName(), randomSurname(), randomAgeTeachersAndRevisers()));
                    break;
                case REVISER:
                    repository.add(new Reviser(randomName(), randomSurname(), randomAgeTeachersAndRevisers()));
                    break;
                case STUDENT:
                    repository.add(new Student(randomName(), randomSurname(), randomAge()));
                    break;
            }
        }
    }

    public void createUserAndStore(UserType userType, String name, String surname, int age) {

        switch (userType) {
            case TEACHER -> repository.add(new Teacher(name, surname, age));
            case REVISER -> repository.add(new Reviser(name, surname, age));
            case STUDENT -> repository.add(new Student(name, surname, age));
            default -> throw new IllegalArgumentException();
        }
    }

    public List<Human> collectByAge(int age) {

        List<Human> result = new ArrayList<>();
        for (Human human : repository.getAll()) {
            if (human.getAge() == age) {
                result.add(human);
            }
        }
        logger.info("Result of collectByAge "+result);
        return result;
    }

    public List<Human> getByName(String name) {

        List<Human> result = new ArrayList<>();
        for (Human human : repository.getAll()) {
            if (human.getName().equals(name)) {
                result.add(human);
            }
        }
        logger.info("Result of getByName "+result);
        return result;
    }

    public void update(String surname, Human humanToReplace){
        repository.updateBySurname(surname, humanToReplace);
    }

    public List<Human> getBySurname(String surname) {

        List<Human> result = new ArrayList<>();
        for (Human human : repository.getAll()) {
            if (human.getSurname().equals(surname)) {
                result.add(human);
            }
        }
        logger.info("Result of getBySurname "+result);
        return result;
    }

    public String printStorage() {

        System.out.println("What is in the storage printStorage method: " + repository.getAll());

        return "What is in the storage printStorage method: " + repository.getAll();
    }

    public List<String> listsOfNames(List<Human> list) {

        List<String> listOfNames = new ArrayList<>();

        for (Human item : list) {
            listOfNames.add(item.getName());
        }
        logger.info("Result of listsOfNames "+listOfNames);
        return listOfNames;
    }

    public void remove(String surname){
        repository.removeBySurname(surname);
    }

    public enum UserType {
        STUDENT,
        TEACHER,
        REVISER
    }
}
