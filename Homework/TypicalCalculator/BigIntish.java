import java.util.Map;
import java.util.HashMap;
import java.math.BigInteger;

public class BigIntish extends AbstractNumberish<BigInteger> implements Numberish<BigInteger> {
    private static final Map<String, Lambdish<BigInteger>> functions;
    public static final BigIntish INSTANCE = new BigIntish();

    public BigIntish() {
        super();
    }

    public BigIntish(BigInteger value) {
        super(value);
    }
    
    public boolean isZero() {
        return value.compareTo(BigInteger.ZERO) == 0;
    }

    public Numberish<BigInteger> abs() throws CalculateErrorException {
        return new BigIntish(value.abs());
    }

    public Numberish<BigInteger> fromString(String s) {
        return new BigIntish(new BigInteger(s));
    }

    public Numberish<BigInteger> add(Numberish<BigInteger> other) throws CalculateErrorException {
        return new BigIntish(value.add(other.getValue()));
    }

    public Numberish<BigInteger> subtract(Numberish<BigInteger> other) throws CalculateErrorException {
        return new BigIntish(value.subtract(other.getValue()));
    }

    public Numberish<BigInteger> divide(Numberish<BigInteger> other) throws CalculateErrorException {
        if (other.isZero()) {
            throw new DivisionByZeroException();
        }
        return new BigIntish(value.divide(other.getValue()));
    }

    public Numberish<BigInteger> multiply(Numberish<BigInteger> other) throws CalculateErrorException {
        return new BigIntish(value.multiply(other.getValue()));
    }

    public Numberish<BigInteger> negate() throws CalculateErrorException {
        return new BigIntish(value.negate());
    }

    public Numberish<BigInteger> bitNegate() throws CalculateErrorException {
        return new BigIntish(value.not());
    }

    public Numberish<BigInteger> log2() throws CalculateErrorException {
        if (value.compareTo(BigInteger.ZERO) <= 0) {
            throw new NonPositiveLogException();
        }
        return new BigIntish(BigInteger.valueOf(value.bitLength()));
    }

    public Numberish<BigInteger> power(Numberish<BigInteger> other) throws CalculateErrorException {
        BigInteger a = value, b = other.getValue();
        if (b.compareTo(BigInteger.ZERO) < 0) {
            throw new NegativeExponentException();
        }
        if (b.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0 || b.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
            throw new BigExponentException();
        }
        return new BigIntish(a.pow(b.intValue()));
    }

    // Arbitrary functions
    public Map<String, Lambdish<BigInteger>> getFunctions() {
        return functions;
    }

    public Numberish<BigInteger> factorial(Numberish<BigInteger> x) throws CalculateErrorException {
        BigInteger value = x.getValue();
        BigInteger result = BigInteger.ONE;
        for (BigInteger i = BigInteger.ONE; i.compareTo(value) <= 0; i = i.add(BigInteger.ONE)) {
            result = result.multiply(i);
        }
        return new BigIntish(result);
    }

    static {
        functions = new HashMap<String, Lambdish<BigInteger>>();

        functions.put("factorial", x -> INSTANCE.factorial(x));
    }
}
