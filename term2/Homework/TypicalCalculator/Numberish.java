import java.util.Map; 

public interface Numberish<T> {
    public boolean isZero();
    public T getValue();
    public Numberish<T> abs() throws CalculateErrorException;
    public Numberish<T> negate() throws CalculateErrorException;
    public Numberish<T> bitNegate() throws CalculateErrorException;
    public Numberish<T> log2() throws CalculateErrorException;
    public Numberish<T> fromString(String s);
    public Numberish<T> fromInteger(Integer n);
    public Numberish<T> add(Numberish<T> other) throws CalculateErrorException;
    public Numberish<T> subtract(Numberish<T> other) throws CalculateErrorException;
    public Numberish<T> divide(Numberish<T> other) throws CalculateErrorException;
    public Numberish<T> multiply(Numberish<T> other) throws CalculateErrorException;
    public Numberish<T> power(Numberish<T> other) throws CalculateErrorException;

    // Arbitral functions
    public Map<String, Lambdish<T>> getFunctions();

    public Numberish<T> factorial(Numberish<T> x) throws CalculateErrorException;
}
