public class Variable implements Expression3 {
    private final String varName;

    public Variable(String name) {
        varName = name;
    }

    public int evaluate(int x, int y, int z) {
        if (varName.equals("x")) {
            return x;
        } else if (varName.equals("y")) {
            return y;
        } else {
            return z;
        }
    }
}
