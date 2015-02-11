public abstract class AbstractBinaryOp implements Expression3 {
    private final Expression3 left, right;

    public AbstractBinaryOp(Expression3 left, Expression3 right) {
        this.left = left;
        this.right = right;
    }

    public double evaluate(double x, double y, double z) {
        return opImpl(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
    
    protected abstract double opImpl(double a, double b);
}
