public class Variable implements Expression3 {
    private final String varName;

    public Variable(String name) {
        varName = name;
    }

    public double evaluate(double x, double y, double z) {
        if (varName.equals("x")) {
            return x;
        } else if (varName.equals("y")) {
            return y;
        } else {
            return z;
        }
    }
}
