package GUI.Table;

import Classes.Doctor;
import Classes.SkinConsultationManager;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class DoctorTableModel  extends AbstractTableModel {

    private final String[] columnNames = {"ID", "Name", "Medical Licence", "Specialisation", "Ward No"};
    private ArrayList<Doctor> myList = new ArrayList<>();

    public DoctorTableModel() {
        for (int i = 0; i < SkinConsultationManager.doctorList.length; i++) {
            if (SkinConsultationManager.doctorList[i]!=null){
                myList.add(SkinConsultationManager.doctorList[i]);
            }
        }
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return myList.size();
    }

    public Object getValueAt(int row, int col) {
        Object temp = null;
        if (col == 0) {
            temp = String.format("%02d",myList.get(row).getID());
        }
        else if (col == 1) {
            temp = " "+myList.get(row).getName()+" "+myList.get(row).getSurname();
        }
        else if (col == 2) {
            temp = myList.get(row).getMedicalLicenceNumber();
        }
        else if (col == 3) {
            temp = myList.get(row).getSpecialisation().toString();
        }
        else if (col == 4) {
            temp = String.format("%02d",myList.get(row).getSpecialisation().getNumberOfSpecialisation());
        }
        return temp;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class getColumnClass(int col) {
        return String.class;
    }

    public ArrayList<Doctor> getMyList() {
        return myList;
    }

    public void setMyList(ArrayList<Doctor> myList) {
        this.myList = myList;
    }
}
