package net.education.kyivstar;

import net.education.kyivstar.config.CreateSchema;
import net.education.kyivstar.services.user.UserService;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import static java.util.Arrays.asList;
import static net.education.kyivstar.Names.*;
import static net.education.kyivstar.services.user.UserType.*;
import static net.education.kyivstar.config.EducationEmbeddedMariaDb.startEmbeddedMariaDB;
import static net.education.kyivstar.config.EducationEmbeddedMariaDb.stopEmbeddedMariaDB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class UserServiceTest {
    CreateSchema createSchema = new CreateSchema();
    UserService userService = new UserService();

    @BeforeAll
    void clean() {
        startEmbeddedMariaDB();
        createSchema.createDataBase();
        createSchema.createTables();
    }

    @BeforeEach
    void cleanDb() throws SQLException {
        userService.removeAll();
    }

    @AfterAll
    void cleanAfter() {
        stopEmbeddedMariaDB();
    }

    @Test
    void populateStorageTest() throws SQLException {
        userService.populateStorage(5);
        final var list = userService.countUsersByCategory();

        assertNotNull(list.get(REVISERS));
        assertNotNull(list.get(STUDENTS));
        assertNotNull(list.get(TEACHERS));

        final var count1 = list.get(REVISERS);
        final var count2 = list.get(STUDENTS);
        final var count3 = list.get(TEACHERS);
        final var countAll = count1 + count2 + count3;

        assertEquals(5, countAll);
    }

    @Test
    void collectRevisersTest() throws SQLException {
        userService.createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        userService.createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        userService.createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        userService.createUserAndStore(STUDENT, "SurnameStudent", "NameStudent", 18);

        final var revisers = userService.collectRevisers();
        final var countOfRevisers = userService.countUsersByCategory().get(REVISERS);

        assertEquals(2, countOfRevisers);
        assertEquals(asList("SurnameReviser1", "NameReviser1", 21, "SurnameReviser2", "NameReviser2", 29), revisers);
    }

    @Test
    void collectRevisersBySurname() throws SQLException {
        userService.createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        userService.createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        userService.createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        userService.createUserAndStore(STUDENT, "SurnameStudent", "NameStudent", 18);

        final var countOfRevisers = userService.countUsersByCategory().get(REVISERS);

        assertEquals(2, countOfRevisers);
        assertEquals(asList("SurnameReviser1", "NameReviser1", 21), userService.collectReviserBySurname("SurnameReviser1"));
        assertEquals(asList("SurnameReviser2", "NameReviser2", 29), userService.collectReviserBySurname("SurnameReviser2"));
    }

    @Test
    void collectStudentsBySurname() throws SQLException {
        userService.createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        userService.createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        userService.createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        userService.createUserAndStore(STUDENT, "SurnameStudent", "NameStudent", 18);

        final var countOfStudents = userService.countUsersByCategory().get(STUDENTS);
        final var student = userService.collectStudentsBySurname("SurnameStudent");
        assertNotNull(student);
        assertEquals(1, countOfStudents);
        assertEquals(asList("SurnameStudent", "NameStudent", 18), student);
    }

    @Test
    void collectTeachersBySurname() throws SQLException {
        userService.createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        userService.createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        userService.createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        userService.createUserAndStore(TEACHER, "SurnameTeacher2", "NameTeacher2", 32);
        userService.createUserAndStore(STUDENT, "SurnameStudent", "NameStudent", 18);

        final var countOfRevisers = userService.countUsersByCategory().get(TEACHERS);

        assertNotNull(countOfRevisers);
        assertEquals(2, countOfRevisers);

        final var teacher1 = userService.collectTeachersBySurname("SurnameTeacher");
        final var teacher2 = userService.collectTeachersBySurname("SurnameTeacher2");
        assertNotNull(teacher1);
        assertNotNull(teacher2);
        assertEquals(asList("SurnameTeacher", "NameTeacher", 30), teacher1);
        assertEquals(asList("SurnameTeacher2", "NameTeacher2", 32), teacher2);
    }

    @Test
    void createUserAndStoreTest() throws SQLException {
        userService.createUserAndStore(STUDENT, "Student", "Studenti", 18);
        userService.createUserAndStore(TEACHER, "Teacher", "Teacheri", 40);
        userService.createUserAndStore(REVISER, "Reviser", "Reviseri", 29);

        final var revisers = userService.collectRevisers();
        final var students = userService.collectStudents();
        final var teachers = userService.collectTeachers();

        assertNotNull(revisers);
        assertNotNull(students);
        assertNotNull(teachers);

        final var revisersCount = userService.countUsersByCategory().get(REVISERS);
        final var studentCount = userService.countUsersByCategory().get(STUDENTS);
        final var teacherCount = userService.countUsersByCategory().get(TEACHERS);

        assertEquals(1, revisersCount);
        assertEquals(1, studentCount);
        assertEquals(1, teacherCount);

        assertEquals(asList("Reviser", "Reviseri", 29), revisers);

        assertEquals(asList("Student", "Studenti", 18), students);

        assertEquals(asList("Teacher", "Teacheri", 40), teachers);
    }

    @Test
    void collectStudentsTest() throws SQLException {
        userService.createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        userService.createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        userService.createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        userService.createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        userService.createUserAndStore(STUDENT, "SurnameStudent2", "NameStudent2", 21);
        userService.createUserAndStore(STUDENT, "SurnameStudent3", "NameStudent3", 23);

        final var students = userService.collectStudents();
        final var countOfStudents = userService.countUsersByCategory().get(STUDENTS);

        assertEquals(3, countOfStudents);
        assertEquals(asList("SurnameStudent1", "NameStudent1", 18, "SurnameStudent2", "NameStudent2", 21, "SurnameStudent3", "NameStudent3", 23), students);
    }

    @Test
    void collectTeachersTest() throws SQLException {
        userService.createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        userService.createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        userService.createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        userService.createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        userService.createUserAndStore(STUDENT, "SurnameStudent2", "NameStudent2", 21);
        userService.createUserAndStore(STUDENT, "SurnameStudent3", "NameStudent3", 23);

        final var teachers = userService.collectTeachers();
        final var countOfTeachers = userService.countUsersByCategory().get(TEACHERS);

        assertEquals(1, countOfTeachers);
        assertEquals(asList("SurnameTeacher", "NameTeacher", 30), teachers);
    }

    @Test
    void collectAllUsersTest() throws SQLException {
        userService.createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        userService.createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        userService.createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        userService.createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        userService.createUserAndStore(STUDENT, "SurnameStudent2", "NameStudent2", 21);
        userService.createUserAndStore(STUDENT, "SurnameStudent3", "NameStudent3", 23);

        final var list = userService.collectAllUsers();

        assertNotNull(list);

        assertEquals(asList("SurnameReviser1", "NameReviser1", 21, "SurnameReviser2", "NameReviser2", 29,
                "SurnameTeacher", "NameTeacher", 30, "SurnameStudent1", "NameStudent1", 18,
                "SurnameStudent2", "NameStudent2", 21, "SurnameStudent3", "NameStudent3", 23), list);
    }

    @Test
    void collectBySurnameTest() throws SQLException {
        userService.createUserAndStore(REVISER, "SurnameReviser", "NameReviser", 30);
        userService.createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        userService.createUserAndStore(TEACHER, "SurnameTeacher1", "NameTeacher1", 21);


        final var reviser = userService.collectBySurname("SurnameReviser");
        final var student = userService.collectBySurname("SurnameStudent1");
        final var teacher = userService.collectBySurname("SurnameTeacher1");

        assertNotNull(reviser);
        assertNotNull(student);
        assertNotNull(teacher);

        assertEquals(asList("SurnameReviser", "NameReviser", 30), reviser);
        assertEquals(asList("SurnameStudent1", "NameStudent1", 18), student);
        assertEquals(asList("SurnameTeacher1", "NameTeacher1", 21), teacher);
    }

    @Test
    void replaceUserTest() throws SQLException {
        userService.createUserAndStore(REVISER, "SurnameReviser", "NameReviser", 30);
        userService.createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        userService.createUserAndStore(STUDENT, "SurnameStudent2", "NameStudent2", 21);
        userService.createUserAndStore(TEACHER, "SurnameTeacher1", "NameTeacher1", 21);
        userService.createUserAndStore(TEACHER, "SurnameTeacher2", "NameTeacher2", 29);

        userService.replaceUser("SurnameReviser", REVISER, "ReplaceNameReviser", "ReplaceSurnameReviser", 48);
        final var replacedReviser = userService.collectBySurname("ReplaceSurnameReviser");
        Assertions.assertEquals(asList("ReplaceSurnameReviser", "ReplaceNameReviser", 48), replacedReviser);

        userService.replaceUser("SurnameStudent1", STUDENT, "ReplaceNameStudent", "ReplaceSurnameStudent", 22);
        final var replacedStudent = userService.collectBySurname("ReplaceSurnameStudent");
        Assertions.assertEquals(asList("ReplaceSurnameStudent", "ReplaceNameStudent", 22), replacedStudent);

        userService.replaceUser("SurnameTeacher2", TEACHER, "ReplaceNameTeacher", "ReplaceSurnameTeacher", 42);
        final var replacedTeacher = userService.collectBySurname("ReplaceSurnameTeacher");
        Assertions.assertEquals(asList("ReplaceSurnameTeacher", "ReplaceNameTeacher", 42), replacedTeacher);


    }

    @Test
    void removeUserTest() throws SQLException {
        userService.createUserAndStore(REVISER, "SurnameReviser", "NameReviser", 30);
        userService.createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        userService.createUserAndStore(STUDENT, "SurnameStudent2", "NameStudent2", 21);
        userService.createUserAndStore(TEACHER, "SurnameTeacher1", "NameTeacher1", 21);
        userService.createUserAndStore(TEACHER, "SurnameTeacher2", "NameTeacher2", 29);

        final var allUsers = userService.collectAllUsers();
        Assertions.assertEquals(asList("SurnameReviser", "NameReviser", 30, "SurnameTeacher1", "NameTeacher1", 21, "SurnameTeacher2", "NameTeacher2", 29, "SurnameStudent1", "NameStudent1", 18, "SurnameStudent2", "NameStudent2", 21),
                allUsers);
        userService.removeUser("SurnameReviser", "NameReviser");

        final var replacedUser = userService.collectBySurname("SurnameReviser");
        Assertions.assertTrue(replacedUser.isEmpty());
    }

    @Test
    void removeAllUsersTest() throws SQLException {
        userService.createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 30);
        userService.createUserAndStore(STUDENT, "NameStudent1", "SurnameStudent1", 18);
        userService.createUserAndStore(STUDENT, "NameStudent2", "SurnameStudent2", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher1", "SurnameTeacher1", 21);
        userService.createUserAndStore(TEACHER, "NameTeacher2", "SurnameTeacher2", 29);

        userService.removeAll();

        Assertions.assertTrue(userService.collectAllUsers().isEmpty());
    }
}