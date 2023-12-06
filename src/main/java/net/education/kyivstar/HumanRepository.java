package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Human;

import java.util.List;
import java.util.stream.Collectors;


public class HumanRepository {
    private final Storage data;

    public HumanRepository(Storage storage) {
        this.data = storage;
    }

    public List<Human> extractAllUsers() {
        return data.selectAll();
    }

    public void removeUserBySurname(String surname) {
        List<Human> list = data.selectAll()
                .stream()
                .filter(s -> s.getSurname()
                        .equals(surname))
                .collect(Collectors.toList());
        data.deleteBySurname(list);
    }
}
