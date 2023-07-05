package net.education.kyivstar.courseModel;

import net.education.kyivstar.courseParticipants.BaseReviser;
import net.education.kyivstar.courseParticipants.BaseStudent;
import net.education.kyivstar.subjects.Subject;

public class InPersonCourse extends Course implements Subject {
    private String courseName = getClass().getName().substring(getClass().getName().indexOf("l.")+2);
    private int courseCode = 2;
    private String courseDuration ="6 weeks";
    private int enrollmentCapacity =30;


    @Override
    public String getCourseName() {
        return courseName;
    }

    @Override
    public int getCourseCode() {
        return courseCode;
    }

    @Override
    public String getCourseDuration() {
        return courseDuration;
    }

    @Override
    public int getEnrollmentCapacity() {
        return enrollmentCapacity;
    }

    @Override
    public void enrollInCourse(BaseStudent student) {
        System.out.println( student + " enrolled on HybridCourse");
    }

    @Override
    public void cancelEnrollmentInCourse(BaseReviser reviser) {
        System.out.println(reviser + " is cancelled on HybridCourse");
    }

    @Override
    public void viewCourseSchedule(String dayOfWeek) {
        switch (dayOfWeek){
            case "Monday":
                System.out.println("Monday");
                System.out.println("1."+Subject.Chemistry);
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
