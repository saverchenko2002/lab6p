package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;
import java.awt.geom.GeneralPath;

import static java.lang.Math.random;

public class Portal extends ObjectCoordinate {

    private final int id;
    public static int nextId = 0;


    public Portal(int x, int y) {
        super(x, y);
        id = nextId++;
        setColor(new Color((float) random(), (float) random(), (float) random()));
    }

    public Portal(int x, int y, Portal portal) {
        super(x, y);
        id = nextId++;
        setColor(portal.getColor());
        setSize(portal.getSize());
    }

    public void paint(Graphics2D canvas) {
        GeneralPath marker = new GeneralPath();
        canvas.setColor(getColor());
        Point centerPoint = new Point(getX(), getY());
        marker.moveTo(centerPoint.getX() - getSize() / 3.0, centerPoint.getY() - getSize() / 2.0);
        marker.lineTo(centerPoint.getX() + getSize() / 3.0, centerPoint.getY() - getSize() / 2.0);
        marker.lineTo(centerPoint.getX() + getSize() / 3.0, centerPoint.getY() + getSize() / 2.0);
        marker.lineTo(centerPoint.getX() - getSize() / 3.0, centerPoint.getY() + getSize() / 2.0);
        marker.lineTo(centerPoint.getX() - getSize() / 3.0, centerPoint.getY() - getSize() / 2.0);
        marker.closePath();
        canvas.draw(marker);
    }

    public int getId() {
        return id;
    }
}