public class AbsoluteValue<T> implements Expression3<T> {
    final Expression3<T> expression;

    public AbsoluteValue(Expression3<T> expr) {
        expression = expr;
    }

    public Numberish<T> evaluate(Numberish<T> x, Numberish<T> y, Numberish<T> z) throws CalculateErrorException {
        return expression.evaluate(x, y, z).abs();
    }
}
