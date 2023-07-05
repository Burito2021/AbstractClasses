package net.education.kyivstar;

import com.github.javafaker.Faker;
import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import net.education.kyivstar.courseParticipants.Student;
import net.education.kyivstar.courseParticipants.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.education.kyivstar.Service.UserType.values;

public class Service extends Storage {

    private final Faker faker;
    private final Storage store;
    private final Random random;

    public Service(Faker faker, Storage storage, Random random) {
        this.faker = faker;
        this.store = storage;
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
                    store.storage.add(new Teacher(randomName(), randomSurname(), randomAgeTeachersAndRevisers()));
                    break;
                case REVISER:
                    store.storage.add(new Reviser(randomName(), randomSurname(), randomAgeTeachersAndRevisers()));
                    break;
                case STUDENT:
                    store.storage.add(new Student(randomName(), randomSurname(), randomAge()));
                    break;
            }
        }
    }

    public void createUserAndStore(UserType userType, String name, String surname, int age) {

        switch (userType) {
            case TEACHER:
                store.storage.add(new Teacher(name, surname, age));
                break;
            case REVISER:
                store.storage.add(new Reviser(name, surname, age));
                break;
            case STUDENT:
                store.storage.add(new Student(name, surname, age));
                break;
        }
    }
//storage methods

    public void add(Human human) {

        store.storage.add(human);
    }

    public List<Human> collectByAge(int age) {

        List<Human> result = new ArrayList<>();
        for (Human human : store.storage) {
            if (human.getAge() == age) {
                result.add(human);
            }
        }
        return result;
    }

    public List<Human> getByName(String name) {

        List<Human> result = new ArrayList<>();
        for (Human human : store.storage) {
            if (human.getName().equals(name)) {
                result.add(human);
            }
        }
        return result;
    }

    public List<Human> getBySurname(String surname) {

        List<Human> result = new ArrayList<>();
        for (Human human : store.storage) {
            if (human.getSurname().equals(surname)) {
                result.add(human);
            }
        }
        return result;
    }

    public String printStorage() {

        System.out.println("What is in the storage printStorage method: " + store.storage);

        return "What is in the storage printStorage method: " + store.storage;
    }

    public List<String> listsOfNames(List<Human> list) {

        List<String> listOfNames = new ArrayList<>();

        for (Human item : list) {
            listOfNames.add(item.getName());
        }
        System.out.println("list of names by age: " + listOfNames);
        return listOfNames;
    }
    //

    public enum UserType {
        STUDENT,
        TEACHER,
        REVISER
    }
}
