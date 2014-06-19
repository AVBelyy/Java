import java.util.Map;
import java.util.HashMap;

public class Doublish extends AbstractNumberish<Double> implements Numberish<Double> {
    private static final double EPS = 1e-16;
    private static final Map<String, Lambdish<Double>> functions;
    public static final Doublish INSTANCE = new Doublish();

    public Doublish() {
        super();
    }

    public Doublish(Double value) {
        super(value);
    }

    private double checkNaN(double x) throws NotANumberException {
        if (Double.isNaN(x)) {
            // no NaN checking this time
            // throw new NotANumberException();
        }
        return x;
    }

    public boolean isZero() {
        return Math.abs(value) <= EPS;
    }

    public Numberish<Double> abs() throws CalculateErrorException {
        return new Doublish(Math.abs(value));
    }

    public Numberish<Double> fromString(String s) {
        return new Doublish(Double.parseDouble(s));
    }

    public Numberish<Double> add(Numberish<Double> other) throws CalculateErrorException {
        return new Doublish(value + other.getValue());
    }

    public Numberish<Double> subtract(Numberish<Double> other) throws CalculateErrorException {
        return new Doublish(value - other.getValue());
    }

    public Numberish<Double> divide(Numberish<Double> other) throws CalculateErrorException {
        return new Doublish(checkNaN(value / other.getValue()));
    }

    public Numberish<Double> multiply(Numberish<Double> other) throws CalculateErrorException {
        return new Doublish(checkNaN(value * other.getValue()));
    }

    public Numberish<Double> negate() throws CalculateErrorException {
        return new Doublish(-value);
    }

    public Numberish<Double> bitNegate() throws CalculateErrorException {
        throw new NotImplementedException("~", Double.class);
    }

    public Numberish<Double> log2() throws CalculateErrorException {
        if (value <= EPS) {
            throw new NonPositiveLogException();
        }
        return new Doublish(Math.log(value) / Math.log(2));
    }

    public Numberish<Double> power(Numberish<Double> other) throws CalculateErrorException {
        if (value <= -EPS && Math.abs(other.getValue() - other.getValue().longValue()) > EPS) {
            throw new NegativeFractionalExponentException();
        }
        return new Doublish(Math.pow(value, other.getValue()));
    }

    // Arbitrary functions
    public Map<String, Lambdish<Double>> getFunctions() {
        return functions;
    }

    public Numberish<Double> factorial(Numberish<Double> x) throws CalculateErrorException {
        double value = x.getValue();
        double result = 1.0;
        for (int i = 1; i <= value; i++) {
            result *= i;
        }
        return new Doublish(result);
    }

    public Numberish<Double> sin(Numberish<Double> x) throws CalculateErrorException {
        return new Doublish(Math.sin(x.getValue()));
    }

    static {
        functions = new HashMap<String, Lambdish<Double>>();

        functions.put("factorial", x -> INSTANCE.factorial(x));
        functions.put("sin", x -> INSTANCE.sin(x));
    }
}
