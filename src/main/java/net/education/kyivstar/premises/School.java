package net.education.kyivstar.premises;

import net.education.kyivstar.courseParticipants.BaseReviser;
import net.education.kyivstar.courseParticipants.BaseStudent;
import net.education.kyivstar.courseParticipants.BaseTeacher;

public abstract class School extends Gate{

    public abstract void giveClasses(BaseTeacher teacher);
    public abstract void sitInClassRoomInLecture(BaseStudent student);
    public abstract void watchStudentsAttendClasses(BaseReviser reviser);
}
