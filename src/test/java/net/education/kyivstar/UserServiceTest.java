package net.education.kyivstar;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static net.education.kyivstar.UserType.*;


class UserServiceTest {
    Faker faker = new Faker();
    Random random = new Random();
    Storage storage = new Storage();
    ReviserRepository reviserRepository = new ReviserRepository(storage);
    StudentRepository studentRepository = new StudentRepository(storage);
    TeacherRepository teacherRepository = new TeacherRepository(storage);
    HumanRepository humanRepository = new HumanRepository(storage);
    UserService userService = new UserService(faker, random, humanRepository, reviserRepository, studentRepository, teacherRepository);


    @Test
    void populateStorageTest() {
        userService.populateStorage(5);
        final var list = userService.collectAllUsers();
        Assertions.assertEquals(5, list.size());
        Assertions.assertNotNull(list.get(0));
        Assertions.assertNotNull(list.get(1));
        Assertions.assertNotNull(list.get(2));
        Assertions.assertNotNull(list.get(3));
        Assertions.assertNotNull(list.get(4));
    }

    @Test
    void createUserAndStoreTest() {
        userService.createUserAndStore(STUDENT, "Student", "Studenti", 18);
        userService.createUserAndStore(TEACHER, "Teacher", "Teacheri", 40);
        userService.createUserAndStore(REVISER, "Reviser", "Reviseri", 29);
        final var studentList = userService.collectBySurname("Studenti");
        final var student = studentList.get(0);
        Assertions.assertNotNull(student);
        Assertions.assertFalse(studentList.isEmpty());

        Assertions.assertEquals("Student", student.getName());
        Assertions.assertEquals("Studenti", student.getSurname());
        Assertions.assertEquals(18, student.getAge());

        final var teacherList = userService.collectBySurname("Teacheri");
        final var teacher = teacherList.get(0);
        Assertions.assertNotNull(teacher);
        Assertions.assertFalse(teacherList.isEmpty());

        Assertions.assertEquals("Teacher", teacher.getName());
        Assertions.assertEquals("Teacheri", teacher.getSurname());
        Assertions.assertEquals(40, teacher.getAge());

        final var reviserList = userService.collectBySurname("Reviseri");
        final var reviser = reviserList.get(0);
        Assertions.assertNotNull(reviser);
        Assertions.assertFalse(reviserList.isEmpty());

        Assertions.assertEquals("Reviser", reviser.getName());
        Assertions.assertEquals("Reviseri", reviser.getSurname());
        Assertions.assertEquals(29, reviser.getAge());
    }

    @Test
    void collectRevisersTest() {
        userService.createUserAndStore(REVISER, "NameReviser1", "SurnameReviser1", 21);
        userService.createUserAndStore(REVISER, "NameReviser2", "SurnameReviser2", 29);
        userService.createUserAndStore(TEACHER, "NameTeacher", "SurnameTeacher", 30);
        userService.createUserAndStore(STUDENT, "NameStudent", "SurnameStudent", 18);

        final var listOfRevisers = userService.collectRevisers();

        Assertions.assertEquals(2, listOfRevisers.stream().count());
        Assertions.assertEquals("NameReviser1", listOfRevisers.get(0).getName());
        Assertions.assertEquals("SurnameReviser1", listOfRevisers.get(0).getSurname());
        Assertions.assertEquals(21, listOfRevisers.get(0).getAge());
        Assertions.assertEquals("NameReviser2", listOfRevisers.get(1).getName());
        Assertions.assertEquals("SurnameReviser2", listOfRevisers.get(1).getSurname());
        Assertions.assertEquals(29, listOfRevisers.get(1).getAge());
    }

    @Test
    void collectStudentsTest() {
        userService.createUserAndStore(REVISER, "NameReviser1", "SurnameReviser1", 21);
        userService.createUserAndStore(REVISER, "NameReviser2", "SurnameReviser2", 29);
        userService.createUserAndStore(TEACHER, "NameTeacher", "SurnameTeacher", 30);
        userService.createUserAndStore(STUDENT, "NameStudent1", "SurnameStudent1", 18);
        userService.createUserAndStore(STUDENT, "NameStudent2", "SurnameStudent2", 21);
        final var listOfStudents = userService.collectStudents();

        Assertions.assertEquals(2, listOfStudents.stream().count());
        Assertions.assertEquals("NameStudent1", listOfStudents.get(0).getName());
        Assertions.assertEquals("SurnameStudent1", listOfStudents.get(0).getSurname());
        Assertions.assertEquals(18, listOfStudents.get(0).getAge());
        Assertions.assertEquals("NameStudent2", listOfStudents.get(1).getName());
        Assertions.assertEquals("SurnameStudent2", listOfStudents.get(1).getSurname());
        Assertions.assertEquals(21, listOfStudents.get(1).getAge());
    }

    @Test
    void collectTeachersTest() {
        userService.createUserAndStore(TEACHER, "NameTeacher1", "SurnameTeacher1", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher2", "SurnameTeacher2", 29);
        userService.createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 30);
        userService.createUserAndStore(STUDENT, "NameStudent1", "SurnameStudent1", 18);
        userService.createUserAndStore(STUDENT, "NameStudent2", "SurnameStudent2", 21);
        final var listOfStudents = userService.collectTeachers();

        Assertions.assertEquals(2, listOfStudents.stream().count());
        Assertions.assertEquals("NameTeacher1", listOfStudents.get(0).getName());
        Assertions.assertEquals("SurnameTeacher1", listOfStudents.get(0).getSurname());
        Assertions.assertEquals(21, listOfStudents.get(0).getAge());
        Assertions.assertEquals("NameTeacher2", listOfStudents.get(1).getName());
        Assertions.assertEquals("SurnameTeacher2", listOfStudents.get(1).getSurname());
        Assertions.assertEquals(29, listOfStudents.get(1).getAge());
    }

