package GUI;

import Classes.Consultation;
import GUI.Panels.DetailEnterPanel;
import GUI.Table.UserTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

public class MakeAppointment extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    private JPanel userTablePanel;
    private final DetailEnterPanel detailPanel;
    private UserTableModel userTableModel;

    public MakeAppointment(boolean isUpdate) throws IOException, ParseException {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,0,0,0);

        componentInitialize();


        gbc.gridy =0;
        gbc.gridx =0;
        panel.add(picturePanel(),gbc);

        detailPanel = new DetailEnterPanel(isUpdate, this, userTableModel);


        gbc.gridy =0;
        gbc.gridx =1;
        panel.add(detailPanel, gbc);

        gbc.insets = new Insets(20,10,20,30);
        gbc.gridy =0;
        gbc.gridx =2;
        panel.add(userTablePanel,gbc);

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
        displayField.setBounds(0,0,380,600);
        Image image = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Pictures/pic3.jpeg"))).getImage();
        Image imageScale = image.getScaledInstance(displayField.getWidth(), displayField.getHeight(), Image.SCALE_SMOOTH);
        displayField.setIcon(new ImageIcon(imageScale));


        //Upper text display


        JLabel n1Label = new JLabel("Westminster Skin Consultation Center",SwingConstants.CENTER);
        n1Label.setFont(new Font("DialogInput", Font.BOLD, 18));
        n1Label.setForeground(Color.BLACK);
        JLabel n2Label = new JLabel("Make Appointment",SwingConstants.CENTER);
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

    private void componentInitialize() throws ParseException {
        userTablePanel = new JPanel();
        userTableModel = new UserTableModel(Consultation.getConsultations());
        userTablePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20,30,30,30);

        JLabel topicLabel = new JLabel("Appointment Details",SwingConstants.CENTER);
        topicLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
        topicLabel.setForeground(Color.white);

        JTable userTable = new JTable(userTableModel);

        userTable.setRowHeight(30);
        userTable.setBackground(Color.white);
        userTable.setForeground(Color.black);
        JScrollPane scrollPane = new JScrollPane(userTable);

        userTable.setFillsViewportHeight(true);
        scrollPane.setBackground(Color.BLACK);
        userTablePanel.setBackground(Color.BLACK);
        TableColumnModel columnModel = userTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(35);
        columnModel.getColumn(2).setPreferredWidth(15);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(10);
        columnModel.getColumn(6).setPreferredWidth(10);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        userTable.setGridColor(Color.darkGray);
        columnModel.getColumn(0).setCellRenderer( centerRenderer );
        columnModel.getColumn(4).setCellRenderer( centerRenderer );
        columnModel.getColumn(2).setCellRenderer( centerRenderer );

        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width,scrollPane.getPreferredSize().height+45));
        userTable.setRowHeight((scrollPane.getPreferredSize().height)/10);

        gbc.gridx =1;
        gbc.gridy=0;
        userTablePanel.add(topicLabel, gbc);


        gbc.gridwidth = 2;
        gbc.gridy =1;
        gbc.gridx =0;
        userTablePanel.add(scrollPane, gbc);
    }

    int mouseX;
    int mouseY;
    public DetailEnterPanel getDetailPanel() {
        return detailPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent e) {}

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
