package net.education.kyivstar.services.user;

import com.github.javafaker.Faker;
import net.education.kyivstar.repositories.HumanRepository;
import net.education.kyivstar.repositories.ReviserRepository;
import net.education.kyivstar.repositories.StudentRepository;
import net.education.kyivstar.repositories.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static net.education.kyivstar.services.user.UserType.values;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    final Faker faker;
    final Random random;

    private final HumanRepository humanRepository;
    private final ReviserRepository reviserRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public UserService(Faker faker, Random random, HumanRepository humanRepository, ReviserRepository reviserRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.faker = faker;
        this.random = random;
        this.humanRepository = humanRepository;
        this.reviserRepository = reviserRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public void createUserAndStore(UserType userType, String surname, String name, int age) {
        ValidationService.validateCreateAndStoreValidation(name, surname, age);

        switch (userType) {
            case TEACHER -> teacherRepository.addTeacher(surname, name, age);
            case REVISER -> reviserRepository.addReviser(surname, name, age);
            case STUDENT -> studentRepository.addStudent(surname, name, age);
            default -> throw new IllegalArgumentException();
        }
    }

    public void populateStorage(int numberOfPeople) {
        ValidationService.validateNumber(numberOfPeople);

        for (int x = 0; x < numberOfPeople; x++) {
            int randomNumber = random.nextInt(values().length);
            var randomHuman = UserType.values()[randomNumber];

            switch (randomHuman) {
                case TEACHER -> teacherRepository.addTeacher(randomName(), randomSurname(), randomAgeTeachersAndRevisers());
                case REVISER -> reviserRepository.addReviser(randomName(), randomSurname(), randomAgeTeachersAndRevisers());
                case STUDENT -> studentRepository.addStudent(randomName(), randomSurname(), randomAge());
                default -> throw new IllegalArgumentException("Unexpected value: " + randomHuman);
            }
        }
    }

    public List<Object> collectRevisers() {

        return reviserRepository.extractAllRevisers();
    }

    public List<Object> collectStudents() {

        return studentRepository.extractAllStudents();
    }

    public List<Object> collectTeachers() {

        return teacherRepository.extractAllTeachers();
    }

    public List<Object> collectAllUsers() {

        return humanRepository.extractAllUsers();
    }

    public List<Object> collectReviserBySurname(String surname) {

        return reviserRepository.extractRevisersBySurname(surname);
    }

    public List<Object> collectStudentsBySurname(String surname) {

        return studentRepository.extractStudentsBySurname(surname);
    }

    public List<Object> collectTeachersBySurname(String surname) {

        return teacherRepository.extractTeacherBySurname(surname);
    }

    public HashMap<String, Integer> countUsersByCategory() {

        return humanRepository.countUsersByCategory();
    }

    public HashMap<String, Integer> countAllUsers() {

        return humanRepository.countUsersByCategory();
    }

    public List<Object> collectBySurname(String surname) {

        ValidationService.validateName(surname);
        return humanRepository.extractBySurname(surname);
    }

    public void replaceUser(String surname, UserType type, String name, String surnameToBePlaced, int age) {
        ValidationService.validateReplaceUser(surname, name, surnameToBePlaced, age);

        switch (type) {
            case TEACHER -> teacherRepository.updateTeacherBySurname(surname, surnameToBePlaced, name, age);
            case STUDENT -> studentRepository.updateStudentBySurname(surname, surnameToBePlaced, name, age);
            case REVISER -> reviserRepository.updateReviserBySurname(surname, surnameToBePlaced, name, age);
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }

    public void removeUser(String surname, String name) {
        ValidationService.validateName(surname);
        ValidationService.validateName(name);

        humanRepository.removeUserBySurname(surname, name);
    }

    public void removeAll() {
        humanRepository.removeAll();
    }

    public String printStorageAllUsers() {
        final var text = "What is in the storage printStorage method: ";
        logger.info(text + collectAllUsers());

        return text + collectAllUsers();
    }

    public String printReviserStorage() {
        final var text = "What is in the Reviser storage printStorage method: ";
        logger.info(text + reviserRepository.extractAllRevisers());

        return text + reviserRepository.extractAllRevisers();
    }

    public String printStudentStorage() {
        final var text = "What is in the Student storage printStorage method: ";
        logger.info(text + studentRepository.extractAllStudents());

        return text + studentRepository.extractAllStudents();
    }

    public String printTeacherStorage() {
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
