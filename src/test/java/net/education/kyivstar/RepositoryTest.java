package net.education.kyivstar;

import net.education.kyivstar.courseParticipants.Reviser;
import net.education.kyivstar.courseParticipants.Student;
import org.junit.jupiter.api.Test;

import static net.education.kyivstar.Service.UserType.TEACHER;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RepositoryTest {
    Repository repository = new Repository(new Storage());

    @Test
    void addTest() {
        repository.add(new Reviser("NameReviser", "SurnameReviser", 30));

        final var actual = repository.getAll();
        assertNotNull(actual);
        assertFalse(actual.isEmpty());

        assertEquals("NameReviser", actual.get(0).getName());
        assertEquals("SurnameReviser", actual.get(0).getSurname());
        assertEquals(30, actual.get(0).getAge());

        repository.removeAll();
    }

    @Test
    void removeBySurnameTest() {
        repository.add(new Reviser("NameReviser", "SurnameReviser", 30));

        final var beforeRemove = repository.getAll();
        assertNotNull(beforeRemove);
        assertFalse(beforeRemove.isEmpty());

        repository.removeBySurname("SurnameReviser");
        final var afterRemove = repository.getAll();

        assertTrue(afterRemove.isEmpty());

        repository.removeAll();
    }

    @Test
    void updateBySurname() {
        repository.add(new Reviser("NameReviser", "SurnameReviser", 30));

        final var beforeUpdate = repository.getAll();
        assertNotNull(beforeUpdate);
        assertFalse(beforeUpdate.isEmpty());

        repository.updateBySurname("SurnameReviser",new Student("Student", "SurnameStudent", 17));
        repository.removeBySurname("SurnameReviser");
        final var afterUpdate = repository.getAll().get(0);

        assertEquals("Student",afterUpdate.getName());
        assertEquals("SurnameStudent",afterUpdate.getSurname());
        assertEquals(17,afterUpdate.getAge());

        repository.removeAll();
    }

    @Test
    void updateBySudrname() {
        repository.add(new Reviser("NameReviser", "SurnameReviser", 30));

        final var actual = repository.getAll();
        assertNotNull(actual);
        assertFalse(actual.isEmpty());

        assertEquals("NameReviser",actual.get(0).getName());
        assertEquals("SurnameReviser",actual.get(0).getSurname());
        assertEquals(30,actual.get(0).getAge());

       repository.removeAll();
    }
}