package GUI;

import Classes.Consultation;
import Classes.Patient;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class AppointmentDetail extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
    private final Consultation c;
    private JLabel nameL, surnameL, mobileNumberL, dateL, bookingEndTimeL, doctorL, noteL, DOBLable,
            doctorSpecializationL, ageL, addressL,appointmentIDL, costL;
    private JLabel name, surname, mobileNumber, date, bookingEndTime, doctor, DOB, doctorSpecialization,
            age, appointmentID, cost;
    private JButton ok, delete, update;
    private final JPanel[] panels = new JPanel[]{new JPanel(), new JPanel()};

    public AppointmentDetail(Consultation c) throws ParseException {
        JPanel p = new JPanel();

        JPanel head  = headPanel();

        p.setBorder(new TitledBorder("Appointment Details"));
        this.c = c;
        componentInitialization();
        p.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(20,0,0,0);
        gbc.gridwidth = 4;
        gbc.gridy = 0;
        gbc.gridx = 0;
        p.add(head, gbc);

        gbc.insets = new Insets(20,20,0,20);
        gbc.gridy = 1;
        gbc.gridx = 0;
        p.add(nameL, gbc);

        gbc.gridx = 1;
        p.add(surnameL, gbc);

        gbc.gridx = 2;
        p.add(appointmentIDL, gbc);

        gbc.insets = new Insets(0,20,20,20);
        gbc.gridy = 2;
        gbc.gridx = 0;
        p.add(name, gbc);

        gbc.gridx = 1;
        p.add(surname, gbc);

        gbc.gridx = 2;
        p.add(appointmentID, gbc);

        gbc.insets = new Insets(0,20,20,20);
        gbc.gridy = 4;
        gbc.gridx = 0;
        p.add(addressL, gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 4;
        gbc.gridx = 1;
        p.add(panels[1], gbc);

        gbc.insets = new Insets(20,20,0,20);
        gbc.gridwidth = 1;
        gbc.gridy = 6;
        gbc.gridx = 0;
        p.add(mobileNumberL, gbc);

        gbc.gridx = 1;
        p.add(ageL, gbc);

        gbc.gridx = 2;
        p.add(costL, gbc);

        gbc.insets = new Insets(0,20,20,20);
        gbc.gridx = 0;
        gbc.gridy = 7;
        p.add(mobileNumber, gbc);

        gbc.gridx = 1;
        p.add(age, gbc);

        gbc.gridx = 2;
        p.add(cost, gbc);

        gbc.insets = new Insets(20,20,0,20);
        gbc.gridy = 8;
        gbc.gridx = 0;
        p.add(dateL, gbc);

        gbc.gridx = 1;
        p.add(bookingEndTimeL, gbc);

        gbc.gridx = 2;
        p.add(DOBLable, gbc);

        gbc.insets = new Insets(0,20,20,20);
        gbc.gridy = 9;
        gbc.gridx = 0;
        p.add(date, gbc);

        gbc.gridx = 1;
        p.add(bookingEndTime, gbc);

        gbc.gridx = 2;
        p.add(DOB, gbc);

        gbc.insets = new Insets(20,20,0,20);
        gbc.gridy = 10;
        gbc.gridx = 0;
        p.add(doctorL, gbc);

        gbc.gridx = 1;
        p.add(doctorSpecializationL, gbc);

        gbc.insets = new Insets(0,20,20,20);
        gbc.gridy = 11;
        gbc.gridx = 0;
        p.add(doctor, gbc);

        gbc.gridx = 1;
        p.add(doctorSpecialization, gbc);

        gbc.insets = new Insets(0,20,20,20);
        gbc.gridy = 12;
        gbc.gridx = 0;
        p.add(noteL, gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 12;
        gbc.gridx = 1;
        p.add(panels[0], gbc);

        gbc.insets = new Insets(0,10,30,10);
        gbc.gridwidth =1;
        gbc.gridy = 14;
        gbc.gridx = 0;
        p.add(ok, gbc);

        gbc.gridx = 2;
        p.add(delete, gbc);

        gbc.gridx = 3;
        p.add(update, gbc);

        ok.addActionListener(this);
        delete.addActionListener(this);
        update.addActionListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        p.setForeground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        p.setBackground(Color.BLACK);
        add(p);
        setUndecorated(true);
        pack();
        setLocation(500,100);
        setVisible(true);

    }

    private void componentInitialization() throws ParseException {
        dateL = new JLabel();
        nameL = new JLabel(         "First Name     :", SwingConstants.LEFT);
        surnameL = new JLabel(      "Surname        :", SwingConstants.LEFT);
        mobileNumberL = new JLabel( "Mobile Number  :", SwingConstants.LEFT);
        dateL = new JLabel(         "Booking date and time:", SwingConstants.LEFT);
        DOBLable = new JLabel(         "Date Of Birth  :", SwingConstants.LEFT);
        bookingEndTimeL = new JLabel("Reservation End Time :");
        doctorL = new JLabel(       "Doctor Name :");
        DOBLable = new JLabel(          "Date of Birth");
        ageL = new JLabel(          "Age :");
        doctorSpecializationL = new JLabel("Doctor Specialization :");
        appointmentIDL = new JLabel("Appointment ID :");
        noteL = new JLabel("Note :");
        addressL = new JLabel("Address       :");
        costL = new JLabel("Cost :");

        Patient p = c.getPatient();

        String pattern = "MM/dd/yyyy  HH:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM dd, yyyy");

        name = new JLabel(p.getName());
        surname = new JLabel(p.getSurname());
        mobileNumber = new JLabel(p.getMobileNumber());
        System.out.println(p.getMobileNumber());
        date = new JLabel(dateFormat.format(c.getDate()));
        bookingEndTime = new JLabel(dateFormat.format(c.getEndTime()));
        doctor = new JLabel(c.getDoctor().getName()+" "+c.getDoctor().getSurname());
        System.out.println(p.getDateOfBirth());
        DOB = new JLabel(p.getDateOfBirth().format(df));
        doctorSpecialization = new JLabel(c.getDoctor().getSpecialisation().toString());
        age = new JLabel(p.getAge());
        appointmentID = new JLabel(String.valueOf(c.getAppointmentID()));
        cost = new JLabel(String.valueOf(c.getCost()));

        System.out.println(c.getNotes());
        JLabel[] values = new JLabel[]{ new JLabel(c.getNotes(), SwingConstants.LEFT), new JLabel(p.getAddress(), SwingConstants.LEFT)};

        for (int i = 0; i < 2; i++) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;

            panels[i].setPreferredSize(new Dimension(100,20));

            gbc.gridx =0;
            values[i].setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 10));
            values[i].setForeground(Color.lightGray);
            panels[i].add(values[i],gbc);
            panels[i].setBackground(Color.BLACK);

        }

        ok = new JButton("OK");
        update = new JButton("UPDATE");
        delete = new JButton("DELETE");
        delete.setForeground(Color.WHITE);
        update.setForeground(Color.WHITE);
        ok.setForeground(Color.WHITE);

        ok.setBackground(Color.cyan.darker());
        update.setBackground(Color.orange.darker());
        delete.setBackground(Color.RED.darker());


        colorInitializer(nameL, surnameL, mobileNumberL, dateL, bookingEndTimeL, doctorL, noteL);
        DOBLable.setForeground(Color.lightGray);
        colorInitializer(doctorSpecializationL, ageL, addressL, appointmentIDL, costL, name, surname);
        mobileNumber.setForeground(Color.lightGray);
        date.setForeground(Color.lightGray);
        bookingEndTime.setForeground(Color.lightGray);
        doctor.setForeground(Color.lightGray);
        DOB.setForeground(Color.lightGray);
        name.setForeground(Color.lightGray);
        surname.setForeground(Color.lightGray);
        doctorSpecialization.setForeground(Color.lightGray);
        age.setForeground(Color.lightGray);
        appointmentID.setForeground(Color.lightGray);
        cost.setForeground(Color.lightGray);
        name.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 10));
        fontInitializer(surname, mobileNumber, date, bookingEndTime,10);
        doctor.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 10));
        DOB.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 10));
        fontInitializer(doctorSpecialization, age, appointmentID, cost,10);
    }
    private JPanel headPanel(){

        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Keep Remember", SwingConstants.CENTER),
                required = new JLabel("You must remember your PHONE NUMBER and APPOINTMENT ID for further use.", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 26));

        panel.add(title, BorderLayout.NORTH);
        panel.add(required, BorderLayout.CENTER);
        panel.setBackground(Color.GREEN.darker());
        return panel;
    }

    public static void fontInitializer(JLabel surname, JLabel mobileNumber, JLabel date, JLabel bookingEndTime, int fontSize) {
        surname.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, fontSize));
        mobileNumber.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, fontSize));
        date.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, fontSize));
        bookingEndTime.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, fontSize));
    }

    private void colorInitializer(JLabel nameL, JLabel surnameL, JLabel mobileNumberL, JLabel dateL, JLabel bookingEndTimeL, JLabel doctorL, JLabel noteL) {
        nameL.setForeground(Color.GRAY);
        surnameL.setForeground(Color.GRAY);
        mobileNumberL.setForeground(Color.GRAY);
        dateL.setForeground(Color.GRAY);
        bookingEndTimeL.setForeground(Color.GRAY);
        doctorL.setForeground(Color.GRAY);
        noteL.setForeground(Color.GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==ok){
            dispose();
            try {
                new MainGUI();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == delete) {
            dispose();
            Consultation.deleteConsultation(c);
            try {
                new MainGUI();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } else if (e.getSource() == update) {
            MakeAppointment m = null;
            dispose();
            new MobileNumberEnter(true,false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    int mouseX, mouseY;

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setLocation(getX()+(e.getX()-mouseX),getY()+(e.getY()-mouseY));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
