package net.education.kyivstar;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

import static net.education.kyivstar.UserType.*;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Storage storage = new Storage();
        ReviserRepository reviserRepository = new ReviserRepository(storage);
        StudentRepository studentRepository = new StudentRepository(storage);
        TeacherRepository teacherRepository = new TeacherRepository(storage);
        UserService userService = new UserService(new Faker(), new Random(), reviserRepository, studentRepository, teacherRepository);

        userService.populateStorage(1);
        userService.createUserAndStore(REVISER, "d1", "lds", 16);
        userService.createUserAndStore(REVISER, "d1", "lds6", 16);
        userService.createUserAndStore(REVISER, "d2", "lds1", 16);
        userService.createUserAndStore(STUDENT, "d2", "lds2", 16);
        userService.createUserAndStore(TEACHER, "d2", "lds3", 16);
        userService.createUserAndStore(REVISER, "d3", "lulu", 18);
        System.out.println("ALL");
        userService.printStorageAllUsers();
        userService.replaceUser("lds6",REVISER,"d4","replaced",20);
        System.out.println("!!!");
        userService.printStorageAllUsers();

    }
}
