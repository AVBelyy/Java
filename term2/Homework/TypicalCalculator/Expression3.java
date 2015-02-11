public interface Expression3<T> {
    public Numberish<T> evaluate(Numberish<T> x, Numberish<T> y, Numberish<T> z) throws CalculateErrorException;
}
