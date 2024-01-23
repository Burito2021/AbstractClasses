package net.education.kyivstar;


import com.github.javafaker.Faker;
import net.education.kyivstar.config.ConfigDataBase;
import net.education.kyivstar.config.CreateSchema;
import net.education.kyivstar.config.EducationEmbeddedMariaDb;
import net.education.kyivstar.repositories.HumanRepository;
import net.education.kyivstar.repositories.ReviserRepository;
import net.education.kyivstar.repositories.StudentRepository;
import net.education.kyivstar.repositories.TeacherRepository;
import net.education.kyivstar.services.db.DbConnector;
import net.education.kyivstar.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Random;

import static net.education.kyivstar.config.EducationEmbeddedMariaDb.startEmbeddedMariaDB;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws SQLException {
        Faker faker = new Faker();
        Random random = new Random();
        ConfigDataBase configDataBase = new ConfigDataBase();
        DbConnector dbConnector = new DbConnector(configDataBase);
        EducationEmbeddedMariaDb educationEmbeddedMariaDb = new EducationEmbeddedMariaDb(configDataBase);
        CreateSchema createSchema = new CreateSchema(dbConnector);
        HumanRepository humanRepository = new HumanRepository(dbConnector);
        ReviserRepository reviserRepository = new ReviserRepository(dbConnector);
        StudentRepository studentRepository = new StudentRepository(dbConnector);
        TeacherRepository teacherRepository = new TeacherRepository(dbConnector);
        UserService userService = new UserService(faker, random, humanRepository, reviserRepository, studentRepository, teacherRepository);

        startEmbeddedMariaDB();

        createSchema.createDataBase();
        createSchema.createTables();
        userService.populateStorage(10);
        userService.countAllUsers();
        userService.printStorageAllUsers();
    }
}
