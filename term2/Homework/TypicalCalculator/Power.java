public class Power<T> extends AbstractBinaryOp<T> implements Expression3<T> {
    public Power(Expression3<T> left, Expression3<T> right) {
        super(left, right);
    }

    protected Numberish<T> opImpl(Numberish<T> a, Numberish<T> b) throws CalculateErrorException {
        return a.power(b);
    }
}
