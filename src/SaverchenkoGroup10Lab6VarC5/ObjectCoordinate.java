package SaverchenkoGroup10Lab6VarC5;

abstract public class ObjectCoordinate implements Paint {
    static final int MIN_OBJECT_SIZE = 100;
    static final int RANDOM_VARIETY = 100;

    private int x;
    private int y;
    private int size;

    public ObjectCoordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }
}