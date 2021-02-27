package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;
import java.awt.geom.GeneralPath;

import static java.lang.Math.random;

public class Obj {

    private final int x;
    private final int y;

    public static int nextId = 0;
    private int id = 0;

    public enum Type {
        DESTRUCTOR,
        CONSTRUCTOR,
        PORTAL_IN,
        PORTAL_OUT
    }

    private final float size;
    private static float dadSize;
    private Color color;
    private final Type type;
    static int dadPortal;

    public Obj(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        size = (float) Math.random() * 100 + 150;
        if (type == Type.PORTAL_IN) {
            color = new Color((float) random(), (float) random(), (float) random());
            id = nextId++;
            dadSize = size;
        }
    }

    public Obj(Type type, int x, int y, Obj obj) {
        this.type = type;
        this.x = x;
        this.y = y;
        size = dadSize;
        color = obj.getColor();
        id = nextId++;
        System.out.println(id);

    }

    public void paint(Graphics2D canvas) {
        GeneralPath marker = new GeneralPath();
        switch (this.getType()) {

            case CONSTRUCTOR:
                canvas.setColor(Color.black);
                marker.moveTo(getX(), getY());
                marker.lineTo(getX() + size, getY());
                marker.lineTo(marker.getCurrentPoint().getX(), getY() + size);
                marker.lineTo(marker.getCurrentPoint().getX() - size, marker.getCurrentPoint().getY());
                marker.lineTo(marker.getCurrentPoint().getX(), marker.getCurrentPoint().getY() - size);
                break;

            case DESTRUCTOR:
                canvas.setColor(Color.red);
                marker.moveTo(getX(), getY());
                marker.lineTo(getX() + size, getY() + size);
                marker.moveTo(marker.getCurrentPoint().getX() - size / 2, marker.getCurrentPoint().getY() - size / 2);
                marker.lineTo(marker.getCurrentPoint().getX() + size / 2, marker.getCurrentPoint().getY() - size / 2);
                marker.lineTo(marker.getCurrentPoint().getX() - size, marker.getCurrentPoint().getY() + size);
                canvas.setColor(Color.red);
                break;

            case PORTAL_IN:

                canvas.setColor(color);
                marker.moveTo(getX(), getY());
                marker.lineTo(getX() + size / 3, getY());
                marker.lineTo(marker.getCurrentPoint().getX(), getY() + size);
                marker.lineTo(marker.getCurrentPoint().getX() - size / 3, marker.getCurrentPoint().getY());
                marker.lineTo(marker.getCurrentPoint().getX(), marker.getCurrentPoint().getY() - size);
                break;

            case PORTAL_OUT:

                canvas.setColor(Color.red);
                marker.moveTo(getX(), getY());
                marker.lineTo(getX() + dadSize / 3, getY());
                marker.lineTo(marker.getCurrentPoint().getX(), getY() + dadSize);
                marker.lineTo(marker.getCurrentPoint().getX() - dadSize / 3, marker.getCurrentPoint().getY());
                marker.lineTo(marker.getCurrentPoint().getX(), marker.getCurrentPoint().getY() - dadSize);
                break;
        }
        canvas.draw(marker);
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

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }
}
