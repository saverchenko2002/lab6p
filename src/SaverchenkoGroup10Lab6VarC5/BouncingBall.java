package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static java.lang.Math.*;

public class BouncingBall extends Thread  {

    private static final int MAX_RADIUS = 40;
    private static final int MIN_RADIUS = 3;
    private static final int MAX_SPEED = 15;

    private final Field field;
    public final int radius;
    private final Color color;

    private int speed;
    private double speedX;
    private double speedY;

    private double x;
    private double y;

   private int number = 0;

    public BouncingBall (Field field) {
        this.field = field;
        radius = (int) (MIN_RADIUS+random()*(MAX_RADIUS-2));
        speed = 5*MAX_SPEED / radius;
        if (speed>MAX_SPEED)
            speed=MAX_SPEED;
        double angle = random()*2*PI;
        speedX=3*cos(angle);
        speedY=3*cos(angle);
        color = new Color((float)random(),(float)random(),(float)random());
        x = random()*(field.getSize().getWidth()-2*radius) + radius;
        y = random()*(field.getSize().getHeight()-2*radius) + radius;
        Thread thisThread = new Thread(this);
        thisThread.start();
    }

    public void run () {
        while (true) {
            try {
                field.canMove(this);
                if (x + speedX <= radius) {
                    speedX = -speedX;
                    x = radius;
                } else if (x + speedX >= field.getWidth() - radius) {
                    speedX = -speedX;
                    x = field.getWidth() - radius;
                } else if (y + speedY <= radius) {
                    speedY = -speedY;
                    y = radius;
                } else if (y + speedY >= field.getHeight() - radius) {
                    speedY = -speedY;
                    y = field.getHeight() - radius;
                } else {
                    x += speedX;
                    y += speedY;
                }
                Thread.sleep(16 - speed);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void paint (Graphics2D canvas) {
        canvas.setColor(color);
        canvas.setPaint(color);
        Ellipse2D.Double ball = new Ellipse2D.Double(x-radius,y-radius, 2*radius, 2*radius);
        canvas.draw(ball);
        canvas.fill(ball);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
