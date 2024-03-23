package net.education.kyivstar.config;

import net.education.kyivstar.BaseTest;
import net.education.kyivstar.userServices.TestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;

import static net.education.kyivstar.services.util.Utils.readSqlScriptFromFileStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigTest extends BaseTest {
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
    void createTablesTest() {
        testContext.getCreateSchema().createTables();

        testContext.getReviserRepository().addReviser("Gig", "Tom", 23);
        final var revisers = testContext.getReviserRepository().extractAllRevisers();

        testContext.getStudentRepository().addStudent("Bib", "Tom", 21);
        final var students = testContext.getStudentRepository().extractAllStudents();

        testContext.getTeacherRepository().addTeacher("Fin", "Tom", 33);
        final var teachers = testContext.getTeacherRepository().extractAllTeachers();

        assertEquals(Arrays.asList("Gig", "Tom", 23), revisers);
        assertEquals(Arrays.asList("Bib", "Tom", 21), students);
        assertEquals(Arrays.asList("Fin", "Tom", 33), teachers);
    }

    @Test
    void encryptTest() {
        final var password = testContext.getPasswordEncryptor().encrypt("test");
        assertNotNull(password);
    }

    @Test
    void decryptTest() {
        final var passwordDecrypt = testContext.getPasswordEncryptor().decrypt("x/PhWKj6AD+chPfHqr984g==");
        assertEquals("test", passwordDecrypt);
    }

    @Test
    void scriptFileReader() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("scripts/schema.sql");

        String result = readSqlScriptFromFileStream(inputStream);

        final var expected = "DROP table  IF EXISTS REVISERS;\n" +
                "create table REVISERS\n" +
                "(\n" +
                "    ID            BIGINT AUTO_INCREMENT,\n" +
                "    SURNAME VARCHAR(36) NOT NULL,\n" +
                "    NAME       VARCHAR(36) NOT NULL,\n" +
                "    AGE      VARCHAR(36) NOT NULL,\n" +
                "    ENTRY_DATE  DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    PRIMARY KEY (ID)\n" +
                "\n" +
                ");\n" +
                "\n" +
                "DROP table  IF EXISTS TEACHERS;\n" +
                "CREATE TABLE TEACHERS\n" +
                "(\n" +
                "    ID            BIGINT AUTO_INCREMENT,\n" +
                "    SURNAME VARCHAR(36) NOT NULL,\n" +
                "    NAME       VARCHAR(36) NOT NULL,\n" +
                "    AGE      INT NOT NULL,\n" +
                "    ENTRY_DATE   DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    PRIMARY KEY (ID)\n" +
                ");\n" +
                "\n" +
                "DROP table  IF EXISTS STUDENTS;\n" +
                "CREATE TABLE STUDENTS\n" +
                "(\n" +
                "    ID            BIGINT AUTO_INCREMENT,\n" +
                "    SURNAME VARCHAR(36) NOT NULL,\n" +
                "    NAME       VARCHAR(36) NOT NULL,\n" +
                "    AGE      INT NOT NULL,\n" +
                "    GROUP_ID   BIGINT  NULL,\n" +
                "    ENTRY_DATE   DATETIME  NOT NULL,\n" +
                "    PRIMARY KEY (ID)\n" +
                ");\n" +
                "\n";
        assertEquals(expected.trim().replaceAll("\\s+", ""), result.trim().replaceAll("\\s+", ""));
    }
}
