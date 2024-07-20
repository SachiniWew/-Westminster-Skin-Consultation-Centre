package GUI;

import Classes.Consultation;
import Classes.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AppointmentDetail extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
    private final Consultation c;
    private JLabel nameL, surnameL, mobileNumberL, dateL, bookingEndTimeL, doctorL, noteL, dofL
            ,
            doctorSpecializationL, ageL, addressL,appointmentIDL, costL;
    private JLabel name, surname, mobileNumber, date, bookingEndTime, doctor, dof, doctorSpecialization,
            age, appointmentID, cost;
    private JButton ok, delete, update;
    private final JPanel[] panels = new JPanel[]{new JPanel(), new JPanel()};

    public AppointmentDetail(Consultation c) throws ParseException {
        JPanel p = new JPanel();

        this.c = c;
        componentInitialization();
        p.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(0,0,0,20);
        gbc.gridwidth = 4;
        gbc.gridy = 0;
        gbc.gridx = 0;
        p.add(picturePanel(), gbc);

        gbc.insets = new Insets(0,30,0,30);
        gbc.gridwidth = 4;
        gbc.gridy = 0;
        gbc.gridx = 7;
        p.add(DetailPanel(), gbc);


        addMouseListener(this);
        addMouseMotionListener(this);
        Color g1 = new Color(29,82,12);
        p.setBackground(g1);
        add(p);
        setUndecorated(true);
        pack();
        setLocation(20,50);
        setVisible(true);

    }

        public JPanel picturePanel(){
        JPanel picPanel = new JPanel();
        Color cp = new Color(222,222,222);
        picPanel.setBackground(cp);
        GridBagLayout bL =new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        picPanel.setLayout(bL);

        //Picture display
        JLabel displayField = new JLabel();
        displayField.setBounds(0,0,430,600);
        Image image = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Pictures/pic5.jpeg"))).getImage();
        Image imageScale = image.getScaledInstance(displayField.getWidth(), displayField.getHeight(), Image.SCALE_SMOOTH);
        displayField.setIcon(new ImageIcon(imageScale));

        //Upper text display
            JLabel n1Label = new JLabel("Westminster Skin Consultation Center",SwingConstants.CENTER);
            n1Label.setFont(new Font("DialogInput", Font.BOLD, 20));
            n1Label.setForeground(Color.BLACK);
            JLabel n2Label = new JLabel("Appointment Details",SwingConstants.CENTER);
            n2Label.setFont(new Font("DialogInput", Font.BOLD, 35));
            n2Label.setForeground(Color.BLACK);

            //Components in to panel

            gbc.insets = new Insets(10,20,10,20);
            gbc.gridy =1;
            gbc.gridx=0;
            picPanel.add(n1Label, gbc);

            gbc.gridy =2;
            gbc.gridx=0;
            picPanel.add(n2Label, gbc);

            gbc.gridy =3;
            gbc.gridx =0;
            picPanel.add(displayField, gbc);


            return picPanel;
    }

    private void componentInitialization() throws ParseException {
        dateL = new JLabel();
        nameL = new JLabel          (" First Name                :");
        surnameL = new JLabel       (" Surname                   :");
        mobileNumberL = new JLabel  (" Mobile Number             :");
        dateL = new JLabel          (" Appointment date and time :");
        dofL = new JLabel           (" Date Of Birth             :");
        bookingEndTimeL = new JLabel(" Reservation End Time      :");
        doctorL = new JLabel        (" Doctor Name               :");
        dofL = new JLabel           (" Date of Birth             :");
        ageL = new JLabel           (" Age                       :");
        doctorSpecializationL = new JLabel(" Doctor Specialization          :");
        appointmentIDL = new JLabel (" Appointment ID             :");
        noteL = new JLabel          (" Note                       :");
        addressL = new JLabel       (" Address                    :");
        costL = new JLabel          (" Cost                       :");

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
        dof = new JLabel(p.getDateOfBirth().format(df));
        doctorSpecialization = new JLabel(c.getDoctor().getSpecialisation().toString());
        age = new JLabel(p.getAge());
        appointmentID = new JLabel(String.valueOf(c.getAppointmentID()));
        cost = new JLabel(String.valueOf(c.getCost()));

        JLabel[] values = new JLabel[]{ new JLabel(c.getNotes(), SwingConstants.LEFT), new JLabel(p.getAddress(), SwingConstants.LEFT)};

        for (int i = 0; i < 2; i++) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panels[i].setPreferredSize(new Dimension(100,40));
            gbc.gridx =0;
            values[i].setFont(new Font("Verdana", Font.ITALIC, 12));
            values[i].setForeground(Color.BLACK);
            panels[i].add(values[i],gbc);
            panels[i].setBackground(Color.WHITE);
        }

        ok = new JButton("OK");
        update = new JButton("UPDATE");
        delete = new JButton("DELETE");


        delete.setBorderPainted(false);
        delete.setOpaque(true);
        delete.setBackground(Color.red.darker());
        ok.setBorderPainted(false);
        ok.setOpaque(true);
        ok.setBackground(Color.green);
        update.setBorderPainted(false);
        update.setOpaque(true);
        update.setBackground(Color.BLUE);
        update.setForeground(Color.WHITE);
        ok.setForeground(Color.WHITE);
        delete.setForeground(Color.WHITE);


        colorInitializer(nameL, surnameL, mobileNumberL, dateL, bookingEndTimeL, doctorL, noteL);
        dofL.setForeground(Color.WHITE);
        colorInitializer(doctorSpecializationL, ageL, addressL, appointmentIDL, costL, name, surname);
        mobileNumber.setForeground(Color.lightGray);
        date.setForeground(Color.lightGray);
        bookingEndTime.setForeground(Color.lightGray);
        doctor.setForeground(Color.lightGray);
        dof.setForeground(Color.lightGray);
        name.setForeground(Color.lightGray);
        surname.setForeground(Color.lightGray);
        doctorSpecialization.setForeground(Color.lightGray);
        age.setForeground(Color.lightGray);
        appointmentID.setForeground(Color.lightGray);
        cost.setForeground(Color.lightGray);
        name.setFont(new Font("Verdana", Font.BOLD , 14));
        fontInitializer(surname, mobileNumber, date, bookingEndTime);
        doctor.setFont(new Font("Verdana", Font.BOLD , 14));
        dof.setFont(new Font("Verdana", Font.BOLD , 14));
        fontInitializer(doctorSpecialization, age, appointmentID, cost);
    }
    private JPanel DetailPanel(){

        JPanel p = new JPanel(new BorderLayout());

        p.setBackground(Color.GREEN.darker());
        GridBagLayout bL =new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p.setLayout(bL);

        gbc.insets = new Insets(20,30,5,20);
        gbc.gridy = 1;
        gbc.gridx = 0;
        p.add(nameL, gbc);
        gbc.gridy = 1;
        gbc.gridx = 1;
        p.add(name, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        p.add(surnameL, gbc);
        gbc.gridy = 2;
        gbc.gridx = 1;
        p.add(surname, gbc);


        gbc.gridy = 3;
        gbc.gridx = 0;
        p.add(mobileNumberL, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        p.add(mobileNumber, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        p.add(dofL, gbc);
        gbc.gridy = 4;
        gbc.gridx = 1;
        p.add(dof, gbc);


        gbc.gridy = 5;
        gbc.gridx = 0;
        p.add(ageL, gbc);
        gbc.gridy = 5;
        gbc.gridx = 1;
        p.add(age, gbc);


        gbc.gridy = 6;
        gbc.gridx = 0;
        p.add(addressL, gbc);
        gbc.gridwidth = 2;
        gbc.gridy = 6;
        gbc.gridx = 1;
        p.add(panels[1], gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        p.add(appointmentIDL, gbc);
        gbc.gridy = 7;
        gbc.gridx = 1;
        p.add(appointmentID, gbc);


        gbc.gridy = 8;
        gbc.gridx = 0;
        p.add(doctorL, gbc);
        gbc.gridy = 8;
        gbc.gridx = 1;
        p.add(doctor, gbc);

        gbc.gridy = 9;
        gbc.gridx = 0;
        p.add(doctorSpecializationL, gbc);
        gbc.gridy = 9;
        gbc.gridx = 1;
        p.add(doctorSpecialization, gbc);


        gbc.gridy = 10;
        gbc.gridx = 0;
        p.add(dateL, gbc);
        gbc.gridy = 10;
        gbc.gridx = 1;
        p.add(date, gbc);

        gbc.gridy = 11;
        gbc.gridx = 0;
        p.add(bookingEndTimeL, gbc);
        gbc.gridy = 11;
        gbc.gridx = 1;
        p.add(bookingEndTime, gbc);


        gbc.gridy = 12;
        gbc.gridx = 0;
        p.add(noteL, gbc);
        gbc.gridwidth = 2;
        gbc.gridy = 12;
        gbc.gridx = 1;
        p.add(panels[0], gbc);

        gbc.gridy = 13;
        gbc.gridx = 0;
        p.add(costL, gbc);
        gbc.gridy = 13;
        gbc.gridx = 1;
        p.add(cost, gbc);

        gbc.insets = new Insets(30,10,30,10);
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

        Color g1 = new Color(29,82,12);
        p.setBackground(g1);

        return p;
    }

    public static void fontInitializer(JLabel surname, JLabel mobileNumber, JLabel date, JLabel bookingEndTime) {
        surname.setFont(new Font("Verdana", Font.BOLD,14));
        mobileNumber.setFont(new Font("Verdana", Font.BOLD,14));
        date.setFont(new Font("Verdana", Font.BOLD ,14));
        bookingEndTime.setFont(new Font("Verdana", Font.BOLD,14));
    }

    private void colorInitializer(JLabel nameL, JLabel surnameL, JLabel mobileNumberL, JLabel dateL, JLabel bookingEndTimeL, JLabel doctorL, JLabel noteL) {
        nameL.setForeground(Color.white);
        surnameL.setForeground(Color.white);
        mobileNumberL.setForeground(Color.white);
        dateL.setForeground(Color.white);
        bookingEndTimeL.setForeground(Color.white);
        doctorL.setForeground(Color.white);
        noteL.setForeground(Color.white);
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
            new SearchAppointment(true,false);
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