    @Test
    void collectAllUsersTest() {
        userService.createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 30);
        userService.createUserAndStore(STUDENT, "NameStudent1", "SurnameStudent1", 18);
        userService.createUserAndStore(STUDENT, "NameStudent2", "SurnameStudent2", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher1", "SurnameTeacher1", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher2", "SurnameTeacher2", 29);

        final var list = userService.collectAllUsers();

        Assertions.assertEquals(5, list.stream().count());
        Assertions.assertEquals("NameReviser", list.get(0).getName());
        Assertions.assertEquals("SurnameReviser", list.get(0).getSurname());
        Assertions.assertEquals(30, list.get(0).getAge());

        Assertions.assertEquals("NameStudent1", list.get(1).getName());
        Assertions.assertEquals("SurnameStudent1", list.get(1).getSurname());
        Assertions.assertEquals(18, list.get(1).getAge());

        Assertions.assertEquals("NameStudent2", list.get(2).getName());
        Assertions.assertEquals("SurnameStudent2", list.get(2).getSurname());
        Assertions.assertEquals(21, list.get(2).getAge());

        Assertions.assertEquals("NameTeacher1", list.get(3).getName());
        Assertions.assertEquals("SurnameTeacher1", list.get(3).getSurname());
        Assertions.assertEquals(21, list.get(3).getAge());

        Assertions.assertEquals("NameTeacher2", list.get(4).getName());
        Assertions.assertEquals("SurnameTeacher2", list.get(4).getSurname());
        Assertions.assertEquals(29, list.get(4).getAge());
    }

    @Test
    void collectBySurnameTest() {
        userService.createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 30);
        userService.createUserAndStore(STUDENT, "NameStudent1", "SurnameStudent1", 18);
        userService.createUserAndStore(STUDENT, "NameStudent2", "SurnameStudent2", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher1", "SurnameTeacher1", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher2", "SurnameTeacher2", 29);

        final var list = userService.collectBySurname("SurnameTeacher2").get(0);

        Assertions.assertEquals("NameTeacher2", list.getName());
        Assertions.assertEquals("SurnameTeacher2", list.getSurname());
        Assertions.assertEquals(29, list.getAge());
    }

    @Test
    void replaceUserTest() {
        userService.createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 30);
        userService.createUserAndStore(STUDENT, "NameStudent1", "SurnameStudent1", 18);
        userService.createUserAndStore(STUDENT, "NameStudent2", "SurnameStudent2", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher1", "SurnameTeacher1", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher2", "SurnameTeacher2", 29);

        userService.replaceUser("SurnameStudent1", STUDENT, "ReplaceName", "ReplaceSurname", 18);

        final var replacedUser = userService.collectBySurname("ReplaceSurname").get(0);
        Assertions.assertEquals("ReplaceName",replacedUser.getName());
        Assertions.assertEquals("ReplaceSurname",replacedUser.getSurname());
        Assertions.assertEquals(18,replacedUser.getAge());

        userService.replaceUser("SurnameReviser", REVISER, "ReplaceReviserName", "ReplaceReviserSurname", 19);
        final var replacedReviser = userService.collectBySurname("ReplaceReviserSurname").get(0);
        Assertions.assertEquals("ReplaceReviserName",replacedReviser.getName());
        Assertions.assertEquals("ReplaceReviserSurname",replacedReviser.getSurname());
        Assertions.assertEquals(19,replacedReviser.getAge());

        userService.replaceUser("SurnameTeacher1", TEACHER, "ReplaceTeacherName", "ReplaceTeacherSurname", 21);
        final var replacedTeacher = userService.collectBySurname("ReplaceTeacherSurname").get(0);
        Assertions.assertEquals("ReplaceTeacherName",replacedTeacher.getName());
        Assertions.assertEquals("ReplaceTeacherSurname",replacedTeacher.getSurname());
        Assertions.assertEquals(21,replacedTeacher.getAge());
    }


    @Test
    void removeUserTest() {
        userService.createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 30);
        userService.createUserAndStore(STUDENT, "NameStudent1", "SurnameStudent1", 18);
        userService.createUserAndStore(STUDENT, "NameStudent2", "SurnameStudent2", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher1", "SurnameTeacher1", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher2", "SurnameTeacher2", 29);

        userService.removeUser("SurnameStudent1");

        final var replacedUser = userService.collectBySurname("SurnameStudent1");
        Assertions.assertTrue( replacedUser.isEmpty());
    }

    @Test
    void removeAllUsersTest() {
        userService.createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 30);
        userService.createUserAndStore(STUDENT, "NameStudent1", "SurnameStudent1", 18);
        userService.createUserAndStore(STUDENT, "NameStudent2", "SurnameStudent2", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher1", "SurnameTeacher1", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher2", "SurnameTeacher2", 29);

        userService.removeAll();

        Assertions.assertTrue(userService.collectAllUsers().isEmpty());
    }
}