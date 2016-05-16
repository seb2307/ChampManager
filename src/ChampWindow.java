import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by xxx on 15.05.2016.
 */
public class ChampWindow extends JFrame implements KeyListener{
    public JTable table1;
    private boolean DEBUG = true;

    private JTextField NameField;
    private JTextField SurnameField;
    private JTextField WeightField;
    private JTextField AgeField;
    private JButton addCompetitorButton;
    private JButton exportButton;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel nameTextPanel;
    private JPanel textPanel;
    private JLabel infoAddingLabel;
    private JButton deletePreviousButton;
    private JPanel tablePanel;
    private JScrollBar scrollBar1;
    private JScrollPane scrollPane;
    //    public Object rowData;
//    public Object columnNames;
    int i= 1;
    boolean flag = true;
    //    public Object[][]  rowData = { { "Name", "Surname", "Weight" , "Age"},
//        { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
//    public Object[] columnNames= { "Column One", "Column Two", "Column Three" };
    public DefaultTableModel model;
    private boolean focus = false;

    ChampWindow()
    {


//        NameField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
////                model.addRow(new Object[] { "v1", "v2", "v3", "v4" });
////                model.setValueAt(NameField.getText(), i, 0);
////
////
////                i++;
//            }
//        });

        addCompetitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkConsistency();
            }
        });
        deletePreviousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (i > 0) {
                    i--;
                    model.removeRow(i);
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StaxWriter configFile = new StaxWriter(model, i);
                configFile.setFile("config2.xml");
                try {
                    configFile.saveConfig();
                    infoAddingLabel.setText("Exported");
                } catch (Exception b) {
                    b.printStackTrace();
                }
            }
        });

        AgeField.addKeyListener(this);
        AgeField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                focus = true;
            }

            @Override
            public void focusLost(FocusEvent e) {
                //Your code here
            }
        });





    }

    public void checkConsistency()
    {
        if(NameField.getText().equals("") || SurnameField.getText().equals("") || AgeField.getText().equals("") || WeightField.getText().equals(""))
            infoAddingLabel.setText("Fill all text Fields");
        else
        {
            try {
                Integer.parseInt(WeightField.getText());
            }
            catch (NumberFormatException a) {

                infoAddingLabel.setText("Weight must be a number");
                WeightField.setText("");
                flag = false;
            }
            try {
                Integer.parseInt(AgeField.getText());
            }
            catch (NumberFormatException a) {

                infoAddingLabel.setText("Age must be a number");
                AgeField.setText("");
                flag = false;
            }
            if(flag) {
                model.addRow(new Object[]{});
                model.setValueAt(i, i, 0);
                model.setValueAt(NameField.getText(), i, 1);
                model.setValueAt(SurnameField.getText(), i, 2);
                model.setValueAt(WeightField.getText(), i, 3);
                model.setValueAt(AgeField.getText(), i, 4);
                i++;
                NameField.setText("");
                SurnameField.setText("");
                WeightField.setText("");
                AgeField.setText("");
            }
        }
        flag = true;
    }

    public int displayInfo(KeyEvent e, String keyStatus) {
        int keyCode=0;
        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
             keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode
                    + " ("
                    + KeyEvent.getKeyText(keyCode)
                    + ")";
        }
        int modifiersEx = e.getModifiersEx();
        String modString = "extended modifiers = " + modifiersEx;
        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
        if (tmpString.length() > 0) {
            modString += " (" + tmpString + ")";
        } else {
            modString += " (no extended modifiers)";
        }

        String actionString = "action key? ";
        if (e.isActionKey()) {
            actionString += "YES";
        } else {
            actionString += "NO";
        }

        String locationString = "key location: ";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
            locationString += "standard";
        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
            locationString += "left";
        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
            locationString += "right";
        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
            locationString += "numpad";
        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
            locationString += "unknown";
        }
        System.out.println(keyString);
        return keyCode;
    }


    public static void main(String[] args) {


// take the menu bar off the jframe
        System.setProperty("apple.laf.useScreenMenuBar", "true");

// set the name of the application menu item
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Image");

        JFrame frame = new JFrame("Champ Manager");
        frame.setBounds(300,300, 800, 200);

        ChampWindow window = new ChampWindow();
//        frame.setSize(800,200);
        frame.setContentPane(window.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        frame.pack();
//        frame.setVisible(true);


//        window.scrollPane = new JScrollPane(window.table1);

//        frame.add(window.scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);





    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent m) {
        int pressedKey = this.displayInfo(m, "KEY PRESSED: ");
        if(pressedKey == 10)
            checkConsistency();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void createUIComponents() {
        model = new DefaultTableModel();
        table1= new JTable(model);
        model.addColumn("Number");
        model.addColumn("Name");
        model.addColumn("Surname");
        model.addColumn("Weight");
        model.addColumn("Age");



        model.addRow(new Object[] { "Number", "Name", "Surname", "Weight", "Age"});

//        table1= new JTable(rowData,columnNames);
        table1.setPreferredScrollableViewportSize(new Dimension(500, 400));
        table1.setFillsViewportHeight(true);
        if (DEBUG) {
            table1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table1);
                }
            });
        }
    }


    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}