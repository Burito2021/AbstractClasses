package net.education.kyivstar.premises;

import net.education.kyivstar.courseParticipants.BaseReviser;
import net.education.kyivstar.courseParticipants.BaseStudent;
import net.education.kyivstar.courseParticipants.BaseTeacher;

public class TeacherRoom extends Gate {

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
