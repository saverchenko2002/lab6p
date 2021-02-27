package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class Destructor extends ObjectCoordinate {

    public Destructor(int x, int y) {
        super(x, y);
        setSize((int)(Math.random()*RANDOM_VARIETY+MIN_OBJECT_SIZE));
    }

    public void paint(Graphics2D canvas) {
        canvas.setColor(Color.red);
        GeneralPath marker = new GeneralPath();
        Point centerPoint = new Point((int)(getX()+getSize()/2.0),(int)(getY()+getSize()/2.0));
        marker.moveTo(getX(),getY());
        marker.lineTo(getX()+getSize(),getY()+getSize());
        marker.moveTo(centerPoint.getX(),centerPoint.getY());
        marker.lineTo(getX()+getSize(),getY());
        marker.moveTo(centerPoint.getX(),centerPoint.getY());
        marker.lineTo(getX(),getY()+getSize());
        marker.closePath();
        canvas.draw(marker);
    }
}
