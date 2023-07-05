package net.education.kyivstar.premises;

import net.education.kyivstar.courseParticipants.BaseReviser;
import net.education.kyivstar.courseParticipants.BaseStudent;
import net.education.kyivstar.courseParticipants.BaseTeacher;

public abstract class Gate {

    public abstract void enter(BaseTeacher teacher);

    public abstract void enter(BaseReviser reviser);

    public abstract void enter(BaseStudent student);

    public abstract void exit(BaseStudent student);

    public abstract void exit(BaseTeacher teacher);

    public abstract void exit(BaseReviser reviser);
}
