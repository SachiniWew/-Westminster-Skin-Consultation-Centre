package GUI;

import Classes.Consultation;
import Classes.ManagerOptions;
import Classes.SkinConsultationManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Login extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
    private JPanel managerPanel;
    private JPanel panelNorth;
    private JButton managerButton, close, userButton, submit,reset, back;
    private int mouseX, mouseY;
    private JTextField userName;
    private JPasswordField password;


    public Login(){
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        panel.add(upperSidePanel(), gbc);

        gbc.gridy = 2;
        panel.add(belowSidePanel(), gbc);
        add(panel);

        //Set button actions
        managerButton.addActionListener(this);
        close.addActionListener(this);
        back.addActionListener(this);
        userButton.addActionListener(this);
        submit.addActionListener(this);
        reset.addActionListener(this);
        panelNorth.addMouseListener(this);
        panelNorth.addMouseMotionListener(this);

        setBackground(Color.BLACK);
        setUndecorated(true);
        pack();
        setLocation(600,75);
        setVisible(true);

    }
    public JPanel upperSidePanel(){
        JPanel panelWest = new JPanel();
        panelNorth = new JPanel();
        GridBagLayout bL =new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panelWest.setLayout(bL);

        //Logo display
        JLabel displayField = new JLabel();
        displayField.setSize(440,240);
        Image image = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Materials/Logo.jpg"))).getImage();
        Image imageScale = image.getScaledInstance(displayField.getWidth(), displayField.getHeight(), Image.SCALE_SMOOTH);
        displayField.setIcon(new ImageIcon(imageScale));


        //Upper text display and add component into panels
        JLabel label = new JLabel("Your beauty is our profession",SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 25));
        label.setForeground(Color.WHITE);
        panelNorth.add(label, BorderLayout.CENTER);
        panelNorth.setBackground(Color.GREEN.darker());

        gbc.gridy =0;
        panelWest.add(panelNorth, gbc);

        gbc.gridy =1;
        panelWest.add(displayField, gbc);
        panelWest.setBackground(Color.GREEN.darker());

        return panelWest;
    }

    public JPanel belowSidePanel(){
        JPanel panelBelow = new JPanel();
        panelBelow.setLayout(new GridBagLayout());
        panelBelow.setBackground(Color.black);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Right side customize
        JLabel label1 = new JLabel("L O G I N",SwingConstants.CENTER);
        label1.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
        JLabel managerLabel = new JLabel("Manager Login :",SwingConstants.LEFT);
        managerLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
        JLabel userLabel = new JLabel("User Login       :",SwingConstants.LEFT);
        userLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
        managerButton = new JButton("Manager");
        userButton = new JButton("User");
        close = new JButton("Close");

        label1.setForeground(Color.WHITE);
        userLabel.setForeground(Color.lightGray);
        managerLabel.setForeground(Color.lightGray);
        close.setFont(new Font("Serif", Font.BOLD, 14));
        managerButton.setFont(new Font("Serif", Font.PLAIN, 14));
        userButton.setFont(new Font("Serif", Font.PLAIN, 14));
        managerButton.setBackground(Color.GREEN.darker());
        userButton.setBackground(Color.GREEN.darker());
        close.setBackground(Color.RED);
        managerButton.setForeground(Color.WHITE);
        userButton.setForeground(Color.WHITE);
        close.setForeground(Color.WHITE);

        gbc.insets = new Insets(30,30,30,30);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBelow.add(label1, gbc);

        JPanel managerSubmitPanel = managerLogin();
        managerSubmitPanel.setVisible(false);

        gbc.insets = new Insets(10,30,10,30);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelBelow.add(managerLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelBelow.add(managerButton, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelBelow.add(managerSubmitPanel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelBelow.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panelBelow.add(userButton, gbc);

        gbc.insets = new Insets(10,30,30,30);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelBelow.add(close, gbc);

        return panelBelow;
    }

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
        JLabel passwordLabel = new JLabel("Enter your Password :",SwingConstants.LEFT);
        JLabel usernameLabel = new JLabel("Enter your Username :",SwingConstants.LEFT);

        passwordLabel.setForeground(Color.lightGray);
        usernameLabel.setForeground(Color.lightGray);
        passwordLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
        usernameLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
        submit.setBackground(Color.cyan.darker());
        reset.setBackground(Color.red);
        back.setBackground(Color.magenta.darker());
        submit.setForeground(Color.WHITE);
        reset.setForeground(Color.WHITE);
        back.setForeground(Color.WHITE);


        managerPanel.setBackground(Color.BLACK);
        managerPanel.setBorder(new LineBorder(Color.GRAY,1,true));

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
            userButton.setEnabled(false);
            userButton.setBackground(Color.lightGray);
            managerPanel.setVisible(true);
            pack();
        } else if (e.getSource() == userButton) {
            try {
                SkinConsultationManager.readFile();
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
                JOptionPane.showMessageDialog(this, "The Password or Username field cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            else if (passwordText.contains(" ") || usernameText.equals(" ")) {
                JOptionPane.showMessageDialog(this, "The Password field or Username field doesn't have any spaces.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            else {
                try {
                    ManagerOptions.readFile();
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
            userButton.setEnabled(true);
            userButton.setBackground(Color.GREEN.darker());
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
    public static void main(String[] args) throws ParseException, IOException {
        new Login();
    }
}

