package net.education.kyivstar.premises;

import net.education.kyivstar.courseParticipants.BaseStudent;
import net.education.kyivstar.courseParticipants.BaseTeacher;

public class Canteen extends Gate{


    @Override
    public void enter(BaseTeacher teacher) {

    }

    @Override
    public void enter(BaseStudent student) {
        System.out.println(student+" is entering the canteen");
    }

    @Override
    public void exit(BaseStudent student) {
        System.out.println(student+" is entering the canteen");
    }
}
