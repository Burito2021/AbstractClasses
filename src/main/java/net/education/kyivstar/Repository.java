package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class Repository {
    private final Storage data;
    private Logger logger = LoggerFactory.getLogger(Service.class);

    public Repository(Storage storage) {
        this.data = storage;
    }

    //  public void add(); remove(); get(), getAll(); update();
    public void add(Human human) {
        data.getStorage().add(human);
    }

    public void removeAll(){
        data.getStorage().removeAll(getAll());

    }

    public void removeBySurname(String surname) {
        var size = data.getStorage().size();
        for (int x = 0; x < size; x++) {
            if (data.getStorage().get(x).getSurname().equals(surname))
                data.getStorage().remove(x);
        }
        logger.debug("Storage size " + data.getStorage().size());
    }

    public List<Human> getAll() {
        logger.debug("Storage content " + data.getStorage());
        return data.getStorage();
    }

    public void updateBySurname(String surname, Human humanToReplaceWith) {
        var size = data.getStorage().size();
        for (int x = 0; x < size; x++) {
            if (data.getStorage().get(x).getSurname().equals(surname))
                data.getStorage().set(x, humanToReplaceWith);
            logger.debug("Record replaced " + data.getStorage().get(x));
        }
        logger.debug("Storage size " + data.getStorage().size());
    }
}
