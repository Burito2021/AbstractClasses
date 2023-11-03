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

    public Stream<Student> extractAllStudentsStream() {
        return data.selectAll()
                .stream()
                .filter(obj -> obj instanceof Student)
                .map(human -> (Student) human);
    }

    public void updateStudentBySurname(String surname, Student humanToReplaceWith) {
            IntStream.range(0, data.selectCount())
                    .filter(n -> data.selectAll()
                            .get(n)
                            instanceof Student)
                    .filter(n -> data.selectAll()
                            .get(n)
                            .getSurname()
                            .equals(surname))
                    .forEach(b -> data.update(b, humanToReplaceWith));
        }

    public void removeStudentBySurname(String surname) {
        List <Human> list = data.selectAllStudents()
                .stream()
                .filter(s->s.getSurname()
                        .equals(surname))
                .collect(Collectors.toList());
        data.deleteBySurname(list);
    }
    }

