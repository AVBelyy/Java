public class Multiply extends AbstractBinaryOp implements Expression3 {
    public Multiply(Expression3 left, Expression3 right) {
        super(left, right);
    }

    protected double opImpl(double a, double b) {
        return a * b;
    }
}
