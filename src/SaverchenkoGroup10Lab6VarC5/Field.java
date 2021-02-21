package SaverchenkoGroup10Lab6VarC5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class Field extends JPanel {

    private boolean paused;
    private boolean destructorIA = false;
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
        if (obj.size()!=0) {
            for (Obj obj1 : obj) {
                GeneralPath marker = new GeneralPath();
                canvas.setColor(Color.red);
                marker.moveTo(obj1.getX(),obj1.getY());
                marker.lineTo(obj1.getX()+50,obj1.getY()+50);
                marker.moveTo(marker.getCurrentPoint().getX()-25,marker.getCurrentPoint().getY()-25);
                marker.lineTo(marker.getCurrentPoint().getX()+25,marker.getCurrentPoint().getY()-25);
                marker.lineTo(marker.getCurrentPoint().getX()-50,marker.getCurrentPoint().getY()+50);
                canvas.setColor(Color.red);
                canvas.draw(marker);
            }
        }
        if (balls.size()!=0 && obj.size()!=0) {
            for (BouncingBall ball : balls) {
                for (Obj obj1 : obj) {

                    //if ((ball.getX() - ball.radius > obj1.getX()-25 && ball.getX() + ball.radius < obj1.getX()+25)
                       // && (ball.getY() + ball.radius < obj1.getY()+25 && ball.getY() - ball.radius > obj1.getY()-25))
                    if (ball.getX()>obj1.getX() && ball.getY()>obj1.getY())
                    {
                        int saveIndex = ball.getNumber();
                        for (int i = ball.getNumber()+1;i<balls.size();i++)
                            balls.get(i).setNumber(i-1);
                        balls.remove(saveIndex);
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
                    obj.add(new Obj("d",e.getX(),e.getY()));
                    setDestructorIA(false);
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

    public boolean getDestructorIA () {
        return destructorIA;
    }

    public boolean getDefaultCursorTrigger () {
        return defaultCursorTrigger;
    }
}
