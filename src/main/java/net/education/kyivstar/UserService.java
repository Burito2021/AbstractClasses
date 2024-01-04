package net.education.kyivstar;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static net.education.kyivstar.UserType.values;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    final Faker faker = new Faker();
    final Random random = new Random();

    private HumanRepository humanRepository = new HumanRepository();
    private ReviserRepository reviserRepository = new ReviserRepository();
    private StudentRepository studentRepository = new StudentRepository();
    private TeacherRepository teacherRepository = new TeacherRepository();

    public UserService() {
    }


    public void createUserAndStore(UserType userType, String surname, String name, int age) throws SQLException {
        ValidationService.validateCreateAndStoreValidation(name, surname, age);

        switch (userType) {
            case TEACHER -> teacherRepository.addTeacher(surname, name, age);
            case REVISER -> reviserRepository.addReviser(surname, name, age);
            case STUDENT -> studentRepository.addStudent(surname, name, age);
            default -> throw new IllegalArgumentException();
        }
    }

    public void populateStorage(int numberOfPeople) throws SQLException {
        ValidationService.validateNumber(numberOfPeople);

        for (int x = 0; x < numberOfPeople; x++) {
            int randomNumber = random.nextInt(values().length);
            UserType randomHuman = UserType.values()[randomNumber];

            switch (randomHuman) {
                case TEACHER -> teacherRepository.addTeacher(randomName(), randomSurname(), randomAgeTeachersAndRevisers());
                case REVISER -> reviserRepository.addReviser(randomName(), randomSurname(), randomAgeTeachersAndRevisers());
                case STUDENT -> studentRepository.addStudent(randomName(), randomSurname(), randomAge());
                default -> throw new IllegalArgumentException("Unexpected value: " + randomHuman);
            }
        }
    }

    public List<Object> collectRevisers() throws SQLException {
        return reviserRepository
                .extractAllRevisers();
    }

    public List<Object> collectStudents() throws SQLException {
        return studentRepository
                .extractAllStudents();
    }

    public List<Object> collectTeachers() throws SQLException {
        return teacherRepository
                .extractAllTeachers();
    }

    public List<Object> collectAllUsers() {
        List<Object> list = null;
        try {
            list = humanRepository.extractAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> collectReviserBySurname(String surname) {
        List<Object> list = null;
        try {
            list = reviserRepository.extractRevisersBySurname(surname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> collectStudentsBySurname(String surname) {
        List<Object> list = null;
        try {
            list = studentRepository.extractStudentsBySurname(surname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> collectTeachersBySurname(String surname) {
        List<Object> list = null;
        try {
            list = teacherRepository.extractTeacherBySurname(surname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public HashMap<String, Integer> countUsersByCategory() {
        HashMap<String, Integer> list = null;
        try {
            list = humanRepository.countUsersByCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public HashMap<String, Integer> countAllUsers() {
        HashMap<String, Integer> list = null;
        try {
            list = humanRepository.countUsersByCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Object> collectBySurname(String surname) throws SQLException {
        ValidationService.validateName(surname);
        return humanRepository.extractBySurname(surname);
    }

    public void replaceUser(String surname, UserType type, String name, String surnameToBePlaced, int age) throws SQLException {
        ValidationService.validateReplaceUser(surname, name, surnameToBePlaced, age);

        switch (type) {
            case TEACHER -> teacherRepository.updateTeacherBySurname(surname, surnameToBePlaced, name, age);
            case STUDENT -> studentRepository.updateStudentBySurname(surname, surnameToBePlaced, name, age);
            case REVISER -> reviserRepository.updateReviserBySurname(surname, surnameToBePlaced, name, age);
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }

    public void removeUser(String surname, String name) throws SQLException {
        ValidationService.validateName(surname);
        ValidationService.validateName(name);

        humanRepository.removeUserBySurname(surname, name);
    }

    public void removeAll() throws SQLException {
        humanRepository.removeAll();
    }

    public String printStorageAllUsers() {
        final var text = "What is in the storage printStorage method: ";
        logger.info(text + collectAllUsers());

        return text + collectAllUsers();
    }

    public String printReviserStorage() throws SQLException {
        final var text = "What is in the Reviser storage printStorage method: ";
        logger.info(text + reviserRepository.extractAllRevisers());

        return text + reviserRepository.extractAllRevisers();
    }

    public String printStudentStorage() throws SQLException {
        final var text = "What is in the Student storage printStorage method: ";
        logger.info(text + studentRepository.extractAllStudents());

        return text + studentRepository.extractAllStudents();
    }

    public String printTeacherStorage() throws SQLException {
        final var text = "What is in the Teacher storage printStorage method: ";
        logger.info(text + teacherRepository.extractAllTeachers());

        return text + teacherRepository.extractAllTeachers();
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
}
