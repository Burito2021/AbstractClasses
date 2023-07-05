package net.education.kyivstar;

import com.github.javafaker.Faker;

import java.util.Random;

import static net.education.kyivstar.Service.UserType.*;
import static net.education.kyivstar.Storage.*;

public class Application {

    public static void main(String[] args) {
        Faker faker = new Faker();
        Storage storage = new Storage();
        Random random = new Random();

        Service service = new Service(faker,storage,random);

        service.populateStorage(1000);

        service.createUserAndStore(STUDENT,"Alex","Burmistrenko",30);
        service.createUserAndStore(TEACHER,"Maxim","Dzhezhelo",30);

        System.out.println("Get by name method: " + service.getByName("Alex"));

        System.out.println("Get by surname method: " + service.getBySurname("Dzhezhelo"));

        System.out.println("Collect by age method: " + service.collectByAge(30));

        service.listsOfNames(service.collectByAge(30));

        service.printStorage();
    }
}
