package net.education.kyivstar.courseParticipants;

import net.education.kyivstar.courseModel.Course;

public class Reviser extends BaseReviser{
    public Reviser(String name, String surname, int age) {
        super(name, surname, age);
    }

    @Override
    public void revise(Course course) {
        System.out.println("Revise "+course);
    }
}
