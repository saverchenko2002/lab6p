package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static java.lang.Math.*;

public class BouncingBall extends Thread  {

    public enum Cloned {
        AVAILABLE,
        UNAVAILABLE
    }

    Cloned cloned;

    private static final int MAX_RADIUS = 40;
    private static final int MIN_RADIUS = 3;
    private static final int MAX_SPEED = 15;

    private final Field field;
    public final int radius;
    private  Color color;

    private int speed;
    private double speedX;
    private double speedY;

    private double x;
    private double y;
    private int number = 0;

    public BouncingBall (Field field) {
        cloned = Cloned.AVAILABLE;
        this.field = field;
        double angle = random()*2*PI;
        radius = (int) (MIN_RADIUS+random()*(MAX_RADIUS-2));
        speed = 5*MAX_SPEED / radius;
        if (speed>MAX_SPEED)
            speed=MAX_SPEED;
        speedX=3*cos(angle);
        speedY=3*cos(angle);
        color = new Color((float)random(),(float)random(),(float)random());
        x = random()*(field.getSize().getWidth()-2*radius) + radius;
        y = random()*(field.getSize().getHeight()-2*radius) + radius;
        Thread thisThread = new Thread(this);
        thisThread.start();
    }

    public BouncingBall (Field field, BouncingBall ball) {
        cloned = Cloned.UNAVAILABLE;
        this.field=field;
        color = new Color((float)random(),(float)random(),(float)random());
        radius = ball.getRadius();
        x=ball.getX();
        y=ball.getY();
        double angle = random()*2*PI;
        speedX=3*cos(angle);
        speedY=3*cos(angle);
        Thread thisThread = new Thread(this);
        thisThread.start();
    }

    public void run () {
        while (true) {
            try {
                /*if (cloned == Cloned.UNAVAILABLE) {
                    for (Obj obj2 : field.getObj() ) {
                        System.out.println(obj2.getType());
                        if(obj2.getType()==Obj.Type.CONSTRUCTOR && !intersect(obj2))
                            cloned=Cloned.AVAILABLE;
                    }
                }*/
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

    public boolean intersect(Obj obj) {
        switch (obj.getType()) {
            case DESTRUCTOR: return ((getX() - radius >= obj.getX() && getX() + radius <= obj.getX()+obj.getSize())
                    && (getY() + radius <= obj.getY()+obj.getSize() && getY() - radius > obj.getY()));
            case CONSTRUCTOR: {

                return ((getX() - radius >= obj.getX() && getX() + radius <= obj.getX()+obj.getSize())
                        && (getY() + radius <= obj.getY()+obj.getSize() && getY() - radius > obj.getY()));
            }
            default:
                return false;
        }
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }


}
