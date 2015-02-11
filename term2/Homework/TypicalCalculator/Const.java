public class Const<T> implements Expression3<T> {
    private final Numberish<T> value;

    public Const(Numberish<T> val) {
        value = val;
    }

    public Numberish<T> evaluate(Numberish<T> x, Numberish<T> y, Numberish<T> z) {
        return value;
    }
}
