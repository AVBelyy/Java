public class UnaryMinus implements Expression3 {
    final Expression3 expression;

    public UnaryMinus(Expression3 expr) {
        expression = expr;
    }

    public int evaluate(int x, int y, int z) {
        return -expression.evaluate(x, y, z);
    }
}
