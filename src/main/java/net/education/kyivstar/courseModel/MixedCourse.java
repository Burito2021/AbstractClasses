package net.education.kyivstar.courseModel;

import net.education.kyivstar.courseParticipants.BaseStudent;
import net.education.kyivstar.subjects.Subject;

public class MixedCourse extends HybridCourse {

    @Override
    public String getCourseName() {
        return super.getCourseName();
    }

    @Override
    public int getEnrollmentCapacity() {
        return super.getEnrollmentCapacity();
    }

    @Override
    public void enrollInCourse(BaseStudent student) {
        super.enrollInCourse(student);
    }

    @Override
    public void viewCourseSchedule(String dayOfWeek) {
        switch (dayOfWeek){
            case "Monday":
                System.out.println("Monday");
                System.out.println("1."+ Subject.Chemistry);
                System.out.println("2."+Subject.EnglishLanguage);
                System.out.println("3."+Subject.EnglishLiterature);
                System.out.println("4."+Subject.Chemistry);
                System.out.println("5."+Subject.Math);
                break;
            case "Tuesday":
                System.out.println("Tuesday");
                System.out.println("1."+Subject.PE);
                System.out.println("2."+Subject.Painting);
                System.out.println("3."+Subject.Geography);
                System.out.println("4."+Subject.Geometry);
                System.out.println("5."+Subject.UkrainianLanguage);
                break;
            case "Wednesday":
                System.out.println("Wednesday");
                System.out.println("1."+Subject.PE);
                System.out.println("2."+Subject.Painting);
                System.out.println("3."+Subject.Geography);
                System.out.println("4."+Subject.Geometry);
                System.out.println("5."+Subject.UkrainianLanguage);
                break;
            case "Thursday":
                System.out.println("Thursday");
                System.out.println("1."+Subject.PE);
                System.out.println("2."+Subject.Painting);
                System.out.println("3."+Subject.Geography);
                System.out.println("4."+Subject.Geometry);
                System.out.println("5."+Subject.UkrainianLanguage);

                break;
            case "Friday":
                System.out.println("1."+Subject.Chemistry);
                System.out.println("2."+Subject.EnglishLanguage);
                System.out.println("3."+Subject.EnglishLiterature);
                System.out.println("4."+Subject.Chemistry);
                System.out.println("5."+Subject.Math);
                break;
        }
    }

}
