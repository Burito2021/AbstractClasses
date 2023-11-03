package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReviserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final Storage data;

    public ReviserRepository(Storage storage) {
        this.data = storage;
    }

    public void addReviser(Reviser reviser) {
        data.insert(reviser);
    }

    public List<Reviser> extractAllRevisers() {
        return data.selectAllRevisers();
    }

    public Stream<Reviser> extractAllRevisersStream() {
        return data.selectAllRevisers().stream();
    }

    public List<Human> extractAllUsers(){
        return data.selectAll();
    }

    public void removeUserBySurname(String surname) {
        List <Human> list = data.selectAll()
                .stream()
                .filter(s->s.getSurname()
                        .equals(surname))
                .collect(Collectors.toList());
        data.deleteBySurname(list);
    }

    public void updateReviserBySurname(String surname, Reviser humanToReplaceWith) {

        IntStream.range(0, data.selectCount())
                .filter(n -> data.selectAll()
                        .get(n) instanceof Reviser)
                .filter(n -> data.selectAll()
                        .get(n)
                        .getSurname()
                        .equals(surname))
                .forEach(b -> data.update(b, humanToReplaceWith));
    }

    public void removeAll(){
       data.deleteAll();
    }
}
