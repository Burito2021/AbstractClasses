package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StudentRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final Storage data;

    public StudentRepository(Storage storage) {
        this.data = storage;
    }

    public void addStudent(Student student) {
        data.insert(student);
    }

    public List<Student> extractAllStudents() {
        return data.selectAllStudents();
    }

    public void updateStudentBySurname(String surname, Student humanToReplaceWith) {
        List<Human> humans = data.selectAllStudents()
                .stream()
                .filter(human -> human.getSurname().equals(surname)).collect(Collectors.toList());
        data.update(humans,humanToReplaceWith);
    }

    public void removeStudentBySurname(String surname) {
        List<Human> list = data.selectAllStudents()
                .stream()
                .filter(s -> s.getSurname()
                        .equals(surname))
                .collect(Collectors.toList());
        data.deleteBySurname(list);
    }
}

