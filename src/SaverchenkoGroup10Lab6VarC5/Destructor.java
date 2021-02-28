package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class Destructor extends ObjectCoordinate {

    public Destructor(int x, int y) {
        super(x, y);
    }

    public void paint(Graphics2D canvas) {
        canvas.setColor(Color.red);
        GeneralPath marker = new GeneralPath();
        Point centerPoint = new Point(getX(), getY());
        marker.moveTo(centerPoint.getX(), centerPoint.getY());
        marker.lineTo(centerPoint.getX() - getSize() / 2.0, centerPoint.getY() - getSize() / 2.0);
        marker.moveTo(centerPoint.getX(), centerPoint.getY());
        marker.lineTo(centerPoint.getX() + getSize() / 2.0, centerPoint.getY() + getSize() / 2.0);
        marker.moveTo(centerPoint.getX(), centerPoint.getY());
        marker.lineTo(centerPoint.getX() + getSize() / 2.0, centerPoint.getY() - getSize() / 2.0);
        marker.moveTo(centerPoint.getX(), centerPoint.getY());
        marker.lineTo(centerPoint.getX() - getSize() / 2.0, centerPoint.getY() + getSize() / 2.0);
        marker.closePath();
        canvas.draw(marker);
    }
}