package net.education.kyivstar.config;

import com.github.javafaker.Faker;
import net.education.kyivstar.BaseTest;
import net.education.kyivstar.repositories.HumanRepository;
import net.education.kyivstar.repositories.ReviserRepository;
import net.education.kyivstar.repositories.StudentRepository;
import net.education.kyivstar.repositories.TeacherRepository;
import net.education.kyivstar.services.user.UserService;
import net.education.kyivstar.services.util.Utils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;

import static net.education.kyivstar.config.MariaDbDeployment.startEmbeddedMariaDB;
import static net.education.kyivstar.config.MariaDbDeployment.stopEmbeddedMariaDB;
import static net.education.kyivstar.services.util.Utils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigTest extends BaseTest {
    Faker faker = new Faker();
    Random random = new Random();
    ConfigDataBase configDataBase = new ConfigDataBase();
    DbConnector dbConnector = new DbConnector(configDataBase);
    HumanRepository humanRepository = new HumanRepository(dbConnector);
    ReviserRepository reviserRepository = new ReviserRepository(dbConnector);
    StudentRepository studentRepository = new StudentRepository(dbConnector);
    TeacherRepository teacherRepository = new TeacherRepository(dbConnector);
    UserService userService = new UserService(faker, random, humanRepository, reviserRepository, studentRepository, teacherRepository);

    MariaDbDeployment educationEmbeddedMariaDb = new MariaDbDeployment(configDataBase);


    @Test
    void loadConfigTest() {
        configDataBase.loadConfig();

        assertEquals("jdbc:mariadb://localhost", configDataBase.getUrl());
        assertEquals(3324, configDataBase.getPort());
        assertEquals("user", configDataBase.getUser());
        assertEquals("password", configDataBase.getPass());
        assertEquals("EDUCATION", configDataBase.getDbName());
        assertEquals("D:\\Education\\MariaBBB\\d", configDataBase.getDirectory());
    }

    @Test
    void createTablesTest() throws SQLException, InterruptedException {
        try {
            startEmbeddedMariaDB();
            CreateSchema createSchema = new CreateSchema(dbConnector);
            createSchema.createTables();
            Connection conn = dbConnector.connectMariaDb(true);
            reviserRepository.addReviser("Gig", "Tom", 23);
            final var revisers = reviserRepository.extractAllRevisers();

            studentRepository.addStudent("Bib", "Tom", 21);
            final var students = studentRepository.extractAllStudents();

            teacherRepository.addTeacher("Fin", "Tom", 33);
            final var teachers = teacherRepository.extractAllTeachers();

            assertEquals(Arrays.asList("Gig", "Tom", 23), revisers);
            assertEquals(Arrays.asList("Bib", "Tom", 21), students);
            assertEquals(Arrays.asList("Fin", "Tom", 33), teachers);
        } finally {
            stopEmbeddedMariaDB();
        }
    }

    @Test
    void encryptTest() {
        final var password = PasswordEncryptor.encrypt("test");
        assertNotNull(password);
    }

    @Test
    void decryptTest() {
        final var passwordEncrypt = PasswordEncryptor.encrypt("test");
        final var passwordDecrypt = PasswordEncryptor.decrypt(passwordEncrypt);
        assertEquals("test", passwordDecrypt);
    }

    @Test
    void scriptFileReader() {
        final var result = readSqlScriptFromFile("src/test/java/resources/script_reader_test.sql");

        final var expected = "create table REVISERS\n" +
                "(\n" +
                "    ID            BIGINT AUTO_INCREMENT,\n" +
                "    SURNAME VARCHAR(36) NOT NULL,\n" +
                "    NAME       VARCHAR(36) NOT NULL,\n" +
                "    AGE      VARCHAR(36) NOT NULL,\n" +
                "    ENTRY_DATE  DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    PRIMARY KEY (ID)\n" +
                "\n" +
                ");";
        assertEquals(expected.trim(), result.trim());
    }
}