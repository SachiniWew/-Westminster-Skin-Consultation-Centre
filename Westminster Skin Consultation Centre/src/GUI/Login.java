package GUI;

import Classes.Consultation;
import Classes.ManagerOptions;
import Classes.SkinConsultationManager;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

import static java.awt.Color.*;

public class Login extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
    private JPanel managerPanel;
    private JButton managerButton, close, userButton, submit,reset, back;
    private int mouseX, mouseY;
    private JTextField userName;
    private JPasswordField password;


    public Login(){


        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;

        //adding leftSidePanel to panel
        gbc.gridx = 0;
        panel.add(leftSidePanel(), gbc);

        //adding rightSidePanel to panel
        gbc.gridy = 0;
        gbc.gridx = 1;
        panel.add(rightSidePanel(), gbc);
        add(panel);

        //Set button actions
        managerButton.addActionListener(this);
        close.addActionListener(this);
        back.addActionListener(this);
        userButton.addActionListener(this);
        submit.addActionListener(this);
        reset.addActionListener(this);


        setUndecorated(true);
        pack();
        setLocation(20,50);
        setVisible(true);
    }

    public JPanel leftSidePanel(){
        JPanel rootPanelLeft = new JPanel();
        Color cp = new Color(222,222,222);
        rootPanelLeft.setBackground(cp);
        GridBagLayout bL =new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanelLeft.setLayout(bL);

        //Picture display
        JLabel displayField = new JLabel();
        displayField.setBounds(0,0,800,600);
        Image image = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Pictures/pic1.jpeg"))).getImage();
        Image imageScale = image.getScaledInstance(displayField.getWidth(), displayField.getHeight(), Image.SCALE_SMOOTH);
        displayField.setIcon(new ImageIcon(imageScale));


        //Upper text display
        JLabel nLabel = new JLabel("Westminster Skin Consultation Centre",SwingConstants.CENTER);
        nLabel.setFont(new Font("DialogInput", Font.BOLD, 40));
        nLabel.setForeground(Color.BLACK);
        JLabel sLabel = new JLabel("For the natural healthy-looking Skin",SwingConstants.CENTER);
        sLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
        sLabel.setForeground(Color.BLACK);

        //Add component to the panel
        gbc.insets = new Insets(10,30,10,30);
        gbc.gridy =0;
        gbc.gridx =0;
        rootPanelLeft.add(nLabel, gbc);

        gbc.gridy =1;
        gbc.gridx=0;
        rootPanelLeft.add(sLabel, gbc);

        gbc.gridy =2;
        gbc.gridx =0;
        rootPanelLeft.add(displayField, gbc);

        return rootPanelLeft;
    }

    public JPanel rightSidePanel(){
        Color r1 = new Color(115,27,24);
        Color g1 = new Color(29,82,12);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(g1);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Right side customize
        JLabel loginLabel = new JLabel("LOGIN",SwingConstants.CENTER);
        loginLabel.setFont(new Font("DialogInput", Font.BOLD , 40));
        loginLabel.setForeground(Color.white);
        managerButton = new JButton("Manager Login");
        userButton = new JButton("User Login");
        close = new JButton("Close");
        managerButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        userButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        close.setFont(new Font("SansSerif", Font.BOLD, 20));
        managerButton.setBorderPainted(false);
        managerButton.setOpaque(true);
        managerButton.setBackground(black);
        userButton.setBorderPainted(false);
        userButton.setOpaque(true);
        userButton.setBackground(black);
        close.setBorderPainted(false);
        close.setOpaque(true);
        close.setBackground(r1);
        managerButton.setForeground(white);
        userButton.setForeground(Color.white);
        close.setForeground(white);

        //Add component to the panel
        gbc.insets = new Insets(100,30,30,30);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(loginLabel, gbc);

        JPanel managerSubmitPanel = managerLogin();
        managerSubmitPanel.setVisible(false);

        gbc.insets = new Insets(10,30,10,30);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth=7;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        rightPanel.add(managerButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth=7;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        rightPanel.add(userButton, gbc);

        gbc.insets = new Insets(10,30,30,30);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth=7;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        rightPanel.add(close, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        rightPanel.add(managerSubmitPanel, gbc);

        return rightPanel;
    }


    //After click manger login button
    public JPanel managerLogin(){
        managerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcManager = new GridBagConstraints();
        gbcManager.fill = GridBagConstraints.HORIZONTAL;
        gbcManager.insets = new Insets(10,30,10,30);

        userName = new JTextField();
        password = new JPasswordField();
        password.setEchoChar('*');
        submit = new JButton("Submit");
        reset = new JButton("Reset");
        back = new JButton("Back");
        JLabel passwordLabel = new JLabel("Enter your Password",SwingConstants.LEFT);
        JLabel usernameLabel = new JLabel("Enter your Username",SwingConstants.LEFT);

        //formatting
        passwordLabel.setForeground(BLACK);
        usernameLabel.setForeground(BLACK);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD , 16));
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD , 16));
        submit.setBorderPainted(false);
        submit.setOpaque(true);
        submit.setBackground(Color.red.darker());
        reset.setBorderPainted(false);
        reset.setOpaque(true);
        reset.setBackground(Color.green);
        back.setBorderPainted(false);
        back.setOpaque(true);
        back.setBackground(Color.BLUE);
        submit.setForeground(Color.BLACK);
        reset.setForeground(Color.BLACK);
        back.setForeground(Color.BLACK);
        Color g1 = new Color(29,82,12);
        managerPanel.setBackground(g1);

        //Adding component to the panel
        gbcManager.insets = new Insets(30,5,0,5);
        gbcManager.ipadx = 50;
        gbcManager.gridwidth = 3;
        gbcManager.gridy=0;
        managerPanel.add(usernameLabel, gbcManager);

        gbcManager.insets = new Insets(10,5,10,5);
        gbcManager.ipady = 5;
        gbcManager.gridy=1;
        managerPanel.add(userName, gbcManager);

        gbcManager.insets = new Insets(10,5,0,5);
        gbcManager.ipady = 0;
        gbcManager.gridy=2;
        managerPanel.add(passwordLabel, gbcManager);

        gbcManager.insets = new Insets(10,5,10,5);
        gbcManager.ipady = 5;
        gbcManager.gridy=3;
        managerPanel.add(password, gbcManager);

        gbcManager.insets = new Insets(20,5,30,5);
        gbcManager.ipady = 0;
        gbcManager.gridwidth = 1;
        gbcManager.gridy=4;
        gbcManager.gridx=0;
        managerPanel.add(submit, gbcManager);

        gbcManager.gridy=4;
        gbcManager.gridx=1;
        managerPanel.add(reset, gbcManager);

        gbcManager.gridy=4;
        gbcManager.gridx=2;
        managerPanel.add(back, gbcManager);

        return managerPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (close.equals(source)) {
            System.exit(0);
        } else if (managerButton.equals(source)) {
            userButton.setVisible(false);
            managerPanel.setVisible(true);
            pack();
        } else if (e.getSource() == userButton) {
            try {
                userButton.setVisible(true);
                SkinConsultationManager.readDoctorFile();
                Consultation.readFile(this);
                new MainGUI();
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        } else if (e.getSource()==submit) {
            char[] passwordChar = password.getPassword();
            String passwordText = new String(passwordChar);
            String usernameText = userName.getText();

            if (passwordText.equals("") || usernameText.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter your Username and Password.", "Warning", JOptionPane.WARNING_MESSAGE);
            }

            else {
                try {
                    ManagerOptions.readManagerFile();
                    int userExist = ManagerOptions.isManagerObjectExist(usernameText, passwordText);
                    System.out.println(userExist);
                    if (userExist==8) JOptionPane.showMessageDialog(this, "Wrong Password. Try again","Error", JOptionPane.ERROR_MESSAGE);
                    else if (userExist == 10) {
                        JOptionPane.showMessageDialog(this, "User doesn't exists","Error", JOptionPane.ERROR_MESSAGE);
                    } else if (userExist < 4) {
                        this.dispose();
                        new ManagerOptions(usernameText,passwordText);
                        new Login();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        } else if (e.getSource()==reset) {
            userName.setText(""); password.setText("");

        } else if (e.getSource() == back) {
            managerPanel.setVisible(false);
            userButton.setVisible(true);
            pack();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

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
    public static void main(String[] args) {
        new Login();
    }
}

