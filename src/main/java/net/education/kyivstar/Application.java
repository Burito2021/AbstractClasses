package net.education.kyivstar;


import com.github.javafaker.Faker;
import net.education.kyivstar.config.ConfigDataBase;
import net.education.kyivstar.config.CreateSchema;
import net.education.kyivstar.config.HikariConfiguration;
import net.education.kyivstar.config.MariaDbDeploy;
import net.education.kyivstar.repositories.HumanRepository;
import net.education.kyivstar.repositories.ReviserRepository;
import net.education.kyivstar.repositories.StudentRepository;
import net.education.kyivstar.repositories.TeacherRepository;
import net.education.kyivstar.services.user.UserService;

import java.sql.SQLException;
import java.util.Random;

import static net.education.kyivstar.services.util.Utils.createDirectoryIfNotExists;

public class Application {

    public static void main(String[] args) throws SQLException {
        Faker faker = new Faker();
        Random random = new Random();
        ConfigDataBase configDataBase = new ConfigDataBase();
        HikariConfiguration hikariDataBase = new HikariConfiguration(configDataBase, false);
        HikariConfiguration hikariTables = new HikariConfiguration(configDataBase, true);
        HumanRepository humanRepository = new HumanRepository(hikariTables);
        ReviserRepository reviserRepository = new ReviserRepository(hikariTables);
        StudentRepository studentRepository = new StudentRepository(hikariTables);
        TeacherRepository teacherRepository = new TeacherRepository(hikariTables);
        UserService userService = new UserService(faker, random, humanRepository, reviserRepository, studentRepository, teacherRepository);
        CreateSchema createSchemaDb = new CreateSchema(hikariDataBase);
        CreateSchema createSchemaTables = new CreateSchema(hikariTables);
        MariaDbDeploy deployMaria = new MariaDbDeploy(configDataBase);


        createDirectoryIfNotExists(configDataBase.getDirectory());

        deployMaria.startEmbeddedMariaDB();
        createSchemaDb.createDataBase();
        createSchemaTables.createTables();
        userService.populateStorage(10);
        userService.countAllUsers();
        userService.printStorageAllUsers();
        deployMaria.stopEmbeddedMariaDB();
    }
}
