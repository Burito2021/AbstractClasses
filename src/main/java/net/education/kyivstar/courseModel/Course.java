package net.education.kyivstar.courseModel;

import net.education.kyivstar.courseParticipants.BaseReviser;
import net.education.kyivstar.courseParticipants.BaseStudent;

public abstract class Course {


    public abstract void enrollInCourse(BaseStudent student);

    public abstract void cancelEnrollmentInCourse(BaseReviser reviser);

    public abstract void viewCourseSchedule(String dayOfWeek);


    public abstract String getCourseName();

    public abstract int getCourseCode();

    public abstract String getCourseDuration();

    public abstract int getEnrollmentCapacity();


    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + getCourseName() + '\'' +
                ", courseCode=" + getCourseCode() +
                ", courseDuration=" + getCourseDuration() +
                ", enrollmentCapacity=" + getEnrollmentCapacity() +
                '}';
    }
}
