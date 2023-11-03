package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import net.education.kyivstar.courseParticipants.Student;
import net.education.kyivstar.courseParticipants.Teacher;
import java.util.ArrayList;
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

    public List<Human> selectAllRevisersHuman() {
        return storage.stream()
                .filter(obj -> obj instanceof Reviser)
                .collect(Collectors.toList());
    }

    public List<Student> selectAllStudents() {
        return storage
                .stream()
                .filter(obj -> obj instanceof Student)
                .map(human -> (Student) human)
                .collect(Collectors.toList());
    }

    public List<Teacher> selectAllTeachers() {
        return storage
                .stream()
                .filter(obj -> obj instanceof Teacher)
                .map(human -> (Teacher) human)
                .collect(Collectors.toList());
    }

    public List<Human> selectBySurname(String surname) {
        return  storage
                .stream()
                .filter(human -> human.getSurname()
                        .equals(surname)).collect(Collectors.toList());
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

    public void deleteAll() {
        storage.clear();
    }

    public void deleteBySurname(List<Human> list) {
        storage.removeAll(list);
    }
}
