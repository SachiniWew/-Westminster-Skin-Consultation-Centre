package GUI.Panels;

import Classes.Consultation;
import GUI.Comparator.AppointmentIDComparator;
import GUI.Login;
import GUI.MakeAppointment;
import GUI.SearchAppointment;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import static java.awt.Color.*;

public class MainMenuPanel extends JPanel implements ActionListener{
    private JButton viewDetail, deleteConsultation, addConsultation, updateConsultation, close, back;

    private final JFrame container;

    public MainMenuPanel(JFrame container) throws IOException {
        this.container = container;
        componentInitialize();
        addDetailsForm();

        viewDetail.addActionListener(this);
        deleteConsultation.addActionListener(this);
        addConsultation.addActionListener(this);
        updateConsultation.addActionListener(this);
        close.addActionListener(this);
        back.addActionListener(this);
    }
    private void addDetailsForm(){

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Color g1 = new Color(29,82,12);
        setBackground(g1);

        gbc.insets = new Insets(10,0,20,0);
        setPreferredSize(new Dimension(350,630));

        gbc.insets = new Insets(5,30,30,0);
        gbc.gridy =2;
        gbc.gridx =1;
        add(addConsultation, gbc);

        gbc.insets = new Insets(5,30,30,0);
        gbc.gridy =3;
        gbc.gridx =1;
        add(updateConsultation, gbc);

        gbc.insets = new Insets(5,30,30,0);
        gbc.gridy =4;
        gbc.gridx =1;
        add(deleteConsultation, gbc);

        gbc.insets = new Insets(5,30,30,0);
        gbc.gridy =5;
        gbc.gridx =1;
        add(viewDetail, gbc);

        gbc.insets = new Insets(5,30,30,0);
        gbc.gridwidth =3;
        gbc.gridy = 6;
        gbc.gridx =1;
        add(back, gbc);

        gbc.insets = new Insets(5,30,30,0);
        gbc.gridwidth =3;
        gbc.gridy = 7;
        gbc.gridx =1;
        add(close, gbc);


    }

    private void componentInitialize()  {
        addConsultation = new JButton("Make Appointment");
        addConsultation.setFont(new Font("SansSerif", Font.BOLD, 20));
        deleteConsultation = new JButton("Delete Appointment");
        deleteConsultation.setFont(new Font("SansSerif", Font.BOLD, 20));
        updateConsultation = new JButton("Update Appointment");
        updateConsultation.setFont(new Font("SansSerif", Font.BOLD, 20));
        viewDetail = new JButton("View Your Appointment");
        viewDetail.setFont(new Font("SansSerif", Font.BOLD, 20));
        back = new JButton("Back");
        back.setFont(new Font("SansSerif", Font.BOLD, 20));
        close = new JButton("Close");
        close.setFont(new Font("SansSerif", Font.BOLD, 20));

        addConsultation.setBorderPainted(false);
        addConsultation.setOpaque(true);
        addConsultation.setBackground(black);
        addConsultation.setForeground(WHITE);

        deleteConsultation.setBorderPainted(false);
        deleteConsultation.setOpaque(true);
        deleteConsultation.setBackground(black);
        deleteConsultation.setForeground(WHITE);

        updateConsultation.setBorderPainted(false);
        updateConsultation.setOpaque(true);
        updateConsultation.setBackground(black);
        updateConsultation.setForeground(WHITE);


        viewDetail.setBorderPainted(false);
        viewDetail.setOpaque(true);
        viewDetail.setBackground(black);
        viewDetail.setForeground(WHITE);

        back.setBorderPainted(false);
        back.setOpaque(true);
        back.setBackground(blue.darker());
        back.setForeground(white);

        close.setBorderPainted(false);
        close.setOpaque(true);
        close.setBackground(red.darker());
        close.setForeground(white);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==viewDetail){
            container.dispose();
            SearchAppointment mobileNumberEnter = new SearchAppointment(false, true);
        }

        else if (e.getSource()==addConsultation) {
            try {
                container.dispose();
                new MakeAppointment(false);
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        }

        else if (e.getSource() == deleteConsultation) {
            container.dispose();
            new SearchAppointment(false,true);
        }

        else if (e.getSource() == updateConsultation) {
            container.dispose();
            new SearchAppointment(true,false);
        }

        else if (e.getSource() == back) {
            try {
                Consultation.fileWrite();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            container.dispose();
            new Login();
        }

        else if (e.getSource() == close) {
            try {
                Consultation.getConsultations().sort(new AppointmentIDComparator());
                Consultation.fileWrite();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.exit(0);
        }
    }

}
