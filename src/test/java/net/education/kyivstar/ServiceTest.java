package net.education.kyivstar;

import com.github.javafaker.Faker;
import net.education.kyivstar.courseParticipants.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static net.education.kyivstar.Service.UserType.*;
import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    Service service = new Service(new Faker(), new Random());

    @Test
    void populateStorageTest() {

        service.populateStorage(2);

        final var actual = service.repository.getAll();

        assertNotNull(actual);
    }

    @Test
    void createUserAndStoreTest() {
        //check revisers
        service.createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 35);
        final var reviser = service.getByName("NameReviser");
        assertNotNull(reviser);
        assertFalse(reviser.isEmpty());
        final var reviserObject = reviser.get(0);
        assertEquals("NameReviser", reviserObject.getName());
        assertEquals("SurnameReviser", reviserObject.getSurname());
        assertEquals(35, reviserObject.getAge());

        service.createUserAndStore(TEACHER, "NameTeacher", "SurnameTeacher", 30);
        final var teacher = service.getBySurname("SurnameTeacher").get(0);

        assertEquals("NameTeacher", teacher.getName());
        assertEquals("SurnameTeacher", teacher.getSurname());
        assertEquals(30, teacher.getAge());

        service.createUserAndStore(STUDENT, "NameStudent", "SurnameStudent", 18);
        final var student = service.getBySurname("SurnameStudent").get(0);

        assertEquals("NameStudent", student.getName());
        assertEquals("NameStudent", student.getName());
        assertEquals("SurnameStudent", student.getSurname());
        assertEquals(18, student.getAge());
    }


    @Test
    void collectByAgeTest() {

        service.createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 30);
        final var actual = service.collectByAge(30);

        assertNotNull(actual);
        assertFalse(actual.isEmpty());

        assertEquals("NameReviser", actual.get(0).getName());
        assertEquals("SurnameReviser", actual.get(0).getSurname());
        assertEquals(30, actual.get(0).getAge());
    }

    @Test
    void getByNameTest() {

        service.createUserAndStore(TEACHER, "NameTeacher", "SurnameTeacher", 25);
        final var actual = service.getByName("NameTeacher");

        assertNotNull(actual);
        assertFalse(actual.isEmpty());

        assertEquals("NameTeacher", actual.get(0).getName());
        assertEquals("SurnameTeacher", actual.get(0).getSurname());
        assertEquals(25, actual.get(0).getAge());
    }

    @Test
    void getBySurnameTest() {

        service.createUserAndStore(TEACHER, "NameTeacher", "SurnameTeacher", 25);
        final var actual = service.getBySurname("SurnameTeacher");

        assertNotNull(actual);
        assertFalse(actual.isEmpty());

        assertEquals("NameTeacher", actual.get(0).getName());
        assertEquals("SurnameTeacher", actual.get(0).getSurname());
        assertEquals(25, actual.get(0).getAge());
    }

    @Test
    void printStorageTest() {

        service.createUserAndStore(STUDENT, "NameTeacher", "SurnameTeacher", 25);
        final var actual = service.getBySurname("SurnameTeacher");

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        final var expected = "What is in the storage printStorage method: "+ Arrays.asList(new Student("NameTeacher", "SurnameTeacher", 25));
        final var print =service.printStorage();
        assertEquals(expected,print);
    }

    @Test
    void removeBySurnameTest() {

        service.createUserAndStore(STUDENT, "NameTeacher", "SurnameTeacher", 25);
        final var actual = service.getBySurname("SurnameTeacher");

        assertNotNull(actual);
        assertFalse(actual.isEmpty());

        service.remove("SurnameTeacher");
        assertNotNull(actual);
        service.printStorage();
        assertFalse(actual.isEmpty());
        service.printStorage();
    }

    @Test
    void updateBySurnameTest() {

        service.createUserAndStore(TEACHER, "NameTeacher", "SurnameTeacher", 25);
        final var actual = service.getBySurname("SurnameTeacher");

        assertNotNull(actual);
        assertFalse(actual.isEmpty());

        service.update("SurnameTeacher",new Student("NameStudent", "SurnameStudent", 18));

        final var updatedActual = service.repository.getAll();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());

        assertEquals("NameStudent",updatedActual.get(0).getName());
        assertEquals("SurnameStudent",updatedActual.get(0).getSurname());
        assertEquals(18,updatedActual.get(0).getAge());
    }
}