package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
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

    public List<Reviser> collectReviserByName(String name) {
        var result = data.selectByName(name)
                .stream()
                .filter(obj -> obj instanceof Reviser)
                .map(human -> (Reviser) human)
                .collect(Collectors.toList());
        logger.info("Result of getByName " + result);
        return result;
    }

    public List<Reviser> collectReviserBySurname(String surname) {
        var result = data.selectBySurname(surname)
                .stream()
                .filter(obj -> obj instanceof Reviser)
                .map(human -> (Reviser) human)
                .collect(Collectors.toList());
        logger.info("Result of getBySurname " + result);
        return result;
    }

    public void removeReviserBySurname(String surname) {
        List<Human> list = data.selectAll()
                .stream()
                .filter(s->s.getSurname()
                        .equals(surname))
                .collect(Collectors.toList());
        data.deleteAll(list);
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
        List<Human> list = data.selectAll()
                .stream()
                .collect(Collectors.toList());
       data.deleteAll(list);
    }
}
