package GUI;

import Classes.Consultation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;

public class MobileNumberEnter extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    private final boolean isView;
    private String mobileNumber;
    private JButton confirm, close;
    private JTextField mobile,appointmentIDField;
    private boolean mobileChecked, appointmentIDChecked;
    private final boolean isUpdate;

    public MobileNumberEnter(boolean isUpdate, boolean isView){
        this.isView = isView;
        this.isUpdate = isUpdate;
        mobileChecked =false;
        appointmentIDChecked =false;

        JPanel panel = makePanel();
        add(panel);

        mobile.addKeyListener(this);
        appointmentIDField.addKeyListener(this);
        close.addActionListener(this);
        confirm.addActionListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        panel.setBackground(Color.BLACK);
        setUndecorated(true);
        pack();
        setLocation(600,75);
        setVisible(true);
    }

    private JPanel makePanel(){
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20,30,20,30);

        Label label = new Label("Enter Your Mobile Number to proceed", Label.CENTER);
        Label mobileL = new Label("Mobile Number  :");
        Label appointment = new Label("Appointment ID :");
        confirm = new JButton("Confirm");
        close = new JButton("Back");
        mobile = new JTextField(10);
        appointmentIDField = new JTextField(10);


        mobileL.setForeground(Color.lightGray);
        appointment.setForeground(Color.lightGray);
        label.setForeground(Color.lightGray);
        label.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 14));
        confirm.setBackground(Color.cyan.darker());
        close.setBackground(Color.red);
        confirm.setForeground(Color.WHITE);
        close.setForeground(Color.WHITE);

        gbc.gridwidth =4;
        gbc.gridy=0;
        gbc.gridx =0;
        panel.add(label,gbc);

        gbc.insets = new Insets(10,30,10,30);
        gbc.gridwidth =1;
        gbc.gridy=1;
        gbc.gridx =0;
        panel.add(appointment,gbc);

        gbc.gridx =1;
        panel.add(appointmentIDField,gbc);

        gbc.gridy=2;
        gbc.gridx =0;
        panel.add(mobileL,gbc);

        gbc.gridx =1;
        panel.add(mobile,gbc);

        gbc.insets = new Insets(10,30,30,30);
        gbc.gridwidth =1;
        gbc.gridy=3;
        gbc.gridx =0;
        panel.add(confirm,gbc);

        gbc.gridx =1;
        panel.add(close,gbc);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==close) {
            this.dispose();
            try {
                new MainGUI();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == confirm) {
            System.out.println(mobileChecked+" "+appointmentIDChecked);
            if (mobileChecked && appointmentIDChecked){
                mobileNumber = mobile.getText().trim();
                if (mobileNumber.length()!=10){
                    JOptionPane.showConfirmDialog(this, "Cannot be less than or more than 10 digits", "Warning", JOptionPane.OK_CANCEL_OPTION);
                }else {
                    if (isUpdate) {
                        try {
                            Consultation c = findConsultation();
                            if (c!=null){
                                MakeAppointment m = new MakeAppointment(true);
                                m.getDetailPanel().setUpdateConsultation(c);
                                m.getDetailPanel().setAppointmentDetails(c);
                                m.getDetailPanel().getMobileNumberEnter().dispose();
                                this.dispose();
                            }
                            else JOptionPane.showConfirmDialog(this, "There is no such reservation", "Warning", JOptionPane.OK_CANCEL_OPTION);

                        } catch (IOException | ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (isView) {
                        Consultation c = findConsultation();
                        try {
                            if (c!=null) {
                                new AppointmentDetail(c);
                                this.dispose();
                            }
                            else JOptionPane.showConfirmDialog(this, "There is no such reservation", "Warning", JOptionPane.OK_CANCEL_OPTION);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }else JOptionPane.showConfirmDialog(this, "Fields not meet requirements", "Warning", JOptionPane.OK_CANCEL_OPTION);
        }

    }
    public Consultation findConsultation(){
        this.mobileNumber = mobile.getText().trim();
        int appointmentIDText = Integer.parseInt(appointmentIDField.getText().trim());
        Consultation consultation1 = Consultation.findConsultationByMobile(this.mobileNumber);
        Consultation consultation2 = Consultation.findConsultationByAppointmentID(appointmentIDText);
        if (consultation1==consultation2) return consultation1;
        else return null;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar()!=KeyEvent.VK_BACK_SPACE && e.getKeyChar()!=KeyEvent.VK_ENTER) {
            if (!mobile.getText().equals("")) {
                mobileChecked = Consultation.checkMobileNumber(mobile, this, e);
            }
            if (appointmentIDField.getText().length()>0) {
                appointmentIDChecked =
                        Consultation.checkConsultationByAppointmentID(appointmentIDField, this);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    int mouseX, mouseY;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

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
