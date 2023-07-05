package net.education.kyivstar;

import com.github.javafaker.Faker;
import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import net.education.kyivstar.courseParticipants.Student;
import net.education.kyivstar.courseParticipants.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.*;
import static java.util.Arrays.asList;
import static net.education.kyivstar.Service.UserType.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTest {
    Faker faker = new Faker();
    Storage storage = new Storage();

    Service service = new Service(new Faker(), storage, new Random());

    @Test
    void populateStorageTest() {

        Teacher teacher = new Teacher("Aleks", "Bur", 23);

        service.add(teacher);

        final var actual = storage.getStorage();
        final var expected = asList(teacher);

        assertEquals(expected, actual);
    }

    @Test
    void createUserAndStoreTest() {
       service.createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 35);
        service.createUserAndStore(TEACHER, "NameTeacher", "SurnameTeacher", 30);
        service.createUserAndStore(STUDENT, "NameStudent", "SurnameStudent", 31);
        List<Human> expectedList = new ArrayList<>();
        expectedList.add(new Reviser("NameReviser", "SurnameReviser", 35));
        expectedList.add(new Teacher("NameTeacher", "SurnameTeacher", 30));
        expectedList.add(new Student("NameStudent", "SurnameStudent", 31));
        final var actual = storage.getStorage();
        //  final var expected = asList(new Reviser("NameReviser","SurnameReviser",35),new Teacher("NameTeacher","SurnameTeacher",30), new Student("NameStudent","SurnameStudent",31));

        assertArrayEquals(expectedList.toArray(), actual.toArray());
    }

    @Test
    void addTest() {
        final var reviser = new Reviser("NameReviser", "SurnameReviser", 35);
        final var actual = storage.getStorage();

        service.add(reviser);

        Assertions.assertEquals(asList(reviser), actual);
    }

    @Test
    void collectByAgeTest() {
        final var reviser = new Reviser("NameReviser", "SurnameReviser", 30);
        final var expected = asList(reviser);

        service.add(reviser);
        final var actual = service.collectByAge(30);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByNameTest() {
        final var student = new Student("NameReviser", "SurnameReviser", 30);
        final var expected = asList(student);

        service.add(student);
        final var actual = service.getByName("NameReviser");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getBySurnameTest() {
        final var teacher = new Teacher("NameReviser", "SurnameReviser", 30);
        final var expected = asList(teacher);

        service.add(teacher);
        final var actual = service.getBySurname("SurnameReviser");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void printStorageTest() {
        final var teacher = new Teacher("NameReviser", "SurnameReviser", 30);
        final var expected = "What is in the storage printStorage method: " + asList(teacher);

        service.add(teacher);
         final var actual = service.printStorage();

        Assertions.assertEquals(expected, actual);
    }

}