package net.education.kyivstar.premises;

import net.education.kyivstar.courseParticipants.BaseReviser;
import net.education.kyivstar.courseParticipants.BaseStudent;
import net.education.kyivstar.courseParticipants.BaseTeacher;
import net.education.kyivstar.subjects.Subject;
import net.education.kyivstar.subjects.Paragraph;

public class EnglishClassRoom extends ClassRoom implements Paragraph {




    @Override
    public void studySubject(Subject name) {

    }

    @Override
    public void giveClasses(BaseTeacher teacher) {

    }

    @Override
    public void sitInClassRoomInLecture(BaseStudent student) {

    }

    @Override
    public void watchStudentsAttendClasses(BaseReviser reviser) {

    }

    @Override
    public void enter(BaseTeacher teacher) {

    }

    @Override
    public void enter(BaseReviser reviser) {

    }

    @Override
    public void enter(BaseStudent student) {

    }

    @Override
    public void exit(BaseStudent student) {

    }

    @Override
    public void exit(BaseTeacher teacher) {

    }

    @Override
    public void exit(BaseReviser reviser) {

    }
}
