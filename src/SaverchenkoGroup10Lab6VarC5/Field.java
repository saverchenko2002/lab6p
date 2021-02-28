package SaverchenkoGroup10Lab6VarC5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class Field extends JPanel implements ISelected {

    private Selected selected;
    private boolean paused;

    private final Font hintFont;
    public int hintXCoordinate;
    public int hintYCoordinate;

    private final LinkedList<Ball> balls = new LinkedList<>();
    private final LinkedList<ObjectCoordinate> objects = new LinkedList<>();
    private final LinkedList<Portal> portals = new LinkedList<>();

    public Field() {
        selected = Selected.NONE;
        setBackground(Color.WHITE);
        hintFont = new Font(Font.DIALOG, Font.BOLD, 14);

        Timer repaintTimer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        repaintTimer.start();

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (Ball ball : balls) {
            ball.paint(canvas);
        }
        for (Portal portal : portals)
            portal.paint(canvas);
        for (ObjectCoordinate object : objects)
            object.paint(canvas);

        canvas.setFont(hintFont);
        canvas.setColor(Color.black);
        switch (selected) {
            case DESTRUCTOR_IS_SELECTED:
                canvas.drawString("press LMB to install destructor", hintXCoordinate, hintYCoordinate + HINT_STEP);
                break;
            case CONSTRUCTOR_IS_SELECTED:
                canvas.drawString("press LMB to install constructor", hintXCoordinate, hintYCoordinate + HINT_STEP);
                break;
            case PORTAL_INPUT:
                canvas.drawString("press LMB to install PORTAL-IN", hintXCoordinate, hintYCoordinate + HINT_STEP);
                break;
            case PORTAL_OUTPUT:
                canvas.drawString("press RMB to install PORTAL-OUT", hintXCoordinate, hintYCoordinate + HINT_STEP);
                break;
            case NONE:
                break;
        }

        if (balls.size() != 0 && (objects.size() != 0 || portals.size() != 0)) {
            intersectResult();
        }
    }

    public boolean checkCloneAvailable(Ball ball) {
        boolean flag = false;
        for (ObjectCoordinate object : objects) {
            if (object.getClass().getSimpleName().equals("Constructor")) {
                if (!ball.intersect(object))
                    flag = true;
                else return false;
            }
        }
        return flag;
    }

    public boolean checkPortalAvailable(Ball ball) {
        boolean flag = false;
        for (Portal portal : portals) {
            if (!ball.intersect(portal))
                flag = true;
            else return false;
        }
        return flag;
    }

    public void intersectResult() {
        for (Ball ball : balls) {
            for (Portal portal : portals) {
                if (ball.intersect(portal) && ball.portalled == Ball.Portalled.AVAILABLE) {
                    ball.portalled = Ball.Portalled.UNAVAILABLE;
                    if (portal.getId() % 2 == 0) {
                        if (portals.size() == portal.getId() + 1)
                            return;
                        ball.setX((portals.get(portal.getId() + 1)).getX());
                        ball.setY((portals.get(portal.getId() + 1)).getY());
                    } else if (portal.getId() % 2 == 1) {
                        ball.setX((portals.get(portal.getId() - 1)).getX());
                        ball.setY((portals.get(portal.getId() - 1)).getY());
                    }
                } else if (checkPortalAvailable(ball))
                    ball.portalled = Ball.Portalled.AVAILABLE;
            }
            for (ObjectCoordinate obj : objects) {
                if (ball.intersect(obj) && obj.getClass().getSimpleName().equals("Destructor")) {
                    ball.getThread().interrupt();
                    int saveIndex = ball.getId();
                    for (int i = ball.getId() + 1; i < balls.size(); i++)
                        balls.get(i).setId(i - 1);
                    balls.remove(saveIndex);
                    return;
                } else if (ball.intersect(obj) && obj.getClass().getSimpleName().equals("Constructor") && ball.cloned == ILimit.Cloned.AVAILABLE) {
                    ball.cloned = Ball.Cloned.UNAVAILABLE;
                    Ball ballCopy = new Ball(ball);
                    addBall(ballCopy);
                    return;
                } else if (checkCloneAvailable(ball))
                    ball.cloned = ILimit.Cloned.AVAILABLE;
            }
        }
    }

    public void addBall() {
        Ball ball = new Ball(this);
        ball.setId(balls.size());
        balls.add(ball);
    }

    public void addBall(Ball ball) {
        ball.setId(balls.size());
        balls.add(ball);
    }

    public void killAllBalls() {
        for (Ball ball : balls)
            ball.getThread().interrupt();
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
                switch (selected) {

                    case DESTRUCTOR_IS_SELECTED:
                        objects.add(new Destructor(e.getX(), e.getY()));
                        break;
                    case CONSTRUCTOR_IS_SELECTED:
                        objects.add(new Constructor(e.getX(), e.getY()));
                        break;
                    case PORTAL_INPUT:
                        selected = Selected.PORTAL_OUTPUT;
                        portals.add(new Portal(e.getX(), e.getY()));
                        break;
                    case PORTAL_OUTPUT:
                        selected = Selected.PORTAL_INPUT;
                        portals.add(new Portal(e.getX(), e.getY(), portals.get(Portal.nextId - 1)));
                    case NONE:
                        break;

                }
            }
            selected = Selected.NONE;
            setCursor(Cursor.getDefaultCursor());
        }
    }

    public class MouseMotionHandler implements MouseMotionListener {

        public void mouseDragged(MouseEvent e) {

        }

        public void mouseMoved(MouseEvent e) {
            if (selected != Selected.NONE) {
                hintXCoordinate = e.getX();
                hintYCoordinate = e.getY();
            }

        }
    }

    public void setSelected(Selected selected) {
        this.selected = selected;
    }

    public Selected getSelected() {
        return selected;
    }

    public LinkedList<Ball> getBalls() {
        return balls;
    }

    public LinkedList<ObjectCoordinate> getObjects() {
        return objects;
    }

    public LinkedList<Portal> getPortals() {
        return portals;
    }
}