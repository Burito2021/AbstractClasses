package net.education.kyivstar.courseParticipants;

import net.education.kyivstar.courseModel.Course;

public abstract class BaseTeacher {
    private String name;
    private String surname;
    private int age;

    public BaseTeacher(String name, String surname, int age){
        this.name =name;
        this.surname =surname;
        this.age =age;
    }
    public abstract void teach(BaseStudent student);

    @Override
    public String toString() {
        return "BaseTeacher{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
