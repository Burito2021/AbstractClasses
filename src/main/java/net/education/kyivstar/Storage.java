package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static List<Human> storage;

    public Storage() {
        this.storage = new ArrayList<>();
    }

    public static void add(Human human) {

        storage.add(human);
    }

    public static List<Human> collectByAge(int age) {

        List<Human> result = new ArrayList<>();
        for (Human human : storage) {
            if (human.getAge() == age) {
                result.add(human);
            }
        }
        return result;
    }

    public static List<Human> getByName(String name) {

        List<Human> result = new ArrayList<>();
        for (Human human : storage) {
            if (human.getName() == name) {
                result.add(human);
            }
        }
        return result;
    }

    public static List<Human> getBySurname(String surname) {

        List<Human> result = new ArrayList<>();
        for (Human human : storage) {
            if (human.getSurname() == surname) {
                result.add(human);
            }
        }
        return result;
    }

    public static void printStorage() {

        System.out.println("What is in the storage printStorage method: " + storage);
    }

    public static List<String> listsOfNames(List<Human> list) {

        List<String> listOfNames = new ArrayList<>();

        for (Human item : list) {
            listOfNames.add(item.getName());
        }
        System.out.println("list of names by age: " + listOfNames);
        return listOfNames;
    }
}
