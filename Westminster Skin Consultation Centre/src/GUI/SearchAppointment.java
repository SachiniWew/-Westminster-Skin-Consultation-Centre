package GUI;

import Classes.Consultation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

import static java.awt.Color.*;

public class SearchAppointment extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    private final boolean isView;
    private String mobileNumber;
    private JButton confirm, back;
    private JTextField mobile,appointmentIDField;
    private boolean mobileChecked, appointmentIDChecked;
    private final boolean isUpdate;

    public SearchAppointment(boolean isUpdate, boolean isView){
        this.isView = isView;
        this.isUpdate = isUpdate;
        mobileChecked =false;
        appointmentIDChecked =false;
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;



        gbc.gridy =0;
        gbc.gridx =0;
        panel.add(picturePanel());

        gbc.gridy =0;
        gbc.gridx =2;
        panel.add(makePanel());

        mobile.addKeyListener(this);
        appointmentIDField.addKeyListener(this);
        back.addActionListener(this);
        confirm.addActionListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        add(panel);
        Color g1 = new Color(29,82,12);
        panel.setBackground(g1);
        setUndecorated(true);
        pack();
        setLocation(20,50);
        setVisible(true);
    }

    private JPanel makePanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        Color g1 = new Color(29,82,12);
        panel.setBackground(g1);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20,30,20,30);

        Label label = new Label("Enter below details to find the reservation", Label.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 35));
        Label appointmentL = new Label("Appointment ID :");
        Label mobileL = new Label("Mobile Number  :");

        appointmentIDField = new JTextField(10);
        mobile = new JTextField(10);


        confirm = new JButton("Confirm");
        confirm.setFont(new Font("SansSerif", Font.BOLD, 20));
        back = new JButton("Back");
        back.setFont(new Font("SansSerif", Font.BOLD, 20));


        mobileL.setForeground(Color.white);
        appointmentL.setForeground(Color.white);
        label.setForeground(Color.black);
        label.setFont(new Font("Verdana", Font.BOLD , 14));

        confirm.setBorderPainted(false);
        confirm.setOpaque(true);
        confirm.setBackground(red.darker());
        confirm.setForeground(Color.WHITE);
        back.setBorderPainted(false);
        back.setOpaque(true);
        back.setBackground(blue.darker());
        back.setForeground(Color.WHITE);

        gbc.gridwidth =4;
        gbc.gridy=0;
        gbc.gridx =0;
        panel.add(label,gbc);

        gbc.insets = new Insets(10,30,10,30);
        gbc.gridwidth =1;
        gbc.gridy=1;
        gbc.gridx =0;
        panel.add(appointmentL,gbc);
        gbc.gridx =1;
        panel.add(appointmentIDField,gbc);

        gbc.gridy=2;
        gbc.gridx =0;
        panel.add(mobileL,gbc);

        gbc.gridx =1;
        panel.add(mobile,gbc);

        gbc.insets = new Insets(10,30,30,30);
        gbc.gridwidth =3;
        gbc.gridy=3;
        gbc.gridx =0;
        panel.add(confirm,gbc);

        gbc.gridx =0;
        gbc.gridy=4;
        panel.add(back,gbc);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==back) {
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
                            else JOptionPane.showConfirmDialog(this, "There is no such Appointment", "Warning", JOptionPane.OK_CANCEL_OPTION);

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
                            else JOptionPane.showConfirmDialog(this, "There is no such Appointment", "Warning", JOptionPane.OK_CANCEL_OPTION);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }else JOptionPane.showConfirmDialog(this, "Please Enter the Appointment ID and Mobile Number", "Warning", JOptionPane.OK_CANCEL_OPTION);
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
        displayField.setBounds(0,0,700,600);
        Image image = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Pictures/pic4.jpeg"))).getImage();
        Image imageScale = image.getScaledInstance(displayField.getWidth(), displayField.getHeight(), Image.SCALE_SMOOTH);
        displayField.setIcon(new ImageIcon(imageScale));

        //Upper text display
        JLabel n1Label = new JLabel("Westminster Skin Consultation Center",SwingConstants.CENTER);
        n1Label.setFont(new Font("DialogInput", Font.BOLD, 25));
        n1Label.setForeground(Color.BLACK);
        JLabel n2Label = new JLabel("Search Appointment",SwingConstants.CENTER);
        n2Label.setFont(new Font("DialogInput", Font.BOLD, 40));
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
