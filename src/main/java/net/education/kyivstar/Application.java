package net.education.kyivstar;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static net.education.kyivstar.UserType.REVISER;
import static net.education.kyivstar.UserType.STUDENT;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Storage storage = new Storage();
        ReviserRepository reviserRepository = new ReviserRepository(storage);
        StudentRepository studentRepository = new StudentRepository(storage);
        TeacherRepository teacherRepository = new TeacherRepository(storage);
        UserService userService = new UserService(new Faker(), new Random(), reviserRepository, studentRepository, teacherRepository);


    /*  userService.populateStorage(1);
        userService.printStorageAllUsers();
       userService.createUserAndStore(REVISER, "ls", "ls", 3);*/

        userService.createUserAndStore(REVISER, "d", "lsd", 16);
        userService.createUserAndStore(REVISER, "d", "ls", 16);
        userService.createUserAndStore(STUDENT, "d", "lds", 17);
        userService.printStorageAllUsers();
        userService.removeUser("ls");
        userService.printStorageAllUsers();
       // userService.removeAll();
        userService.printStorageAllUsers();
        //   userService.printStorageAllUsers();
        //  System.out.println(userService.collectAllUsers());
        //userService.removeUser("ls");
        // System.out.println(userService.collectAllUsers());


        //userService.printReviserStorage();
        //   userService.createUserAndStore(TEACHER,"alex","1221",3);
       /* userService.repository.add(new Teacher("djorge","fo",3));
        userService.repository.add(new Teacher("alex","1221",3));
        userService.repository.add(new Teacher("Vasya","Vanid",3));

     userService.repository.updateBySurname("Vanid",new Teacher("Alex","Bumer",39));

        userService.repository.removeBySurnameStream("Bumer");

    /*  populateService.populateStorage(10);
         populateService.test();
        userService.createUserAndStore(STUDENT, "Alex", "Burmistrenko", 30);
        userService.createUserAndStore(TEACHER, "Maxim", "Dzhezhelo", 30);

        logger.info("Get by name method: " + userService.repository.getByName("Alex"));

        logger.info("Get by surname method: " + userService.repository.getByName("Dzhezhelo"));
        logger.info("List " + userService.printStorage());
        logger.info("Collect by age method: " + userService.repository.collectByAge(30));
        userService.repository.removeBySurnameStream("Dzhezhelo");
        userService.printStorage();

        logger.info("Burmistrenko", new Student("Burito", "Mohito", 29));

        userService.printStorage();*/
    }
}
