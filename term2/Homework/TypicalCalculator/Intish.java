import java.util.Map;
import java.util.HashMap;

public class Intish extends AbstractNumberish<Integer> implements Numberish<Integer> {
    private static final Map<String, Lambdish<Integer>> functions;
    public static final Intish INSTANCE = new Intish();

    public Intish() {
        super();
    }

    public Intish(Integer value) {
        super(value);
    }
    
    private static int checkOverflow(long x) throws OverflowException {
        if (x < Integer.MIN_VALUE || x > Integer.MAX_VALUE) {
            // no overflow checking this time
            // throw new OverflowException();
        }
        return (int)x;
    }

    public boolean isZero() {
        return value == 0;
    }

    public Numberish<Integer> abs() throws CalculateErrorException {
        return new Intish(checkOverflow(Math.abs((long)value)));
    }

    public Numberish<Integer> fromString(String s) {
        return new Intish(Integer.parseInt(s));
    }

    public Numberish<Integer> add(Numberish<Integer> other) throws CalculateErrorException {
        return new Intish(checkOverflow((long)value + (long)other.getValue()));
    }

    public Numberish<Integer> subtract(Numberish<Integer> other) throws CalculateErrorException {
        return new Intish(checkOverflow((long)value - (long)other.getValue()));
    }

    public Numberish<Integer> divide(Numberish<Integer> other) throws CalculateErrorException {
        if (other.isZero()) {
            throw new DivisionByZeroException();
        }
        return new Intish(checkOverflow((long)value / (long)other.getValue()));
    }

    public Numberish<Integer> multiply(Numberish<Integer> other) throws CalculateErrorException {
        return new Intish(checkOverflow((long)value * (long)other.getValue()));
    }

    public Numberish<Integer> negate() throws CalculateErrorException {
        return new Intish(checkOverflow(-(long)value));
    }

    public Numberish<Integer> bitNegate() throws CalculateErrorException {
        return new Intish(~value);
    }

    public Numberish<Integer> log2() throws CalculateErrorException {
        int val = value;
        Integer result = 0;
        if (val <= 0) {
            throw new NonPositiveLogException();
        }
        while (val != 1) {
            val >>= 1;
            result++;
        }
        return new Intish(result);
    }

    public Numberish<Integer> power(Numberish<Integer> other) throws CalculateErrorException {
        int a = value, b = other.getValue();
        if (b < 0) {
            throw new NegativeExponentException();
        }
        long result = 1;
        for (int i = 0; i < b; i++) {
            result = checkOverflow(result * a);
        }
        return new Intish((int)result);
    }

    // Arbitrary functions
    public Map<String, Lambdish<Integer>> getFunctions() {
        return functions;
    }

    public Numberish<Integer> factorial(Numberish<Integer> x) throws CalculateErrorException {
        int value = x.getValue();
        long result = 1;
        for (int i = 1; i <= value; i++) {
            result = checkOverflow(result * i);
        }
        return new Intish((int)result);
    }
    
    static {
        functions = new HashMap<String, Lambdish<Integer>>();

        functions.put("factorial", x -> INSTANCE.factorial(x));
    }
}
