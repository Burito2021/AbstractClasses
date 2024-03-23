package net.education.kyivstar.userServices;

import net.education.kyivstar.BaseTest;
import net.education.kyivstar.services.util.Utils;
import org.junit.jupiter.api.*;

import static java.util.Arrays.asList;
import static net.education.kyivstar.services.user.UserType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class UserServiceTest extends BaseTest {
    static TestContext testContext;

    @BeforeAll
    static void init() {
        testContext = new TestContext();
        testContext.getCreateSchema().createTables();
    }

    @AfterAll
    static void destroy() {
        testContext.getHikariConnectManager().closeConnection();
        testContext.getEmbeddedMariaDbLifeCycle().stopEmbeddedMariaDB();
    }

    @BeforeEach
    void cleanRepository() {
        testContext.getUserService().removeAll();
    }

    @Test
    void setTeacherRepository() {
        Utils.createDirectoryIfNotExists(testContext.getConfigDataBase().getDirectory());
    }

    @Test
    void populateStorageTest() {
        testContext.getUserService().populateStorage(5);
        final var list = testContext.getUserService().countUsersByCategory();

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
    void collectRevisersTest() {
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent", "NameStudent", 18);

        final var revisers = testContext.getUserService().collectRevisers();
        final var countOfRevisers = testContext.getUserService().countUsersByCategory().get(REVISERS);

        assertEquals(2, countOfRevisers);
        assertEquals(asList("SurnameReviser1", "NameReviser1", 21, "SurnameReviser2", "NameReviser2", 29), revisers);
    }

    @Test
    void collectRevisersBySurname() {
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent", "NameStudent", 18);

        final var countOfRevisers = testContext.getUserService().countUsersByCategory().get(REVISERS);

        assertEquals(2, countOfRevisers);
        assertEquals(asList("SurnameReviser1", "NameReviser1", 21), testContext.getUserService().collectReviserBySurname("SurnameReviser1"));
        assertEquals(asList("SurnameReviser2", "NameReviser2", 29), testContext.getUserService().collectReviserBySurname("SurnameReviser2"));
    }

    @Test
    void collectStudentsBySurname() {
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent", "NameStudent", 18);

        final var countOfStudents = testContext.getUserService().countUsersByCategory().get(STUDENTS);
        final var student = testContext.getUserService().collectStudentsBySurname("SurnameStudent");
        assertNotNull(student);
        assertEquals(1, countOfStudents);
        assertEquals(asList("SurnameStudent", "NameStudent", 18), student);
    }

    @Test
    void collectTeachersBySurname() {
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher2", "NameTeacher2", 32);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent", "NameStudent", 18);

        final var countOfRevisers = testContext.getUserService().countUsersByCategory().get(TEACHERS);

        assertNotNull(countOfRevisers);
        assertEquals(2, countOfRevisers);

        final var teacher1 = testContext.getUserService().collectTeachersBySurname("SurnameTeacher");
        final var teacher2 = testContext.getUserService().collectTeachersBySurname("SurnameTeacher2");
        assertNotNull(teacher1);
        assertNotNull(teacher2);
        assertEquals(asList("SurnameTeacher", "NameTeacher", 30), teacher1);
        assertEquals(asList("SurnameTeacher2", "NameTeacher2", 32), teacher2);
    }

    @Test
    void createUserAndStoreTest() {
        testContext.getUserService().createUserAndStore(STUDENT, "Student", "Studenti", 18);
        testContext.getUserService().createUserAndStore(TEACHER, "Teacher", "Teacheri", 40);
        testContext.getUserService().createUserAndStore(REVISER, "Reviser", "Reviseri", 29);

        final var revisers = testContext.getUserService().collectRevisers();
        final var students = testContext.getUserService().collectStudents();
        final var teachers = testContext.getUserService().collectTeachers();

        assertNotNull(revisers);
        assertNotNull(students);
        assertNotNull(teachers);

        final var revisersCount = testContext.getUserService().countUsersByCategory().get(REVISERS);
        final var studentCount = testContext.getUserService().countUsersByCategory().get(STUDENTS);
        final var teacherCount = testContext.getUserService().countUsersByCategory().get(TEACHERS);

        assertEquals(1, revisersCount);
        assertEquals(1, studentCount);
        assertEquals(1, teacherCount);

        assertEquals(asList("Reviser", "Reviseri", 29), revisers);
        assertEquals(asList("Student", "Studenti", 18), students);
        assertEquals(asList("Teacher", "Teacheri", 40), teachers);
    }

    @Test
    void collectStudentsTest() {
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent2", "NameStudent2", 21);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent3", "NameStudent3", 23);

        final var students = testContext.getUserService().collectStudents();
        final var countOfStudents = testContext.getUserService().countUsersByCategory().get(STUDENTS);

        assertEquals(3, countOfStudents);
        assertEquals(asList("SurnameStudent1", "NameStudent1", 18, "SurnameStudent2", "NameStudent2", 21, "SurnameStudent3", "NameStudent3", 23), students);
    }

    @Test
    void collectTeachersTest() {
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent2", "NameStudent2", 21);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent3", "NameStudent3", 23);

        final var teachers = testContext.getUserService().collectTeachers();
        final var countOfTeachers = testContext.getUserService().countUsersByCategory().get(TEACHERS);

        assertEquals(1, countOfTeachers);
        assertEquals(asList("SurnameTeacher", "NameTeacher", 30), teachers);
    }

    @Test
    void collectAllUsersTest() {
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser1", "NameReviser1", 21);
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser2", "NameReviser2", 29);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher", "NameTeacher", 30);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent2", "NameStudent2", 21);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent3", "NameStudent3", 23);

        final var list = testContext.getUserService().collectAllUsers();

        assertNotNull(list);

        assertEquals(asList("SurnameReviser1", "NameReviser1", 21, "SurnameReviser2", "NameReviser2", 29,
                "SurnameTeacher", "NameTeacher", 30, "SurnameStudent1", "NameStudent1", 18,
                "SurnameStudent2", "NameStudent2", 21, "SurnameStudent3", "NameStudent3", 23), list);
    }

    @Test
    void collectBySurnameTest() {
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser", "NameReviser", 30);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher1", "NameTeacher1", 21);


        final var reviser = testContext.getUserService().collectBySurname("SurnameReviser");
        final var student = testContext.getUserService().collectBySurname("SurnameStudent1");
        final var teacher = testContext.getUserService().collectBySurname("SurnameTeacher1");

        assertNotNull(reviser);
        assertNotNull(student);
        assertNotNull(teacher);

        assertEquals(asList("SurnameReviser", "NameReviser", 30), reviser);
        assertEquals(asList("SurnameStudent1", "NameStudent1", 18), student);
        assertEquals(asList("SurnameTeacher1", "NameTeacher1", 21), teacher);
    }

    @Test
    void replaceUserTest() {
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser", "NameReviser", 30);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent2", "NameStudent2", 21);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher1", "NameTeacher1", 21);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher2", "NameTeacher2", 29);

        testContext.getUserService().replaceUser("SurnameReviser", REVISER, "ReplaceNameReviser", "ReplaceSurnameReviser", 48);
        final var replacedReviser = testContext.getUserService().collectBySurname("ReplaceSurnameReviser");
        Assertions.assertEquals(asList("ReplaceSurnameReviser", "ReplaceNameReviser", 48), replacedReviser);

        testContext.getUserService().replaceUser("SurnameStudent1", STUDENT, "ReplaceNameStudent", "ReplaceSurnameStudent", 22);
        final var replacedStudent = testContext.getUserService().collectBySurname("ReplaceSurnameStudent");
        Assertions.assertEquals(asList("ReplaceSurnameStudent", "ReplaceNameStudent", 22), replacedStudent);

        testContext.getUserService().replaceUser("SurnameTeacher2", TEACHER, "ReplaceNameTeacher", "ReplaceSurnameTeacher", 42);
        final var replacedTeacher = testContext.getUserService().collectBySurname("ReplaceSurnameTeacher");
        Assertions.assertEquals(asList("ReplaceSurnameTeacher", "ReplaceNameTeacher", 42), replacedTeacher);
    }

    @Test
    void removeUserTest() {
        testContext.getUserService().createUserAndStore(REVISER, "SurnameReviser", "NameReviser", 30);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent1", "NameStudent1", 18);
        testContext.getUserService().createUserAndStore(STUDENT, "SurnameStudent2", "NameStudent2", 21);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher1", "NameTeacher1", 21);
        testContext.getUserService().createUserAndStore(TEACHER, "SurnameTeacher2", "NameTeacher2", 29);

        final var allUsers = testContext.getUserService().collectAllUsers();
        Assertions.assertEquals(asList("SurnameReviser", "NameReviser", 30, "SurnameTeacher1", "NameTeacher1", 21, "SurnameTeacher2", "NameTeacher2", 29, "SurnameStudent1", "NameStudent1", 18, "SurnameStudent2", "NameStudent2", 21),
                allUsers);
        testContext.getUserService().removeUser("SurnameReviser", "NameReviser");

        final var replacedUser = testContext.getUserService().collectBySurname("SurnameReviser");
        Assertions.assertTrue(replacedUser.isEmpty());
    }

    @Test
    void removeAllUsersTest() {
        testContext.getUserService().createUserAndStore(REVISER, "NameReviser", "SurnameReviser", 30);
        testContext.getUserService().createUserAndStore(STUDENT, "NameStudent1", "SurnameStudent1", 18);
        testContext.getUserService().createUserAndStore(STUDENT, "NameStudent2", "SurnameStudent2", 21);
        testContext.getUserService().createUserAndStore(TEACHER, "NameTeacher1", "SurnameTeacher1", 21);
        testContext.getUserService().createUserAndStore(TEACHER, "NameTeacher2", "SurnameTeacher2", 29);

        testContext.getUserService().removeAll();

        Assertions.assertTrue(testContext.getUserService().collectAllUsers().isEmpty());
    }
}