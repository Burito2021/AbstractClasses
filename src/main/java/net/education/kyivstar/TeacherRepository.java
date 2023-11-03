package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TeacherRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final Storage data;

    public TeacherRepository(Storage storage) {
        this.data = storage;
    }

    public void addTeacher(Teacher teacher) {
        data.insert(teacher);
    }

    public List<Teacher> extractAllTeachers(){
        return data.selectAll()
                .stream()
                .filter(obj->obj instanceof Teacher)
                .map(human -> (Teacher) human)
                .collect(Collectors.toList());
    }

    public Stream<Teacher> extractAllTeachersStream(){
        return data.selectAll()
                .stream()
                .filter(obj->obj instanceof Teacher)
                .map(human -> (Teacher) human);
    }

    public void updateTeacherBySurname(String surname, Teacher humanToReplaceWith) {
        IntStream.range(0, data.selectCount())
                .filter(n -> data.selectAll()
                        .get(n)
                        instanceof Teacher)
                .filter(n -> data.selectAll()
                        .get(n)
                        .getSurname()
                        .equals(surname))
                .forEach(b -> data.update(b, humanToReplaceWith));
    }

    public void removeTeacherBySurname(String surname) {
        List <Human> list = data.selectAllTeachers()
                .stream()
                .filter(s->s.getSurname()
                        .equals(surname))
                .collect(Collectors.toList());
        data.deleteBySurname(list);
    }
}
