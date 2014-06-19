public class Const implements Expression3 {
    private final int value;

    public Const(int val) {
        value = val;
    }

    public int evaluate(int x, int y, int z) {
        return value;
    }
}
