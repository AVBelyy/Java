public class Power extends AbstractBinaryOp implements Expression3 {
    public Power(Expression3 left, Expression3 right) {
        super(left, right);
    }

    protected long opImpl(long a, long b) throws CalculateErrorException {
        if (b < 0) {
            throw new NegativePowerException();
        }
        long result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
            AbstractBinaryOp.checkOverflow(result);
        }
        return result;
    }
}
