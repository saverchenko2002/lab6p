package SaverchenkoGroup10Lab6VarC5;

public interface ISelected {

    int HINT_STEP = 50;

    enum Selected {
        NONE,
        DESTRUCTOR_IS_SELECTED,
        CONSTRUCTOR_IS_SELECTED,
        PORTAL_INPUT,
        PORTAL_OUTPUT
    }
}