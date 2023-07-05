package net.education.kyivstar;

import net.education.kyivstar.courseModel.*;
import net.education.kyivstar.courseParticipants.FirstYearStudent;
import net.education.kyivstar.premises.Canteen;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Course hybridCourse = new HybridCourse();
        Course inPersonCourse = new InPersonCourse();
        Course onlineCourse = new OnlineCourse();
        Course mixedCourse = new MixedCourse();
        Canteen canteen = new Canteen();
        FirstYearStudent firstYearStudent = new FirstYearStudent("Alex","Vinir",21);
        canteen.enter(firstYearStudent);

        hybridCourse.viewCourseSchedule("Tuesday");
        inPersonCourse.viewCourseSchedule("Tuesday");


    }
}
