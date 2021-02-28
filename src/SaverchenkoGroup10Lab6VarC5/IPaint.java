package SaverchenkoGroup10Lab6VarC5;

import java.awt.*;

public interface IPaint {
    int MIN_OBJECT_SIZE = 100;
    int RANDOM_VARIETY = 50;

    int MAX_RADIUS = 40;
    int MIN_RADIUS = 3;
    int MAX_SPEED = 15;

    void paint(Graphics2D canvas);
}
