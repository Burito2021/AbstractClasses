package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    protected final List<Human> storage;

    protected Storage() {
        this.storage = new ArrayList<>();
    }

    public List<Human> getStorage() {
        return storage;
    }
}
