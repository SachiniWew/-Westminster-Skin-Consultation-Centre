package GUI.Comparator;

import Classes.Consultation;

import java.util.Comparator;

public class AppointmentIDComparator implements Comparator<Consultation> {
    @Override
    public int compare(Consultation o1, Consultation o2) {
        String s1 = o1.getAppointmentID()+" "+o1.getAppointmentID();
        String s2 = o2.getAppointmentID()+" "+o2.getAppointmentID();
        if (s1.equals(s2))
            return 0;
        else if (s1.compareTo(s2) > 0)
            return 1;
        else
            return -1;
    }

}
