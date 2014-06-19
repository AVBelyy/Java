public class Subtract<T> extends AbstractBinaryOp<T> implements Expression3<T> {
    public Subtract(Expression3<T> left, Expression3<T> right) {
        super(left, right);
    }

    protected Numberish<T> opImpl(Numberish<T> a, Numberish<T> b) throws CalculateErrorException {
        return a.subtract(b);
    }
}
