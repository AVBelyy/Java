public class Variable<T> implements Expression3<T> {
    private final String varName;

    public Variable(String name) {
        varName = name;
    }

    public Numberish<T> evaluate(Numberish<T> x, Numberish<T> y, Numberish<T> z) {
        if (varName.equals("x")) {
            return x;
        } else if (varName.equals("y")) {
            return y;
        } else {
            return z;
        }
    }
}
