public class AbsoluteValue implements Expression3 {
    final Expression3 expression;

    public AbsoluteValue(Expression3 expr) {
        expression = expr;
    }

    public int evaluate(int x, int y, int z) {
        return Math.abs(expression.evaluate(x, y, z));
    }
}
