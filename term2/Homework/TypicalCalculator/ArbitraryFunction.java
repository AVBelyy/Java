import java.util.Map;

public class ArbitraryFunction<T> implements Expression3<T> {
    String f;
    Numberish<T> instance;
    final Expression3<T> expr;

    public ArbitraryFunction(String f, Numberish<T> instance, Expression3<T> expr) {
        this.f = f;
        this.instance = instance;
        this.expr = expr;
    }

    public Numberish<T> evaluate(Numberish<T> x, Numberish<T> y, Numberish<T> z) throws CalculateErrorException {
        Map<String, Lambdish<T>> functions = instance.getFunctions();
        for (String key : functions.keySet()) {
            if (key.equals(f)) {
                return functions.get(f).evaluate(expr.evaluate(x, y, z));
            }
        }
        throw new UnknownFunctionException(f);
    }
}
