package GUI.Panels;

import Classes.*;
import GUI.Comparator.AlphabeticComparatorUser;
import GUI.AppointmentDetail;
import GUI.MainGUI;
import GUI.SearchAppointment;
import GUI.Table.UserTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class DetailEnterPanel extends JPanel implements ActionListener, ItemListener {
    private JTextField name, surname, mobileNumber, address;
    private TextArea note;
    private JSpinner datePicker, DOB;
    private JButton submit, reset, back;
    private JLabel nameL, surnameL, mobileNumberL, addressL, dateL, numberOfHoursL, doctorL, noteL, DOBL;
    private JFrame container;
    private JComboBox<String> doctorDropDown, hoursDropDown;
    private boolean isUpdate;
    private Consultation updateConsultation;
    private Date date1,date2;
    private final UserTableModel userTableModel;

    private SearchAppointment mobileNumberEnter;

    public DetailEnterPanel(boolean isUpdate, JFrame container, UserTableModel userTableModel) {
        this.userTableModel = userTableModel;
        this.isUpdate = isUpdate;

        if (isUpdate){
            mobileNumberEnter =new SearchAppointment(true, false);
            this.container = container;
            componentInitialize();
            addDetailsForm();
            container.dispose();

        }else {
            this.container = container;
            componentInitialize();
            addDetailsForm();
        }

        doctorDropDown.addItemListener(this);
        back.addActionListener(this);
        reset.addActionListener(this);
        submit.addActionListener(this);
    }

    private void addDetailsForm(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Color g1 = new Color(29,82,12);
        setBackground(g1);

        gbc.insets = new Insets(30,10,10,10);
        gbc.gridx =0;
        gbc.gridy =1;
        add(doctorL, gbc);
        gbc.gridx =1;
        add(doctorDropDown, gbc);

        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx =0;
        gbc.gridy =2;
        add(nameL, gbc);
        gbc.gridx =1;
        add(name, gbc);

        gbc.gridx =0;
        gbc.gridy =3;
        add(surnameL, gbc);
        gbc.gridx =1;
        add(surname, gbc);

        gbc.gridx =0;
        gbc.gridy =4;
        add(addressL, gbc);
        gbc.gridx =1;
        add(address, gbc);

        gbc.gridx =0;
        gbc.gridy =5;
        add(mobileNumberL, gbc);
        gbc.gridx =1;
        add(mobileNumber, gbc);

        gbc.gridx =0;
        gbc.gridy =6;
        add(DOBL, gbc);
        gbc.gridx =1;
        add(DOB, gbc);

        gbc.gridx =0;
        gbc.gridy =7;
        add(dateL, gbc);
        gbc.gridx =1;
        add(datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        add(numberOfHoursL, gbc);
        gbc.gridx = 1;
        add(hoursDropDown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        add(noteL, gbc);
        gbc.gridx = 1;
        add(note, gbc);

        gbc.insets = new Insets(10,10,5,30);
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 11;
        add(submit, gbc);

        gbc.insets = new Insets(0,10,5,30);
        gbc.gridx = 0;
        gbc.gridy = 12;
        add(reset, gbc);


        gbc.insets = new Insets(0,10,30,30);
        gbc.gridx = 0;
        gbc.gridy = 13;
        add(back, gbc);
    }


    private Consultation makeNewAppointment() throws ParseException {
        boolean added=false;
        Consultation c = null;

        String doctorFullName = (String) doctorDropDown.getSelectedItem();
        String patientFirstName = Person.nameCapitalising(name.getText());
        String patientSurName = Person.nameCapitalising(surname.getText());

        String addressString = address.getText();
        String mobileNumberString = mobileNumber.getText();
        Date DOB_Date = (Date) DOB.getValue();
        Date date = (Date) datePicker.getValue();
        int hours = Integer.parseInt(String.valueOf(hoursDropDown.getSelectedIndex()));
        String notes = note.getText();

        //testing
        assert doctorFullName != null;
        try {
            if (!doctorFullName.equals("None") && mobileNumberString.trim().length()==10) {
                Doctor doctorSelectByCustomer = Doctor.findDoctorObjectByFullName(doctorFullName);
                Doctor selectedDoctor = Doctor.randomlySelectAnotherDoctorSpecializedIfUnavailable(doctorSelectByCustomer, date, hours, this);

                Date date1 = new Date();
                date1 = Consultation.getEndTime(date1, 1);

                if (selectedDoctor != null && hours != 0 && date.compareTo(date1) >= 0 && !patientSurName.equals("") && !patientFirstName.equals("")) {
                    Patient patient = Patient.isCustomer(patientFirstName, patientSurName);
                    if (patient != null) {
                        if (isUpdate) {
                            c = updateConsultation;
                            updateData(patientFirstName, patientSurName, addressString, mobileNumberString, DOB_Date, date, hours, notes, selectedDoctor);
                        }
                        else c = new Consultation(date, hours, selectedDoctor, patient, notes);
                        added = true;

                    } else {
                        if (isUpdate) {
                            c = updateConsultation;
                            updateData(patientFirstName, patientSurName, addressString, mobileNumberString, DOB_Date, date, hours, notes, selectedDoctor);
                        }
                        else {
                            c = new Consultation(date, hours, selectedDoctor, patientFirstName, patientSurName, addressString, DOB_Date, notes);
                            try {
                                c.getPatient().setMobileNumber(mobileNumberString);
                                added = true;
                            } catch (NumberFormatException e) {
                                Consultation.deleteConsultation(c);
                                JOptionPane.showMessageDialog(this, "The Mobile Number Is Wrong. Please enter correct one", "Warning", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                }
                else if (patientSurName.equals("") || patientFirstName.equals("")) {
                    JOptionPane.showConfirmDialog(this, "Required fields are empty", "Warning", JOptionPane.OK_CANCEL_OPTION);
                }
                else if (date.before(date1)) {
                    JOptionPane.showConfirmDialog(this, "Reservation should be made at least an hour from now.", "Warning", JOptionPane.OK_CANCEL_OPTION);
                }
                else if (hours == 0) {
                    JOptionPane.showConfirmDialog(this, "Please select hours you booking", "Warning", JOptionPane.OK_CANCEL_OPTION);
                }
                else {
                    JOptionPane.showConfirmDialog(this, "All doctors in that specialization category are reserved for that time range. Please select another time", "Warning", JOptionPane.OK_CANCEL_OPTION);
                }
                if (added) {
                    assert c != null;
                    System.out.println("Appointment " + c.getAppointmentID() + " made successfully\n" );
                }
            } else if (mobileNumberString.trim().length()!=10) {
                JOptionPane.showConfirmDialog(this, "Please enter correct mobile number", "Warning", JOptionPane.OK_CANCEL_OPTION);
            } else
                JOptionPane.showConfirmDialog(this, "Please select a doctor", "Warning", JOptionPane.OK_CANCEL_OPTION);
        }catch (NumberFormatException e){
            JOptionPane.showConfirmDialog(this, "Please enter correct mobile number", "Warning", JOptionPane.OK_CANCEL_OPTION);
        }
        return c;

    }

    public void setAppointmentDetails(Consultation c) {
        doctorDropDown.setSelectedItem(c.getDoctor().getName()+" "+c.getDoctor().getSurname());
        name.setText(c.getPatient().getName());
        surname.setText(c.getPatient().getSurname());
        if (c.getPatient().getAddress()==null) address.setText("");
        else address.setText(c.getPatient().getAddress());
        mobileNumber.setText(c.getPatient().getMobileNumber());
        DOB.setValue(c.getPatient().getDateOfBirth(true));
        datePicker.setValue(c.getDate());
        hoursDropDown.setSelectedIndex(c.getHours());
        note.setText(c.getNotes());
    }

    public void setUpdateConsultation(Consultation updateConsultation) {
        this.updateConsultation = updateConsultation;
    }

    private void restAllInputField(){
        date2 = new Date();
        date1 = new Date();
        JSpinner.DateEditor de1 = new JSpinner.DateEditor(datePicker, "  yyyy : MM : dd || hh:mm a  ");
        doctorDropDown.setSelectedItem("None");
        name.setText("");
        surname.setText("");
        address.setText("");
        mobileNumber.setText("");
        DOB.setValue(new Date());
        datePicker.setValue(new Date());
        hoursDropDown.setSelectedIndex(0);
        note.setText("");
    }

    private void componentInitialize() {

        date1 = new Date();
        SpinnerDateModel sm1 = new SpinnerDateModel(date1, null, null, Calendar.DAY_OF_YEAR);
        datePicker = new JSpinner(sm1);
        JSpinner.DateEditor de1 = new JSpinner.DateEditor(datePicker, "  yyyy : MM : dd || hh:mm a  ");
        datePicker.setEditor(de1);

        date2 = new Date();
        SpinnerDateModel sm2 = new SpinnerDateModel(date2, null, null, Calendar.DAY_OF_YEAR);
        DOB = new JSpinner(sm2);
        JSpinner.DateEditor de2 = new JSpinner.DateEditor(DOB, "  yyyy : MM : dd  ");
        DOB.setEditor(de2);

        String[] doctorList = new String[Doctor.getAvailableNumOfDoctors()+1];

        Doctor[] d = SkinConsultationManager.doctorList;
        doctorList[0] = "None";
        for (int i=0;i< d.length;i++) {
            if (d[i] !=null) doctorList[i+1] = d[i].getName()+" "+d[i].getSurname();
        }



        doctorL = new JLabel        ("Doctor                 :");
        nameL = new JLabel          ("First Name             :");
        surnameL = new JLabel       ("Surname                :");
        mobileNumberL = new JLabel  ("Mobile Number          :");
        addressL = new JLabel       ("Address                :");
        dateL = new JLabel          ("Appointment date & time:");
        DOBL = new JLabel           ("Date Of Birth          :");
        noteL = new JLabel          ("Add a note             :");
        numberOfHoursL = new JLabel ("Reservation period     :");

        doctorDropDown = new JComboBox<>(doctorList);
        name = new JTextField(10);
        surname = new JTextField(10);
        mobileNumber = new JTextField(10);
        address = new JTextField(10);
        String[] hour = new String[]{"Choose Hours","1","2","3","4","5","6","7","8","9","10",};
        hoursDropDown = new JComboBox<>(hour);
        note = new TextArea(3,2);
        submit = new JButton("Submit");
        reset = new JButton("Reset");
        back = new JButton("Back");

        submit.setBorderPainted(false);
        submit.setOpaque(true);
        submit.setBackground(Color.red.darker());
        reset.setBorderPainted(false);
        reset.setOpaque(true);
        reset.setBackground(Color.green);
        back.setBorderPainted(false);
        back.setOpaque(true);
        back.setBackground(Color.BLUE);
        submit.setForeground(Color.WHITE);
        reset.setForeground(Color.WHITE);
        back.setForeground(Color.WHITE);

        nameL.setForeground(Color.WHITE);
        surnameL.setForeground(Color.WHITE);
        mobileNumberL.setForeground(Color.WHITE);
        addressL.setForeground(Color.WHITE);
        dateL.setForeground(Color.WHITE);
        DOBL.setForeground(Color.WHITE);
        numberOfHoursL.setForeground(Color.WHITE);
        doctorL.setForeground(Color.WHITE);
        noteL.setForeground(Color.WHITE);
        nameL.setFont(new Font("Verdana", Font.BOLD , 14));
        DOBL.setFont(new Font("Verdana", Font.BOLD , 14));
        AppointmentDetail.fontInitializer(surnameL, mobileNumberL, noteL, addressL);
        fontInitializing(dateL, numberOfHoursL, doctorL);
    }

    static void fontInitializing(JLabel dateL, JLabel numberOfHoursL, JLabel doctor) {
        dateL.setFont(new Font("Verdana", Font.BOLD , 12));
        numberOfHoursL.setFont(new Font("Verdana", Font.BOLD , 12));
        doctor.setFont(new Font("Verdana", Font.BOLD , 12));

    }

    private void updateData(String patientFirstName, String patientSurName, String addressString, String mobileNumberString, Date DOB_Date, Date date, int hours, String notes, Doctor selectedDoctor) {
        Consultation uC = updateConsultation;
        Patient p =uC.getPatient();
        p.setName(patientFirstName);
        p.setSurname(patientSurName);
        p.setAddress(addressString);
        p.setMobileNumber(mobileNumberString);
        p.setDateOfBirth(DOB_Date);
        uC.setDoctor(selectedDoctor);
        uC.setHours(hours);
        uC.setDate(date);
        uC.setNotes(notes);
    }

    public SearchAppointment getMobileNumberEnter() {
        return mobileNumberEnter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==submit) {
            System.out.println("""
        -----------------------
             New Appointment
        -----------------------""");
            if (isUpdate){
                try {
                    Consultation c =makeNewAppointment();
                    if (c!=null){
                        container.dispose();
                        new AppointmentDetail(c);
                    }
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                try {
                    Consultation c = makeNewAppointment();
                    if (c!=null) {
                        container.dispose();
                        new AppointmentDetail(c);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Appointment haven't made", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if (e.getSource()==back){
            container.dispose();
            try {
                new MainGUI();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==reset) {
            restAllInputField();
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource()==doctorDropDown){
            String item  = String.valueOf(doctorDropDown.getSelectedItem());
            if (item.trim().equals("None")) {
                ArrayList<Consultation> c = Consultation.getConsultations();
                c.sort(new AlphabeticComparatorUser());
                userTableModel.setMyList(Consultation.getConsultations());
                userTableModel.fireTableDataChanged();
            }else {

                Doctor[] d = SkinConsultationManager.doctorList;
                ArrayList<Consultation> consultations = new ArrayList<>();
                boolean haveConsultations = false;

                for (Consultation selectedDoctorObject : Consultation.getConsultations()) {
                    String text = selectedDoctorObject.getDoctor().getName() + " " +
                            selectedDoctorObject.getDoctor().getSurname();
                    if ((item).equals(text)) {
                        if (text.equals(item.trim())) {
                            consultations.add(selectedDoctorObject);
                            haveConsultations = true;
                        }
                    }
                }
                consultations.sort(new AlphabeticComparatorUser());
                if (haveConsultations) {
                    userTableModel.setMyList(consultations);
                }else {
                    userTableModel.setMyList(consultations);
                }
                userTableModel.fireTableDataChanged();
            }

        }
    }
}
