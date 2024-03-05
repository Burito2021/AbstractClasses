package net.education.kyivstar.config;

import net.education.kyivstar.BaseTest;
import net.education.kyivstar.repositories.ReviserRepository;
import net.education.kyivstar.repositories.StudentRepository;
import net.education.kyivstar.repositories.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.InputStream;
import java.util.Arrays;

import static net.education.kyivstar.services.util.Utils.readSqlScriptFromFileStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class ConfigTest extends BaseTest {
    PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
    ConfigDataBase configDataBase = new ConfigDataBase(passwordEncryptor);
    EmbeddedMariaDbLifeCycle embeddedMariaDbLifeCycle = new EmbeddedMariaDbLifeCycle(configDataBase);
    HikariConnectionManager hikari = HikariConnectionManager.getInstance(configDataBase);
    ReviserRepository reviserRepository = new ReviserRepository(hikari);
    StudentRepository studentRepository = new StudentRepository(hikari);
    TeacherRepository teacherRepository = new TeacherRepository(hikari);
    SchemaAndTableCreator createSchema = new SchemaAndTableCreator(embeddedMariaDbLifeCycle);

    @Test
    void createTablesTest() {
        try {
            createSchema.createTables();

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
            embeddedMariaDbLifeCycle.stopEmbeddedMariaDB();
        }
    }

    @Test
    void encryptTest() {
        final var password = passwordEncryptor.encrypt("test");
        assertNotNull(password);
    }

    @Test
    void decryptTest() {
        final var passwordEncrypt = passwordEncryptor.encrypt("test");
        final var passwordDecrypt = passwordEncryptor.decrypt(passwordEncrypt);
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