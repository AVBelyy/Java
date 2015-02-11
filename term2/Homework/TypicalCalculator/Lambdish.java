public interface Lambdish<T> {
    public Numberish<T> evaluate(Numberish<T> x) throws CalculateErrorException;
}
