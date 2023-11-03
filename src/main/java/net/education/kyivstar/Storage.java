package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import net.education.kyivstar.courseParticipants.Student;
import net.education.kyivstar.courseParticipants.Teacher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Storage {

    private final List<Human> storage;

    public Storage() {
        this.storage = new ArrayList<>();
    }

    public List<Human> selectAll() {
        return storage;
    }

    public List<Reviser> selectAllRevisers() {
        return storage.stream()
                .filter(obj -> obj instanceof Reviser)
                .map(human -> (Reviser) human)
                .collect(Collectors.toList());
    }

    public List<Student> selectAllStudents() {
        return storage
                .stream()
                .filter(obj -> obj instanceof Student)
                .map(human -> (Student) human)
                .collect(Collectors.toList());
    }

    public List<Teacher> selectAllTeacher() {
        return storage
                .stream()
                .filter(obj -> obj instanceof Teacher)
                .map(human -> (Teacher) human)
                .collect(Collectors.toList());
    }

    public List<Human> selectBySurname(String surname) {
        List<Human> listBySurname = storage
                .stream()
                .filter(human -> human.getSurname()
                        .equals(surname)).collect(Collectors.toList());
        return listBySurname;
    }

    public List<Human> selectByName(String name) {
        return storage
                .stream()
                .filter(human -> human.getName()
                        .equals(name))
                .collect(Collectors.toList());
    }

    public Human selectById(int index) {
        return storage.get(index);
    }

    public int selectCount() {
        return storage.size();
    }

    public void insert(Human human) {
        storage.add(human);
    }

    public void update(int index, Human replacement) {
        storage.set(index, replacement);
    }

    public void deleteAll(List<Human> list) {
        storage.removeAll(list);
    }

    public void deleteItemByIndex(int index) {
        storage.remove(index);
    }


    public void delete(int index) {
        storage.remove(index);
    }

    public void deleteByObject(Human human) {
        storage.remove(human);
    }
}
