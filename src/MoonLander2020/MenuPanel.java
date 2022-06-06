package MoonLander2020;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A menü grafikus megjelenítéséért felelős osztály.
 * @author Istenes Márton
 */
public class MenuPanel extends JPanel {

    /**
     * A képernyőn elhelyezett textfield, amely az akutális játkékos nevét tárolja.
     */
    private JTextField tfield;

    /**
     * A képernyőn elhelyezett lenyitható "combobox", mellyel az aktuális játékhoz ki lehet választani a képernyőszélességet.
     */
    private final JComboBox comboBox;

    /**
     * A panelhoz tartozó menüt tárolja.
     */
    private final Menu menu;

    /**
     * A menü háttere.
     */
    Image background = Toolkit.getDefaultToolkit().getImage("background.png");

    /**
     * A menü hátterén elhelyezett holdkomp.
     */
    Image holdkomp = Toolkit.getDefaultToolkit().getImage("lunarmodule.png");

    /**
     * Felépíti a menüt és feltölti a megfelelő adatokkal Két alpanelből áll.
     * Az alsó rész tartalmazza a dicsőséglistát a felső a jaték elindításához szükséges dolgokat.
     * @param m A panelhoz tartozó főmenü.
     */
    public MenuPanel(Menu m) {

        menu = m;

        //Főpanel felépítése
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 500));

        //Felső panel felépítése
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(400,90));
        panel.setBackground(Color.black);
        panel.setVisible(true);
        GridBagConstraints c = new GridBagConstraints();

        JLabel label = new JLabel("Name: ");
        label.setForeground(Color.white);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(label,c);

        tfield = new JTextField(10);
        tfield.setBackground(Color.black);
        tfield.setForeground(Color.white);
        tfield.setText("Player");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(tfield,c);

        label = new JLabel("Screen width: ");
        label.setForeground(Color.white);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(label,c);

        Object[] obj={500,600,700,800,900,1000,1100,1200,1300,1400,1500};
        comboBox=new JComboBox(obj);
        comboBox.setBackground(Color.black);
        comboBox.setForeground(Color.white);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(comboBox,c);

        JButton gomb = new JButton("Start");
        gomb.setBackground(Color.black);
        gomb.setForeground(Color.white);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(gomb,c);

        //Alsó panel
        JTable table = new JTable(m.getData());
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.white);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.black);
        table.setForeground(Color.white);
        table.setVisible(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400,100));
        scrollPane.setVisible(true);

        //Alpanelek hozzáadása
        add(panel,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.SOUTH);

        //Listenerek
        ActionListener al = new StartButtonActionListener();
        gomb.addActionListener(al);
    }

    /**
     * Kirajzolja a héttér grafikus elemeit.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
        g.drawImage(holdkomp, 234, 260, this);

        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 60));
        g.drawString("MoonLander",75,225);
        g.setFont(new Font("Monospaced:", Font.BOLD, 20));
        g.drawString("Scoreboard:",190,390);
    }

    public JTextField getTfield() {
        return tfield;
    }

    public JComboBox getComboBox() {
        return comboBox;
    }

    /**
     * Fiygeli a start gombot.
     */
    public class StartButtonActionListener implements ActionListener {

        /**
         * Ha meghívodóik elindítja a panelhoz tartozó főmenüben a játékot.
         * @param ae Az esemény.
         */
        public void actionPerformed(ActionEvent ae) {
            menu.setStart(true);
        }
    }
}

