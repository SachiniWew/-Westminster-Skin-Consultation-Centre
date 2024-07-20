package GUI.Comparator;

import Classes.Consultation;

import java.util.Comparator;

public class AlphabeticComparatorUser implements Comparator<Consultation> {
    @Override
    public int compare(Consultation o1, Consultation o2) {
        String s1 = o1.getDoctor().getName()+" "+o1.getDoctor().getSurname();
        String s2 = o2.getDoctor().getName()+" "+o2.getDoctor().getSurname();
        if (s1.equals(s2))
            return 0;
        else if (s1.compareTo(s2) > 0)
            return 1;
        else
            return -1;
    }
}
