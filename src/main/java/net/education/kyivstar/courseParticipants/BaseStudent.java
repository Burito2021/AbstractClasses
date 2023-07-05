package net.education.kyivstar.courseParticipants;

import net.education.kyivstar.courseModel.Course;
import net.education.kyivstar.subjects.Paragraph;
import net.education.kyivstar.subjects.Subject;

public abstract class BaseStudent {
    private String name;
    private String surname;
    private int age;
    public BaseStudent(String name, String surname, int age){
        this.name =name;
        this.surname =surname;
        this.age =age;
    }

    public abstract String getYearOfStudy();

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public BaseStudent setName(String name) {
        this.name = name;
        return this;
    }

    public BaseStudent setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public BaseStudent setAge(int age) {
        this.age = age;
        return this;
    }
    public abstract void doCourse(Course course);
    public abstract void study(Subject subject);
    public abstract void learn(Paragraph paragraph);

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
