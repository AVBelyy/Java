public class Divide extends AbstractBinaryOp implements Expression3 {
    public Divide(Expression3 left, Expression3 right) {
        super(left, right);
    }

    protected int opImpl(int a, int b) {
        return a / b;
    }
}
