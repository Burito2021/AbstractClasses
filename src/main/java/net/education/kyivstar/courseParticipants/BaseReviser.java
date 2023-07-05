package net.education.kyivstar.courseParticipants;

import net.education.kyivstar.courseModel.Course;
import net.education.kyivstar.subjects.Subject;

public abstract class BaseReviser {
    private String name;
    private String surname;
    private int age;
    public BaseReviser(String name, String surname, int age){
        this.name =name;
        this.surname =surname;
        this.age =age;
    }

    public abstract void revise(Course course);

    @Override
    public String toString() {
        return "Reviser{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
