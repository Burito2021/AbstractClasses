package net.education.kyivstar.userServices;

import com.github.javafaker.Faker;
import net.education.kyivstar.config.*;
import net.education.kyivstar.repositories.HumanRepository;
import net.education.kyivstar.repositories.ReviserRepository;
import net.education.kyivstar.repositories.StudentRepository;
import net.education.kyivstar.repositories.TeacherRepository;
import net.education.kyivstar.services.user.UserService;

import java.util.Random;

public class TestContext {

    private Faker faker;
    private Random random;
    private PasswordEncryptor passwordEncryptor;
    private ConfigDataBase configDataBase;
    private EmbeddedMariaDbLifeCycle embeddedMariaDbLifeCycle;
    private SchemaAndTableCreator createSchema;
    private HikariConnectionManager hikariConnectManager;
    private HumanRepository humanRepository;
    private ReviserRepository reviserRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private UserService userService;

    public TestContext() {
        this.faker = new Faker();
        this.random = new Random();
        this.passwordEncryptor = new PasswordEncryptor();
        this.configDataBase = new ConfigDataBase(passwordEncryptor);
        this.embeddedMariaDbLifeCycle = new EmbeddedMariaDbLifeCycle(configDataBase);
        this.createSchema = new SchemaAndTableCreator(embeddedMariaDbLifeCycle);
        createSchema.createDataBase();
        this.hikariConnectManager = new HikariConnectionManager(configDataBase);
        this.humanRepository = new HumanRepository(hikariConnectManager);
        this.reviserRepository = new ReviserRepository(hikariConnectManager);
        this.studentRepository = new StudentRepository(hikariConnectManager);
        this.teacherRepository = new TeacherRepository(hikariConnectManager);
        this.userService = new UserService(faker, random, humanRepository, reviserRepository, studentRepository, teacherRepository);
    }

    public Faker getFaker() {
        return faker;
    }

    public Random getRandom() {
        return random;
    }

    public PasswordEncryptor getPasswordEncryptor() {
        return passwordEncryptor;
    }

    public ConfigDataBase getConfigDataBase() {
        return configDataBase;
    }

    public EmbeddedMariaDbLifeCycle getEmbeddedMariaDbLifeCycle() {
        return embeddedMariaDbLifeCycle;
    }

    public SchemaAndTableCreator getCreateSchema() {
        return createSchema;
    }

    public HikariConnectionManager getHikariConnectManager() {
        return hikariConnectManager;
    }

    public HumanRepository getHumanRepository() {
        return humanRepository;
    }

    public ReviserRepository getReviserRepository() {
        return reviserRepository;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public TeacherRepository getTeacherRepository() {
        return teacherRepository;
    }

    public UserService getUserService() {
        return userService;
    }

    @Override
    public String toString() {
        return "TestContext{" +
                "faker=" + faker +
                ", random=" + random +
                ", passwordEncryptor=" + passwordEncryptor +
                ", configDataBase=" + configDataBase +
                ", embeddedMariaDbLifeCycle=" + embeddedMariaDbLifeCycle +
                ", createSchema=" + createSchema +
                ", hikariConnectManager=" + hikariConnectManager +
                ", humanRepository=" + humanRepository +
                ", reviserRepository=" + reviserRepository +
                ", studentRepository=" + studentRepository +
                ", teacherRepository=" + teacherRepository +
                ", userService=" + userService +
                '}';
    }
}



