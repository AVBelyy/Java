public class UnaryNot implements Expression3 {
    final Expression3 expression;

    public UnaryNot(Expression3 expr) {
        expression = expr;
    }

    public int evaluate(int x, int y, int z) {
        return ~expression.evaluate(x, y, z);
    }
}
