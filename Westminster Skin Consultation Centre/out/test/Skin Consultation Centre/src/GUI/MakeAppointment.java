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

        detailPanel = new DetailEnterPanel(isUpdate, this, userTableModel);
        gbc.insets = new Insets(30,30,30,10);
        gbc.gridwidth = 1;
        gbc.gridy =1;
        gbc.gridx =0;
        panel.add(detailPanel, gbc);

        gbc.insets = new Insets(10,10,10,10);
        gbc.gridy =1;
        gbc.gridx =1;
        panel.add(userTablePanel,gbc);

        addMouseListener(this);
        addMouseMotionListener(this);

        add(panel);
        panel.setBackground(Color.BLACK);
        setUndecorated(true);
        pack();
        setLocation(300,75);
        setVisible(true);
    }


    private void componentInitialize() throws ParseException {
        userTablePanel = new JPanel();

        userTableModel = new UserTableModel(Consultation.getConsultations());
        JTable userTable = new JTable(userTableModel);
        userTable.setRowHeight(30);
        userTable.setBackground(Color.BLACK);
        userTable.setForeground(Color.WHITE);
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

        scrollPane.setPreferredSize(new Dimension(600,500));
        userTablePanel.add(scrollPane);
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
