package GUI.Table;

import Classes.Consultation;
import Classes.Doctor;
import Classes.Patient;
import Classes.SkinConsultationManager;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Appointment ID","Date","Time", "Doctor", "Patient", "Cost","Ward Num"};
    private ArrayList<Consultation> myList = new ArrayList<>();

    public UserTableModel(ArrayList<Consultation> list) throws ParseException {
        myList.addAll(list);
        if (myList.size()==0){
            String address = "151 / A / 28, Diwelwatta, Kaduruduwa, Wanchawela , Galle";
            Consultation c =new Consultation(new Date(), 1, SkinConsultationManager.doctorList[6],"Pasindu", "Geevinda",address, new Date(),"");
            c.getPatient().setMobileNumber("0786201390");

            myList.add(c);


            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy : MM : dd : HH:mm a");
            String address2 = "151 / A / 28, Diwelwatta, Kaduruduwa, Wanchawela , Galle";
            Consultation c2 =new Consultation(formatter1.parse("2023 : 01 : 07 : 11:59 AM"), 1, SkinConsultationManager.doctorList[1],"Pasindu78", "Geevind879a",address2, formatter.parse("29-06-2001"),"hi");

            myList.add(c2);
            System.out.println(myList);
        }
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
