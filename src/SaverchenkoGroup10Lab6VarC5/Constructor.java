package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class Constructor extends ObjectCoordinate {

    public Constructor(int x, int y) {
        super(x, y);
    }

    public void paint(Graphics2D canvas) {
        canvas.setColor(Color.blue);
        GeneralPath marker = new GeneralPath();
        Point centerPoint = new Point(getX(),getY());
        marker.moveTo(centerPoint.getX()-getSize()/2.0, centerPoint.getY()-getSize()/2.0);
        marker.lineTo(centerPoint.getX()+getSize()/2.0,centerPoint.getY()-getSize()/2.0);
        marker.lineTo(centerPoint.getX()+getSize()/2.0,centerPoint.getY()+getSize()/2.0);
        marker.lineTo(centerPoint.getX()-getSize()/2.0,centerPoint.getY()+getSize()/2.0);
        marker.lineTo(centerPoint.getX()-getSize()/2.0, centerPoint.getY()-getSize()/2.0);
        marker.closePath();
        canvas.draw(marker);
    }
}

