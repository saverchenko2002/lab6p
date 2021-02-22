package SaverchenkoGroup10Lab6VarC5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private static final int WIDTH = 950;
    private static final int HEIGHT = 650;

    private static final Field field = new Field();

    Cursor destructorCursor;
    Cursor constructorCursor;
    Cursor tpCursor1;
    Cursor tpCursor2;

    public MainFrame() {

        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);
        Image img = kit.getImage("src/icon.png");
        setIconImage(img);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu functionalMenu = menuBar.add(new JMenu("Добавить"));
        JMenuItem addBall = functionalMenu.add(new JMenuItem("Мяч"));
        JMenu furniture = new JMenu("Оборудование");
        functionalMenu.add(furniture);
        JMenu control = menuBar.add(new JMenu("Управление"));
        JMenuItem pauseMovement = control.add(new JMenuItem("Пауза"));
        JMenuItem continueMovement = control.add(new JMenuItem("Продолжить"));
        continueMovement.setEnabled(false);

        JMenuItem addConstructor = furniture.add(new JMenuItem("Конструктор"));
        JMenuItem addDestructor = furniture.add(new JMenuItem("Деструктор"));
        JMenuItem addTeleport = furniture.add(new JMenuItem("Телепорт"));


        Image cursorD = kit.getImage("src/destructorCursor.PNG");
        destructorCursor = kit.createCustomCursor(cursorD, new Point(0, 0), "cursor");
        Image cursorC = kit.getImage("src/constructorCursor.PNG");
        constructorCursor = kit.createCustomCursor(cursorC, new Point(0, 0), "cursor");

        addBall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.addBall();
            }
        });

        addBall.setAccelerator(KeyStroke.getKeyStroke("A"));

        pauseMovement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.pause();
                continueMovement.setEnabled(true);
                pauseMovement.setEnabled(false);
            }
        });

        pauseMovement.setAccelerator(KeyStroke.getKeyStroke("P"));

        continueMovement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.resume();
                pauseMovement.setEnabled(true);
                continueMovement.setEnabled(false);
            }
        });

        continueMovement.setAccelerator(KeyStroke.getKeyStroke("R"));

        addConstructor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.setSelected(Field.Selected.CIA);
                field.setCursor(constructorCursor);
            }
        });

        addConstructor.setAccelerator(KeyStroke.getKeyStroke("C"));

        addDestructor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.setSelected(Field.Selected.DIA);
                field.setCursor(destructorCursor);
            }
        });

        addDestructor.setAccelerator(KeyStroke.getKeyStroke("D"));
        addTeleport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

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
