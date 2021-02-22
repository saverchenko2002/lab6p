package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class Obj {

    private final int x;
    private final int y;

    private final float size;

    public enum Type {
        DESTRUCTOR,
        CONSTRUCTOR,
        PORTAL
    }

    private final Type type;

    public Obj (Type type, int x, int y) {
        this.type=type;
        this.x=x;
        this.y = y;
        size = (float)Math.random()*100+50;
    }

    public void paint(Graphics2D canvas) {
        switch (this.getType()) {
            case DESTRUCTOR : {
                GeneralPath marker = new GeneralPath();
                canvas.setColor(Color.red);
                marker.moveTo(getX(), getY());
                marker.lineTo(getX() + size, getY() + size);
                marker.moveTo(marker.getCurrentPoint().getX() - size / 2, marker.getCurrentPoint().getY() - size / 2);
                marker.lineTo(marker.getCurrentPoint().getX() + size / 2, marker.getCurrentPoint().getY() - size / 2);
                marker.lineTo(marker.getCurrentPoint().getX() - size, marker.getCurrentPoint().getY() + size);
                canvas.setColor(Color.red);
                canvas.draw(marker);

            }
            case CONSTRUCTOR : {
                break;
            }
            case PORTAL : {
                break;
            }
        }
    }

    public Type getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getSize() {
        return size;
    }

}
