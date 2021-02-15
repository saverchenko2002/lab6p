package SaverchenkoGroup10Lab6VarC5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private static final int WIDTH = 950;
    private static final int HEIGHT = 650;

    public MainFrame () {

        super("Программирование и синхронизация потоков");
        setSize(WIDTH,HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);
        Image img = kit.getImage("src/icon.png");
        setIconImage(img);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu functionalMenu  = menuBar.add(new JMenu("Добавить"));
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

        addBall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        pauseMovement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                continueMovement.setEnabled(true);
                pauseMovement.setEnabled(false);
            }
        });

        continueMovement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pauseMovement.setEnabled(true);
                continueMovement.setEnabled(false);
            }
        });

        addConstructor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        addDestructor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        addTeleport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    public static void main (String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}