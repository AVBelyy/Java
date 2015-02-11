public abstract class AbstractBinaryOp implements Expression3 {
    private final Expression3 left, right;

    public AbstractBinaryOp(Expression3 left, Expression3 right) {
        this.left = left;
        this.right = right;
    }

    public int evaluate(int x, int y, int z) {
        return opImpl(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
    
    protected abstract int opImpl(int a, int b);
}
