public class UnaryMinus implements Expression3 {
    final Expression3 expression;

    public UnaryMinus(Expression3 expr) {
        expression = expr;
    }

    public double evaluate(double x, double y, double z) {
        return -expression.evaluate(x, y, z);
    }
}
