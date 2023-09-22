package net.education.kyivstar;

import static net.education.kyivstar.Service.UserType.*;
import static net.education.kyivstar.Storage.*;

public class Application {

    public static void main(String[] args) {
        Service service = new Service();

        service.populateStorage(1000);

        service.createUserAndStore(STUDENT,"Alex","Burmistrenko",30);
        service.createUserAndStore(TEACHER,"Maxim","Dzhezhelo",30);

        System.out.println("Get by name method: " + getByName("Alex"));

        System.out.println("Get by surname method: " + getBySurname("Dzhezhelo"));

        System.out.println("Collect by age method: " + collectByAge(30));

        listsOfNames(collectByAge(30));

        printStorage();
    }
}
