package SaverchenkoGroup10Lab6VarC5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private static final int WIDTH = 950;
    private static final int HEIGHT = 650;

    private final Field field = new Field();

    private final Cursor destructorCursor;
    private final Cursor constructorCursor;
    private final Cursor tpInCursor;
    private final Cursor tpOutCursor;

    public MainFrame() {

        super("Balls");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);
        Image img = kit.getImage("src/icon.png");
        setIconImage(img);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu functionalMenu = menuBar.add(new JMenu("Add"));
        JMenu furniture = new JMenu("Tools");
        functionalMenu.add(furniture);
        JMenu control = menuBar.add(new JMenu("Control"));

        JMenuItem addBall = functionalMenu.add(new JMenuItem("Ball"));
        JMenuItem pauseMovement = control.add(new JMenuItem("Pause"));
        JMenuItem continueMovement = control.add(new JMenuItem("Resume"));

        continueMovement.setEnabled(false);

        JMenuItem addConstructor = furniture.add(new JMenuItem("Constructor"));
        JMenuItem addDestructor = furniture.add(new JMenuItem("Destructor"));
        JMenuItem addTeleport = furniture.add(new JMenuItem("Teleport"));
        JMenuItem clearAllField = control.add(new JMenuItem("Clear all"));

        addBall.setAccelerator(KeyStroke.getKeyStroke("A"));
        pauseMovement.setAccelerator(KeyStroke.getKeyStroke("P"));
        addConstructor.setAccelerator(KeyStroke.getKeyStroke("C"));
        addDestructor.setAccelerator(KeyStroke.getKeyStroke("D"));
        continueMovement.setAccelerator(KeyStroke.getKeyStroke("R"));
        addTeleport.setAccelerator(KeyStroke.getKeyStroke("T"));
        clearAllField.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));

        Image imgDestructorCursor = kit.getImage("src/destructorCursor.PNG");
        destructorCursor = kit.createCustomCursor(imgDestructorCursor, new Point(0, 0), "cursor");
        Image imgConstructorCursor = kit.getImage("src/constructorCursor.PNG");
        constructorCursor = kit.createCustomCursor(imgConstructorCursor, new Point(0, 0), "cursor");
        Image imgTpInCursor = kit.createImage("src/tpInCursor.png");
        tpInCursor = kit.createCustomCursor(imgTpInCursor, new Point(0, 0), "cursor");
        Image imgTpOutCursor = kit.createImage("src/tpOutCursor.png");
        tpOutCursor = kit.createCustomCursor(imgTpOutCursor, new Point(0, 0), "cursor");

        addBall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.addBall();
            }
        });

        pauseMovement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.pause();
                continueMovement.setEnabled(true);
                pauseMovement.setEnabled(false);
            }
        });

        continueMovement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.resume();
                pauseMovement.setEnabled(true);
                continueMovement.setEnabled(false);
            }
        });

        addConstructor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.setSelected(Field.Selected.CONSTRUCTOR_IS_SELECTED);
                field.setCursor(constructorCursor);
            }
        });

        addDestructor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.setSelected(Field.Selected.DESTRUCTOR_IS_SELECTED);
                field.setCursor(destructorCursor);
            }
        });

        addTeleport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Portal.nextId % 2 == 0) {
                    field.setSelected(Field.Selected.PORTAL_INPUT);
                    field.setCursor(tpInCursor);
                } else {
                    field.setSelected(Field.Selected.PORTAL_OUTPUT);
                    field.setCursor(tpOutCursor);
                }
            }
        });

        clearAllField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.getPortals().clear();
                field.getObjects().clear();
                field.killAllBalls();
                field.getBalls().clear();
                field.setSelected(Field.Selected.NONE);
                field.setCursor(Cursor.getDefaultCursor());
            }
        });

        getContentPane().add(field, BorderLayout.CENTER);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
    }
}