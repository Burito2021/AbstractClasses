package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
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

    public List<Student> collectStudentByName(String name) {
        var result = extractAllStudentsStream()
                .filter(s -> s.getName().equals(name))
                .collect(Collectors.toList());
        logger.info("Result of getByName " + result);
        return result;
    }

    public List<Student> collectStudentBySurname(String surname) {
        var result = extractAllStudentsStream()
                .filter(s -> s.getSurname().equals(surname))
                .collect(Collectors.toList());
        logger.info("Result of getBySurname " + result);
        return result;
    }

   public void removeStudentBySurname(String surname) {
       IntStream.range(0, data.selectCount())
               .filter(n -> data.selectAll()
                       .get(n) instanceof Student)
               .filter(n -> data.selectAll()
                       .get(n)
                       .getSurname()
                       .equals(surname))
               .forEach(data::delete);
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
    }

