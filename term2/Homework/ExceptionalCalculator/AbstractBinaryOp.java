public abstract class AbstractBinaryOp implements Expression3 {
    private final Expression3 left, right;

    public AbstractBinaryOp(Expression3 left, Expression3 right) {
        this.left = left;
        this.right = right;
    }

    protected static void checkOverflow(long x) throws OverflowException {
        if (x < Integer.MIN_VALUE || x > Integer.MAX_VALUE) {
            throw new OverflowException();
        }
    }

    public int evaluate(int x, int y, int z) throws CalculateErrorException {
        long result = opImpl((long)left.evaluate(x, y, z), (long)right.evaluate(x, y, z));
        checkOverflow(result);
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) {
            throw new OverflowException();
        }
        return (int)result;
    }
    
    protected abstract long opImpl(long a, long b) throws CalculateErrorException;
}
