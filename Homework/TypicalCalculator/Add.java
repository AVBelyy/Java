public class Add<T> extends AbstractBinaryOp<T> implements Expression3<T> {
    public Add(Expression3<T> left, Expression3<T> right) {
        super(left, right);
    }

    protected Numberish<T> opImpl(Numberish<T> a, Numberish<T> b) throws CalculateErrorException {
        return a.add(b);
    }
}
