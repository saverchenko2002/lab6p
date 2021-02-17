package SaverchenkoGroup10Lab6VarC5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class Field extends JPanel {

    private boolean paused;
    private LinkedList<BouncingBall> balls = new LinkedList<BouncingBall>();

    public Field() {
        setBackground(Color.WHITE);
        Timer repaintTimer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        repaintTimer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (BouncingBall ball : balls) {
            ball.paint(canvas);
        }
    }

    public void addBall() {
        balls.add(new BouncingBall(this)); //чтобы мяч понимал по какому полю прыгать передаем ссылку на приколюху нашу филдовскую
    }

    public  synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized void canMove (BouncingBall ball) throws InterruptedException {
        if (paused)
            wait();
    }
}
