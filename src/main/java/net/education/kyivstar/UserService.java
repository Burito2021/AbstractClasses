package net.education.kyivstar;

import com.github.javafaker.Faker;
import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import net.education.kyivstar.courseParticipants.Student;
import net.education.kyivstar.courseParticipants.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.education.kyivstar.UserType.values;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;
    private ReviserRepository reviserRepository;
    private final Faker faker;
    private final Random random;

    public UserService(Faker faker, Random random, TeacherRepository repository) {
        this.teacherRepository = repository;
        this.faker = faker;
        this.random = random;
    }

    public UserService(Faker faker, Random random, StudentRepository repository) {
        this.studentRepository = repository;
        this.faker = faker;
        this.random = random;
    }

    public UserService(Faker faker, Random random, ReviserRepository repository) {
        this.reviserRepository = repository;
        this.faker = faker;
        this.random = random;
    }

    public UserService(Faker faker, Random random, ReviserRepository repository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.reviserRepository = repository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.faker = faker;
        this.random = random;
    }

    public void createUserAndStore(UserType userType, String name, String surname, int age) {
        ValidationService.validateCreateAndStoreValidation(name,surname,age);

        switch (userType) {
            case TEACHER -> teacherRepository.addTeacher(new Teacher(name, surname, age));
            case REVISER -> reviserRepository.addReviser(new Reviser(name, surname, age));
            case STUDENT -> studentRepository.addStudent(new Student(name, surname, age));
            default -> throw new IllegalArgumentException();
        }
    }

    public void populateStorage(int numberOfPeople) {
        ValidationService.validateNumber(numberOfPeople);

        for (int x = 0; x < numberOfPeople; x++) {
            int randomNumber = random.nextInt(values().length);
            UserType randomHuman = UserType.values()[randomNumber];

            switch (randomHuman) {
                case TEACHER -> teacherRepository.addTeacher(new Teacher(randomName(), randomSurname(), randomAgeTeachersAndRevisers()));
                case REVISER -> reviserRepository.addReviser(new Reviser(randomName(), randomSurname(), randomAgeTeachersAndRevisers()));
                case STUDENT -> studentRepository.addStudent(new Student(randomName(), randomSurname(), randomAge()));
                default -> throw new IllegalArgumentException("Unexpected value: " + randomHuman);
            }
        }
    }

    public List<Reviser> collectRevisers() {
        return reviserRepository
                .extractAllRevisers();
    }

    public List<Student> collectStudents() {
        return studentRepository
                .extractAllStudents();
    }

    public List<Teacher> collectTeachers() {
        return teacherRepository
                .extractAllTeachers();
    }


    public List<Human> collectAllUsers() {
        final var revisers = reviserRepository.extractAllRevisersStream().map(student -> (Human) student);
        final var students = studentRepository.extractAllStudentsStream().map(student -> (Human) student);
        final var teachers = teacherRepository.extractAllTeachersStream().map(student -> (Human) student);
        final var merged = Stream.concat(Stream.concat(revisers, students), teachers).collect(Collectors.toList());
        return merged;
    }

    public List<Human> collectBySurname(String surname) {
        ValidationService.validateName(surname);
        final var revisers = reviserRepository.extractAllRevisersStream().map(student -> (Human) student);
        final var students = studentRepository.extractAllStudentsStream().map(student -> (Human) student);
        final var teachers = teacherRepository.extractAllTeachersStream().map(student -> (Human) student);
        final var merged = Stream.concat(Stream.concat(revisers, students), teachers);

        return merged.filter(s->s.getSurname().equals(surname)).collect(Collectors.toList());
    }

    public void replaceUser(String surname, UserType type, String name, String surnameToBePlaced, int age) {
        ValidationService.validateReplaceUser(surname,name,surnameToBePlaced,age);

        switch (type) {
            case TEACHER -> teacherRepository.updateTeacherBySurname(surname, new Teacher(name, surnameToBePlaced, age));
            case STUDENT -> studentRepository.updateStudentBySurname(surname, new Student(name, surnameToBePlaced, age));
            case REVISER -> reviserRepository.updateReviserBySurname(surname, new Reviser(name, surnameToBePlaced, age));
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }

    public void removeUser(String surname) {
        ValidationService.validateName(surname);
        reviserRepository.removeReviserBySurname(surname);
    }

    public void removeAll() {
        reviserRepository.removeAll();
    }

    public String printStorageAllUsers() {

        logger.info("What is in the storage printStorage method: " + collectAllUsers());

        return "What is in the storage printStorage method: " + collectAllUsers();
    }

    public String printReviserStorage() {

        logger.info("What is in the storage printStorage method: " + reviserRepository.extractAllRevisers());

        return "What is in the storage printStorage method: " + reviserRepository.extractAllRevisers();
    }

    public String printStudentStorage() {

        logger.info("What is in the storage printStorage method: " + studentRepository.extractAllStudents());

        return "What is in the storage printStorage method: " + studentRepository.extractAllStudents();
    }

    public String printTeacherStorage() {

        logger.info("What is in the storage printStorage method: " + teacherRepository.extractAllTeachers());

        return "What is in the storage printStorage method: " + teacherRepository.extractAllTeachers();
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
