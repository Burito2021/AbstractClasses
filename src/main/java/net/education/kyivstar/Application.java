package net.education.kyivstar;

import com.github.javafaker.Faker;
import net.education.kyivstar.config.*;
import net.education.kyivstar.repositories.HumanRepository;
import net.education.kyivstar.repositories.ReviserRepository;
import net.education.kyivstar.repositories.StudentRepository;
import net.education.kyivstar.repositories.TeacherRepository;
import net.education.kyivstar.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        var faker = new Faker();
        var random = new Random();
        var passwordEncryptor = new PasswordEncryptor();
        var configDataBase = new ConfigDataBase(passwordEncryptor);
        var embeddedMariaDbLifeCycle = new EmbeddedMariaDbLifeCycle(configDataBase);
        var createSchema = new SchemaAndTableCreator(embeddedMariaDbLifeCycle);
        createSchema.createDataBase();
        var hikariConnectManager = HikariConnectionManager.getInstance(configDataBase);
        var humanRepository = new HumanRepository(hikariConnectManager);
        var reviserRepository = new ReviserRepository(hikariConnectManager);
        var studentRepository = new StudentRepository(hikariConnectManager);
        var teacherRepository = new TeacherRepository(hikariConnectManager);
        var userService = new UserService(faker, random, humanRepository, reviserRepository, studentRepository, teacherRepository);


        try {
            createSchema.createTables();
            userService.populateStorage(10);
            userService.countAllUsers();
            userService.printStorageAllUsers();
        } catch (Exception e) {
            logger.debug("Application error " + e);
            throw new RuntimeException("Error " + e);
        } finally {
            hikariConnectManager.closeConnection();
            embeddedMariaDbLifeCycle.stopEmbeddedMariaDB();
        }
    }
}
