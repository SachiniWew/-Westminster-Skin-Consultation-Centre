package GUI.Table;

import Classes.Consultation;
import Classes.Doctor;
import Classes.Patient;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Appointment ID","Date","Time", "Doctor", "Patient", "Cost","Ward Num"};
    private ArrayList<Consultation> myList = new ArrayList<>();

    public UserTableModel(ArrayList<Consultation> list) throws ParseException {
        myList.addAll(list);
    }

    public Object getValueAt(int row, int col) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Object temp = null;
        if (col == 0) {
            temp = myList.get(row).getAppointmentID();
        }
        else if (col == 1) {
            temp = dateFormat.format(myList.get(row).getDate());
        }
        else if (col == 2){
            temp = timeFormat.format(myList.get(row).getDate());
        }
        else if (col == 3) {
            Doctor d = myList.get(row).getDoctor();
            temp = " "+d.getName()+" "+d.getSurname();
        }
        else if (col == 4) {
            Patient p = myList.get(row).getPatient();
            temp = " "+p.getName()+" "+p.getSurname();
        }
        else if (col == 5) {
            temp = String.format("£%.2f", myList.get(row).getCost());
        }
        else if (col == 6) {
            temp = String.format("%02d",myList.get(row).getDoctor()
                    .getSpecialisation().getNumberOfSpecialisation());
        }
        return temp;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class getColumnClass(int col) {
        if (col == 0) {
            return Integer.class;
        }
        else return String.class;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return myList.size();
    }

    public void setMyList(ArrayList<Consultation> myList) {
        this.myList = myList;
    }
}
