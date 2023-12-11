package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import net.education.kyivstar.courseParticipants.Student;
import net.education.kyivstar.courseParticipants.Teacher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
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

    public List<Teacher> selectAllTeachers() {
        return storage
                .stream()
                .filter(obj -> obj instanceof Teacher)
                .map(human -> (Teacher) human)
                .collect(Collectors.toList());
    }

    public int selectCount() {
        return storage.size();
    }

    public void insert(Human human) {
        storage.add(human);
    }

    public void update(List<Human> h, Human replacement) {
        for (Human human:h) {
            Collections.replaceAll(storage,human,replacement);
        }
    }

    public void deleteAll() {
        storage.clear();
    }

    public void deleteBySurname(List<Human> list) {
        storage.removeAll(list);
    }
}
