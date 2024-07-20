package GUI;

import Classes.Doctor;
import Classes.SkinConsultationManager;
import GUI.Comparator.AlphabeticComparatorDoctor;
import GUI.Comparator.ComparatorDoctorID;
import GUI.Panels.MainMenuPanel;
import GUI.Table.DoctorTableModel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.awt.Color.*;

public class MainGUI extends JFrame implements MouseListener, MouseMotionListener, ActionListener {


    private JButton alphabeticallyInitialize;
    private DoctorTableModel tableModel;
    private boolean isAlphabetic;
    public MainGUI() throws IOException{
        this.isAlphabetic = false;
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel doctorTablePanel = doctorDetailsTable();
        JPanel mainButtonPanel = new MainMenuPanel(this);

        gbc.gridy =0;
        gbc.gridx =0;
        panel.add(picturePanel(), gbc);

        gbc.insets=new Insets(0,0,0,30);
        gbc.gridy =0;
        gbc.gridx =2;
        panel.add(doctorTablePanel,gbc);

        gbc.gridy =0;
        gbc.gridx =1;
        panel.add(mainButtonPanel, gbc);

        alphabeticallyInitialize.addActionListener(this);
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
        Image image2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Pictures/pic2.jpeg"))).getImage();
        Image imageScale = image2.getScaledInstance(displayField.getWidth(), displayField.getHeight(), Image.SCALE_SMOOTH);
        displayField.setIcon(new ImageIcon(imageScale));


        //Upper text display
        JLabel n1Label = new JLabel("Westminster Skin Consultation Center",SwingConstants.CENTER);
        n1Label.setFont(new Font("DialogInput", Font.BOLD, 20));
        n1Label.setForeground(Color.BLACK);
        JLabel n2Label = new JLabel("Main Menu",SwingConstants.CENTER);
        n2Label.setFont(new Font("DialogInput", Font.BOLD, 40));
        n2Label.setForeground(Color.BLACK);

        //Adding Components in to panel
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

    private JPanel doctorDetailsTable() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20,30,30,30);

        JLabel topicLabel = new JLabel("Doctor Details",SwingConstants.CENTER);
        topicLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
        topicLabel.setForeground(Color.white);

        tableModel = new DoctorTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(25);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);

        table.setFillsViewportHeight(true);
        scrollPane.setBackground(Color.black);
        mainPanel.setBackground(Color.black);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(4).setPreferredWidth(10);
        columnModel.getColumn(1).setMinWidth(60);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        columnModel.getColumn(0).setCellRenderer( centerRenderer );
        columnModel.getColumn(4).setCellRenderer( centerRenderer );
        columnModel.getColumn(2).setCellRenderer( centerRenderer );
        table.setGridColor(Color.BLACK);

        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width,scrollPane.getPreferredSize().height+45));
        table.setRowHeight((scrollPane.getPreferredSize().height)/10);

        alphabeticallyInitialize = new JButton("Click here to sort by Doctor Name ");
        alphabeticallyInitialize.setFont(new Font("SansSerif", Font.BOLD, 20));
        alphabeticallyInitialize.setBorderPainted(false);
        alphabeticallyInitialize.setOpaque(true);
        alphabeticallyInitialize.setBackground(orange.darker());
        alphabeticallyInitialize.setForeground(white);

        gbc.gridx =1;
        gbc.gridy=0;
        mainPanel.add(topicLabel, gbc);


        gbc.gridwidth = 2;
        gbc.gridy =1;
        gbc.gridx =0;
        mainPanel.add(scrollPane, gbc);

        gbc.gridx =1;
        gbc.gridy=9;
        mainPanel.add(alphabeticallyInitialize, gbc);

        return mainPanel;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== alphabeticallyInitialize) {
            if (!isAlphabetic) {
                ArrayList<Doctor> d = tableModel.getMyList();
                d.sort(new AlphabeticComparatorDoctor());
                tableModel.fireTableDataChanged();
                alphabeticallyInitialize.setText("Click here to sort by Doctor ID");
                isAlphabetic = true;
            }else {
                Doctor[] consultations = SkinConsultationManager.doctorList;
                ArrayList<Doctor> doctors = new ArrayList<>();
                for (Doctor consultation : consultations) {
                    if (consultation != null) doctors.add(consultation);
                }
                doctors.sort(new ComparatorDoctorID());
                tableModel.setMyList(doctors);
                tableModel.fireTableDataChanged();

                alphabeticallyInitialize.setText("Click here to sort by Doctor Name");
                isAlphabetic = false;
            }
        }
    }
}
