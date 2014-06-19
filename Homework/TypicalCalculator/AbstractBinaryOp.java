public abstract class AbstractBinaryOp<T> implements Expression3<T> {
    private final Expression3<T> left, right;

    public AbstractBinaryOp(Expression3<T> left, Expression3<T> right) {
        this.left = left;
        this.right = right;
    }

    public Numberish<T> evaluate(Numberish<T> x, Numberish<T> y, Numberish<T> z) throws CalculateErrorException {
        return opImpl(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
    
    protected abstract Numberish<T> opImpl(Numberish<T> a, Numberish<T> b) throws CalculateErrorException;
}
