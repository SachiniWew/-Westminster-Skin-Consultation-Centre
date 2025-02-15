package GUI.Comparator;

import Classes.Doctor;

import java.util.Comparator;

public class ComparatorDoctorID implements Comparator<Doctor> {
    @Override
    public int compare(Doctor o1, Doctor o2) {
        int s1 = o1.getID();
        int s2 = o2.getID();
        return Integer.compare(s1, s2);
    }

}
