package SaverchenkoGroup10Lab6VarC5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class Field extends JPanel {

    public enum Selected {
        NONE,
        DIA,
        CIA,
        TP1IA,
        TP2IA
    }

    private Selected selected;

    private boolean paused;
    private final LinkedList<BouncingBall> balls = new LinkedList<>();

    private final Font hintFont;
    private final float hintMove = 50;
    public int hintX;
    public int hintY;

    ArrayList<Obj> obj = new ArrayList<>();


    public Field() {
        selected = Selected.NONE;
        setBackground(Color.WHITE);
        Timer repaintTimer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        repaintTimer.start();

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());

        hintFont = new Font(Font.SANS_SERIF, Font.PLAIN + Font.ITALIC, 14);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (BouncingBall ball : balls) {
            ball.paint(canvas);
        }
        canvas.setFont(hintFont);

        switch (selected) {
            case DIA: {
                canvas.setColor(Color.blue);
                canvas.drawString("press LMB to install destructor", hintX, (float) (hintY+hintMove));
                break;
            }
            case CIA: {
                canvas.setColor(Color.blue);
                canvas.drawString("press LMB to install constructor", hintX, (float) (hintY+hintMove));
                break;
                }
            case TP1IA: {

                }
            case TP2IA : {

            }
            case NONE: {

            }
        }

        if (obj.size()!=0) {
            for (Obj obj1 : obj) {
                obj1.paint(canvas);
            }
        }

        if (balls.size()!=0 && obj.size()!=0) {
            fu();

        }
    }
//TODO
    public void fu () {
        for (BouncingBall ball : balls) {
            for (Obj obj1 : obj) {
                if (ball.intersect(obj1)) {
                    switch (obj1.getType()) {
                        case DESTRUCTOR: {
                                int saveIndex = ball.getNumber();
                                for (int i = ball.getNumber() + 1; i < balls.size(); i++)
                                    balls.get(i).setNumber(i - 1);
                                balls.remove(saveIndex);
                                return;
                        }
                        case CONSTRUCTOR: {

                            BouncingBall ballCopy = new BouncingBall(this, ball);
                            addBall(ballCopy);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void addBall() {
        BouncingBall ball = new BouncingBall(this);
        ball.setNumber(balls.size());
        balls.add(ball);
    }

    public void addBall(BouncingBall ball) {
        ball.setNumber(balls.size());
        balls.add(ball);
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized void canMove(BouncingBall ball) throws InterruptedException {
        if (paused)
            wait();
    }

    public class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {

                switch (selected) {
                    case DIA: obj.add(new Obj(Obj.Type.DESTRUCTOR, e.getX(),e.getY())); break;
                    case CIA: obj.add(new Obj(Obj.Type.CONSTRUCTOR,e.getX(),e.getY())); break;
                    case TP1IA: {

                    }
                    case TP2IA: {

                    }
                    case NONE: {

                    }
                }
                selected = Selected.NONE;
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    public class MouseMotionHandler implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (selected!=Selected.NONE) {
                hintX=e.getX();
                hintY=e.getY();
            }
        }
    }

    public Selected getSelected () {
        return selected;
    }

    public void setSelected (Selected selected) {
        this.selected=selected;
    }

}
