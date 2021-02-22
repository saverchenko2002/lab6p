package SaverchenkoGroup10Lab6VarC5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;


public class Field extends JPanel {

    /*public enum Selected {
        NONE,
        DIA,
        CIA,
        TP1IA,
        TP2IA
    }*/

    private boolean paused;
    private boolean destructorIA = false;
    private boolean constructorIA = false;
    private final LinkedList<BouncingBall> balls = new LinkedList<>();

    private final Font hintFont;
    public int hintX;
    public int hintY;
    boolean defaultCursorTrigger = false;

    ArrayList<Obj> obj = new ArrayList<>();


    public Field() {
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
        FontRenderContext context = canvas.getFontRenderContext();
        Rectangle2D bounds = hintFont.getStringBounds("hot-dog", context);

        if (destructorIA) {
            canvas.setColor(Color.magenta);
            canvas.drawString("press LMB to install destructor", hintX, (float) (hintY + bounds.getWidth()));
        }

        else if (constructorIA) {

        }
        else {

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

    public void fu () {
        for (BouncingBall ball : balls) {
            for (Obj obj1 : obj) {
                if ((ball.getX() - ball.radius >= obj1.getX() && ball.getX() + ball.radius <= obj1.getX()+obj1.getSize())
                        && (ball.getY() + ball.radius <= obj1.getY()+obj1.getSize() && ball.getY() - ball.radius > obj1.getY())) {
                    int saveIndex = ball.getNumber();
                    for (int i = ball.getNumber()+1;i<balls.size();i++)
                        balls.get(i).setNumber(i-1);
                    balls.remove(saveIndex);
                    return;
                }
            }
        }
    }
    public void addBall() {
        BouncingBall ball = new BouncingBall(this);
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

    //TODO сделать обобщенную функцию для офа
    public class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                if (destructorIA) {
                    obj.add(new Obj(Obj.Type.DESTRUCTOR,e.getX(),e.getY()));
                    setDestructorIA(false);
                }
                if (constructorIA) {
                    obj.add(new Obj(Obj.Type.CONSTRUCTOR,e.getX(),e.getY()));
                    setConstructorIA(false);
                }
                defaultCursorTrigger = true;
                setCursor(Cursor.getDefaultCursor());
                defaultCursorTrigger = false;
            }
        }
    }

    public class MouseMotionHandler implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (destructorIA) {
                hintX=e.getX();
                hintY=e.getY();
            }
        }
    }

    public void setDestructorIA (boolean value) {
        destructorIA=value;
    }

    public void setConstructorIA (boolean value) {
        constructorIA = value;
    }

    public boolean getDestructorIA () {
        return destructorIA;
    }

    public boolean getDefaultCursorTrigger () {
        return defaultCursorTrigger;
    }
}
