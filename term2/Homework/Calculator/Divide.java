public class Divide extends AbstractBinaryOp implements Expression3 {
    public Divide(Expression3 left, Expression3 right) {
        super(left, right);
    }

    protected double opImpl(double a, double b) {
        return a / b;
    }
}
