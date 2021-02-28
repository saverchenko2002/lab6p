package SaverchenkoGroup10Lab6VarC5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;


public class Field extends JPanel implements ISelected{

    private Selected selected;

    private boolean paused;
    private final LinkedList<Ball> balls = new LinkedList<>();

    private final Font hintFont;
    public int hintX;
    public int hintY;

    public LinkedList<ObjectCoordinate> Objects = new LinkedList<>();


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
        for (Ball ball : balls) {
            ball.paint(canvas);
        }
        canvas.setFont(hintFont);

        float hintMove = 50;
        canvas.setColor(Color.blue);
        switch (selected) {
            case DESTRUCTOR_IS_SELECTED:
                canvas.drawString("press LMB to install destructor", hintX, hintY + hintMove);
                break;
            case CONSTRUCTOR_IS_SELECTED:
                canvas.drawString("press LMB to install constructor", hintX, hintY + hintMove);
                break;
            case PORTAL_INPUT:
                canvas.drawString("press LMB to install PORTAL-IN", hintX, hintY + hintMove);
                break;
            case PORTAL_OUTPUT:
                canvas.drawString("press RMB to install PORTAL-OUT", hintX, hintY + hintMove);
                break;
            case NONE:
        }

        if (Objects.size() != 0) {
            for (ObjectCoordinate object : Objects)
                object.paint(canvas);
        }

        if (balls.size() != 0 && Objects.size() != 0) {
            touches();

        }
    }

    public void touches() {
        int ID;
        for (Ball ball : balls) {
            for (ObjectCoordinate obj : Objects) {
                if (ball.intersect(obj) && obj.getClass().getSimpleName().equals("Destructor")) {
                    ball.getThread().interrupt();
                    int saveIndex = ball.getId();
                    for (int i = ball.getId() + 1; i < balls.size(); i++)
                        balls.get(i).setId(i - 1);
                    balls.remove(saveIndex);
                    return;
                } else if (ball.intersect(obj) && obj.getClass().getSimpleName().equals("Constructor")) {
                    if (ball.cloned == Ball.Cloned.AVAILABLE) {
                        ball.cloned = Ball.Cloned.UNAVAILABLE;
                        Ball ballCopy = new Ball(this, ball);
                        addBall(ballCopy);
                        return;
                    }
                }
            }
        }
    }
   /* public void clearAll() {
        for (BouncingBall balls1 : balls) {
            balls1.interrupt();
        }
    }

    */


    public void addBall() {

        Ball ball = new Ball(this);
        ball.setId(balls.size());
        balls.add(ball);
    }

    public void addBall(Ball ball) {
        ball.setId(balls.size());
        balls.add(ball);
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized void canMove(Ball ball) throws InterruptedException {
        if (paused)
            wait();
    }

    public class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                System.out.println(Objects.size());
                switch (selected) {

                    case DESTRUCTOR_IS_SELECTED:
                        Objects.add(new Destructor(e.getX(), e.getY()));
                        break;
                    case CONSTRUCTOR_IS_SELECTED:
                        Objects.add(new Constructor(e.getX(), e.getY()));
                        break;
                    case PORTAL_INPUT:

                        selected = Selected.PORTAL_OUTPUT;
                        //obj.add(new Obj(Obj.Type.PORTAL_IN, e.getX(), e.getY()));
                        //Obj.dadPortal = obj.size() - 1;
                        break;
                    case PORTAL_OUTPUT:
                        selected = Selected.PORTAL_INPUT;
                        //obj.add(new Obj(Obj.Type.PORTAL_IN, e.getX(), e.getY(), obj.get(Obj.dadPortal)));

                }
            }
            selected = Selected.NONE;
            setCursor(Cursor.getDefaultCursor());
        }
    }

    public class MouseMotionHandler implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (selected == Selected.NONE) {
                hintX = e.getX();
                hintY = e.getY();
            }

        }
    }

    public void setSelected(Selected selected) {
        this.selected = selected;
    }

    public Selected getSelected() {
        return selected;
    }

    //public LinkedList<Obj> getObj() {
    //return obj;
    // }

    /*public boolean cloneCheck(BouncingBall ball) {
        boolean flag = false;
        for (Obj obj1 : obj) {
            if (obj1.getType() == Obj.Type.CONSTRUCTOR) {
                if (!ball.intersect(obj1))
                    flag = true;
                else return false;
            }
        }
        return flag;
    }

     */

    // public LinkedList<BouncingBall> getBalls() {
    //return balls;
    //}
}

/*switch (obj1.getType()) {
                        case DESTRUCTOR:
                            ball.interrupt();
                            int saveIndex = ball.getNumber();
                            for (int i = ball.getNumber() + 1; i < balls.size(); i++)
                                balls.get(i).setNumber(i - 1);
                            balls.remove(saveIndex);
                            return;

                        case CONSTRUCTOR:
                            if (ball.cloned == BouncingBall.Cloned.AVAILABLE) {
                                ball.cloned = BouncingBall.Cloned.UNAVAILABLE;
                                BouncingBall ballCopy = new BouncingBall(this, ball);
                                addBall(ballCopy);
                                return;
                            }

                        case PORTAL_IN:
                            if (ball.portalled == BouncingBall.Portalled.UNAVAILABLE)
                                return;
                            ID = obj1.getId();
                            for (Obj obj2 : obj) {
                                if (obj2.getId() == ID + 1) {
                                    ball.setX((obj2.getX() + obj2.getSize() / 3));
                                    ball.setY((obj2.getY() + obj2.getSize() / 2));
                                    ball.setPortalled(BouncingBall.Portalled.UNAVAILABLE);
                                    return;
                                }
                            }


                        case PORTAL_OUT: {
                            if (ball.portalled == BouncingBall.Portalled.UNAVAILABLE)
                                return;
                            ID = obj1.getId();
                            for (Obj obj2 : obj) {
                                if (obj2.getId() == ID - 1) {
                                    ball.setX((obj2.getX() + obj2.getSize() / 3));
                                    ball.setY((obj2.getY() + obj2.getSize() / 2));
                                    ball.setPortalled(BouncingBall.Portalled.UNAVAILABLE);
                                    return;
                                }
                            }
                        }
                    }

 */