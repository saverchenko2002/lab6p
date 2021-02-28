/*package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static java.lang.Math.*;

public class BouncingBall extends Thread {

    public enum Cloned {
        AVAILABLE,
        UNAVAILABLE
    }

    public enum Portalled {
        AVAILABLE,
        UNAVAILABLE
    }

    Cloned cloned;
    Portalled portalled;


    private int number = 0;

    public BouncingBall(Field field) {
        cloned = Cloned.AVAILABLE;
        this.field = field;


        color = new Color((float) random(), (float) random(), (float) random());
        x = random() * (field.getSize().getWidth() - 2 * radius) + radius;
        y = random() * (field.getSize().getHeight() - 2 * radius) + radius;

    }

    public BouncingBall(Field field, BouncingBall ball) {
        cloned = Cloned.UNAVAILABLE;
        this.field = field;
        radius = ball.getRadius();
        x = ball.getX();
        y = ball.getY();
        double angle = random() * 2 * PI;
        speedX = 3 * cos(angle);
        speedY = 3 * cos(angle);
        speed = ball.speed;
        color = ball.color;
        Thread thisThread = new Thread(this);
        thisThread.start();
    }







    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public void setCloned(Cloned cloned) {
        this.cloned = cloned;
    }

    public void setPortalled(Portalled portalled) {
        this.portalled = portalled;
    }
}
*/