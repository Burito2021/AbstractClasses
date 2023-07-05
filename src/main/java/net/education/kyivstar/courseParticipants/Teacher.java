package net.education.kyivstar.courseParticipants;

public class Teacher extends BaseTeacher{
    public Teacher(String name, String surname, int age) {
        super(name, surname, age);
    }

    @Override
    public void teach(BaseStudent student) {
        System.out.println("Teach "+student);
    }
}
