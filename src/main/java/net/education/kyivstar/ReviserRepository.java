package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;
import net.education.kyivstar.courseParticipants.Reviser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

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


    public void updateReviserBySurname(String surname, Reviser humanToReplaceWith) {
       List<Human> humans = data.selectAllRevisers()
                .stream()
                .filter(human -> human.getSurname().equals(surname)).collect(Collectors.toList());
       data.update(humans,humanToReplaceWith);
    }

    public void removeAll() {
        data.deleteAll();
    }
}
