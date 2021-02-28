package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static java.lang.Math.*;

public class Ball extends ObjectCoordinate implements Runnable, ILimit {

    Cloned cloned;
    Portalled portalled;

    private final Field field;
    private final int radius;

    private int speed;
    private double speedX;
    private double speedY;

    private int id = 0;

    Thread thread;

    public Ball(Field field) {
        cloned = Cloned.AVAILABLE;
        portalled = Portalled.AVAILABLE;
        this.field = field;
        setColor(new Color((float) random(), (float) random(), (float) random()));
        radius = (int) (MIN_RADIUS + random() * (MAX_RADIUS - 2));
        speed = 5 * MAX_SPEED / radius;
        if (speed > MAX_SPEED)
            speed = MAX_SPEED;
        double angle = random() * 2 * PI;
        while (3 * cos(angle) < 1) {
            angle = random() * 2 * PI;
        }
        speedX = 3 * cos(angle);
        speedY = 3 * cos(angle);
        setX((int) (random() * (field.getSize().getWidth() - 2 * radius) + radius));
        setY((int) (random() * (field.getSize().getHeight() - 2 * radius) + radius));
        thread = new Thread(this);
        thread.start();
    }

    public Ball(Ball ball) {
        cloned = Cloned.UNAVAILABLE;
        portalled = Portalled.AVAILABLE;
        this.field = ball.field;
        setColor(ball.getColor());
        radius = ball.radius;
        speed = ball.speed;
        double angle = random() * 2 * PI;
        while (3 * cos(angle) < 1) {
            angle = random() * 2 * PI;
        }
        speedX = 3 * cos(angle);
        speedY = 3 * cos(angle);
        setX(ball.getX());
        setY(ball.getY());
        thread = new Thread(this);
        thread.start();
    }

    public void paint(Graphics2D canvas) {
        canvas.setColor(getColor());
        canvas.setPaint(getColor());
        Ellipse2D.Double ball = new Ellipse2D.Double(getX() - radius, getY() - radius, 2 * radius, 2 * radius);
        canvas.draw(ball);
        canvas.fill(ball);
    }

    public boolean intersect(ObjectCoordinate objects) {
        if (objects.getClass().getSimpleName().equals("Destructor") || objects.getClass().getSimpleName().equals("Constructor")) {
            return ((getX() - radius >= objects.getX() - objects.getSize() / 2.0 && getX() + radius <= objects.getX() + objects.getSize() / 2.0)
                    && (getY() + radius <= objects.getY() + objects.getSize() / 2.0 && getY() - radius > objects.getY() - objects.getSize() / 2.0));
        } else if (objects.getClass().getSimpleName().equals("Portal")) {
            return ((getX() - radius >= objects.getX() - objects.getSize() / 3.0 && getX() + radius <= objects.getX() + objects.getSize() / 3.0)
                    && (getY() + radius <= objects.getY() + objects.getSize() / 2.0 && getY() - radius > objects.getY() - objects.getSize() / 2.0));
        } else return false;
    }

    public void run() {
        while (true) {
            try {

                field.canMove(this);
                if (getX() + speedX <= radius) {
                    speedX = -speedX;
                    setX(radius);
                } else if (getX() + speedX >= field.getWidth() - radius) {
                    speedX = -speedX;
                    setX(field.getWidth() - radius);
                } else if (getY() + speedY <= radius) {
                    speedY = -speedY;
                    setY(radius);
                } else if (getY() + speedY >= field.getHeight() - radius) {
                    speedY = -speedY;
                    setY(field.getHeight() - radius);
                } else {
                    setX((int) (getX() + speedX));
                    setY((int) (getY() + speedY));
                }
                Thread.sleep(16 - speed);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Thread getThread() {
        return thread;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}