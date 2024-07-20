package GUI.Panels;

import Classes.*;
import GUI.Comparator.AlphabeticComparatorUser;
import GUI.AppointmentDetail;
import GUI.MainGUI;
import GUI.MobileNumberEnter;
import GUI.Table.UserTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    private JLabel nameL, surnameL, mobileNumberL, addressL, dateL, numberOfHoursL, doctor, noteL, DOBL;
    private JFrame container;
    private JComboBox<String> doctorDropDown, hoursDropDown;
    private boolean isUpdate;
    private Consultation updateConsultation;
    private Date date1,date2;
    private final UserTableModel userTableModel;

    private MobileNumberEnter mobileNumberEnter;

    public DetailEnterPanel(boolean isUpdate, JFrame container, UserTableModel userTableModel) {
        this.userTableModel = userTableModel;
        this.isUpdate = isUpdate;

        if (isUpdate){
            mobileNumberEnter =new MobileNumberEnter(true, false);
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
        JPanel panel = new JPanel(new BorderLayout());
        setBorder(new TitledBorder("Make an appointment."));
        JLabel title = new JLabel("Enter Your Details Here", SwingConstants.CENTER),
                required = new JLabel("The spaces marked with * may be omitted.", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 20));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(title, BorderLayout.NORTH);
        panel.add(required, BorderLayout.CENTER);
        panel.setBackground(Color.GREEN.darker());
        setBackground(Color.black);

        gbc.insets = new Insets(20,0,20,0);

        gbc.gridwidth = 4;
        gbc.gridheight =1;

        gbc.gridy =0;
        add(panel, gbc);

        gbc.insets = new Insets(10,30,10,30);
        gbc.gridwidth =1;
        gbc.gridx =0;
        gbc.gridy =1;
        add(doctor, gbc);

        gbc.gridx =1;
        add(doctorDropDown, gbc);

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


        gbc.gridx = 0;
        gbc.gridy = 11;
        add(submit, gbc);

        gbc.gridx = 1;
        add(reset, gbc);

        gbc.insets = new Insets(10,30,30,30);
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 12;
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
                    System.out.println(c.getPatient().toString() + "\n" + c.getDoctor().toString() + "\n" +
                            "Date :" + c.getDate() + "\n" +
                            "Appointment ID :" + c.getAppointmentID() + "\n" +
                            "Cost :" + c.getCost() + "\n" +
                            "Note :" + c.getNotes() + "\n\n");
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
        doctorDropDown = new JComboBox<>(doctorList);

        String[] hour = new String[]{"0","1","2","3","4","5","6","7","8","9","10",};
        hoursDropDown = new JComboBox<>(hour);

        name = new JTextField(10);
        surname = new JTextField(10);
        mobileNumber = new JTextField(10);
        address = new JTextField(10);
        note = new TextArea(3,2);
        nameL = new JLabel(         "First Name     :", SwingConstants.LEFT);
        surnameL = new JLabel(      "Surname        :", SwingConstants.LEFT);
        mobileNumberL = new JLabel( "Mobile Number  :", SwingConstants.LEFT);
        addressL = new JLabel(      "Address*       :", SwingConstants.LEFT);
        dateL = new JLabel(         "Booking date and time:", SwingConstants.LEFT);
        DOBL = new JLabel(         "Date Of Birth  :", SwingConstants.LEFT);
        submit = new JButton("Submit");
        reset = new JButton("Reset");
        back = new JButton("Back");
        doctor = new JLabel(        "Doctor         :");
        noteL = new JLabel(         "Add a note*    :");
        numberOfHoursL = new JLabel("Reservation period :");

        submit.setBackground(Color.cyan.darker());
        reset.setBackground(Color.red);
        back.setBackground(Color.magenta.darker());
        submit.setForeground(Color.WHITE);
        reset.setForeground(Color.WHITE);
        back.setForeground(Color.WHITE);

        nameL.setForeground(Color.lightGray);
        surnameL.setForeground(Color.lightGray);
        mobileNumberL.setForeground(Color.lightGray);
        addressL.setForeground(Color.lightGray);
        dateL.setForeground(Color.lightGray);
        DOBL.setForeground(Color.lightGray);
        numberOfHoursL.setForeground(Color.lightGray);
        doctor.setForeground(Color.lightGray);
        noteL.setForeground(Color.lightGray);
        nameL.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
        DOBL.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
        AppointmentDetail.fontInitializer(surnameL, mobileNumberL, noteL, addressL, 12);
        sameInitializing(dateL, numberOfHoursL, doctor);
    }

    static void sameInitializing(JLabel dateL, JLabel numberOfHoursL, JLabel doctor) {
        dateL.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
        numberOfHoursL.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
        doctor.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));

        UIManager.put("OptionPane.background", Color.lightGray);
        UIManager.put("Panel.background", Color.lightGray);
        UIManager.put("Panel.setForeground", Color.white);
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

    public MobileNumberEnter getMobileNumberEnter() {
        return mobileNumberEnter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("actionPerformed");
        if (e.getSource()==submit) {
            System.out.println(getWidth()+" "+getHeight());
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
                        JOptionPane.showMessageDialog(this, "No reservation was made", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }else if (e.getSource()==back){
            System.out.println("Back clicked");
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
