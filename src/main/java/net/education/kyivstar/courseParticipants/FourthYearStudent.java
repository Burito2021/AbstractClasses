package net.education.kyivstar.courseParticipants;

import net.education.kyivstar.courseModel.Course;
import net.education.kyivstar.subjects.Paragraph;
import net.education.kyivstar.subjects.Subject;

public  class FourthYearStudent extends BaseStudent {
    public FourthYearStudent(String name, String surname, int age) {
        super(name, surname, age);
    }

    @Override
    public String getYearOfStudy() {
        return "fourth year of study";
    }
    @Override
    public void doCourse(Course course) {
        System.out.println("do course of "+course);
    }

    @Override
    public void study(Subject subject) {
        System.out.println("Study "+subject);
    }

    @Override
    public void learn(Paragraph paragraph) {
        System.out.println("Study "+paragraph);
    }
}
