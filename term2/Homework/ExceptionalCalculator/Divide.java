public class Divide extends AbstractBinaryOp implements Expression3 {
    public Divide(Expression3 left, Expression3 right) {
        super(left, right);
    }

    protected long opImpl(long a, long b) throws CalculateErrorException {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            throw new DivisionByZeroException();
        }
    }
}
